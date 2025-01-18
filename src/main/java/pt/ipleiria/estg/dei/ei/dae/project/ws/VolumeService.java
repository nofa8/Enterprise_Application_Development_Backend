package pt.ipleiria.estg.dei.ei.dae.project.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import pt.ipleiria.estg.dei.ei.dae.project.dtos.*;
import pt.ipleiria.estg.dei.ei.dae.project.ejbs.ClientBean;
import pt.ipleiria.estg.dei.ei.dae.project.ejbs.OrderBean;
import pt.ipleiria.estg.dei.ei.dae.project.ejbs.UserBean;
import pt.ipleiria.estg.dei.ei.dae.project.ejbs.VolumeBean;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Order;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Product;
import pt.ipleiria.estg.dei.ei.dae.project.entities.User;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Volume;
import pt.ipleiria.estg.dei.ei.dae.project.entities.enums.VolumeState;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.project.security.Authenticated;

import java.util.List;


//@Path("orders/{id:code_order}/volumes") // relative url web path for this service
@Path("orders/{code_order}/volumes")
//@Authenticated
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class VolumeService {

    @EJB
    private VolumeBean volumeBean;

    @EJB
    private ClientBean clientBean;
    @EJB
    private UserBean userBean;
    @EJB
    private OrderBean orderBean;

    @Context
    private SecurityContext securityContext;

    @POST
    @Path("/")
//    @Authenticated
    public Response createVolumes(@PathParam("code_order") Long orderId,
                                  List<PostVolumeDTO> volumeDTOs)
            throws MyEntityNotFoundException, MyConstraintViolationException, MyEntityExistsException {
        try{
            for (PostVolumeDTO vol: volumeDTOs){
                if (vol.getProducts().isEmpty() || vol.getSensors().isEmpty()){
                    return Response.status(Response.Status.BAD_REQUEST).build();
                }
            }
            volumeBean.createVolumes(orderId, volumeDTOs);
            return Response.status(Response.Status.CREATED).build();

        }catch (IllegalStateException e){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @PATCH
    @Path("/{code_volume}")
    @Authenticated
    public Response patchVolume(@PathParam("code_order") Long orderId,
                                @PathParam("code_volume") Long volumeId,
                                VolumeState state)
            throws MyEntityNotFoundException, MyConstraintViolationException {
        Order order = orderBean.findWithVolumes(orderId);
        if (order == null){
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Order " + orderId + " not found")
                    .build();
        }
        Volume volume = volumeBean.find(volumeId);
        if (volume == null){
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Volume " + volumeId + " not found")
                    .build();
        }
        if(!order.getVolumes().contains(volume)){
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("No volume with id " + volumeId + " in order with id " + orderId)
                    .build();
        }
        volumeBean.patchState(volumeId,state);
        return Response.ok().build();
    }

    @GET
    @Path("/{code_volume}")
    @Authenticated
    public Response getVolumeDetails(@PathParam("code_order") Long orderId,
                                     @PathParam("code_volume") Long volumeId) throws MyEntityNotFoundException {

        String username = securityContext.getUserPrincipal().getName();
        User user = userBean.findOrFail(username);
        Volume volume = volumeBean.find(volumeId);
        Order order = volume.getOrder();

        if (order.getCode() == orderId && (securityContext.isUserInRole("Manager") || ((securityContext.isUserInRole("Client")) && order.getClient().getEmail().equals(user.getEmail())))){
            VolumeDTO volumeDetails = VolumeDTO.from(volumeBean.findWithProductsAndSensors(volumeId));
            return Response.ok(volumeDetails).build();
        }
        return Response.status(404).build();
    }
}
