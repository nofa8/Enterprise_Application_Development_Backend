package pt.ipleiria.estg.dei.ei.dae.project.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.project.dtos.VolumeDTO;
import pt.ipleiria.estg.dei.ei.dae.project.ejbs.VolumeBean;
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

    @POST
    @Path("/")
    public Response createVolumes(@PathParam("code_order") Long orderId, List<VolumeDTO> volumeDTOs)
            throws MyEntityNotFoundException, MyConstraintViolationException, MyEntityExistsException {
        volumeBean.createVolumes(orderId, volumeDTOs);
        return Response.status(Response.Status.CREATED).build();
    }

    @PATCH
    @Path("/{id:code_order}/volumes/{id:code_volume}")
    public Response updateVolume(@PathParam("id:code_order") Long orderId,
                                 @PathParam("id:code_volume") Long volumeId,
                                 VolumeState state)
            throws MyEntityNotFoundException, MyConstraintViolationException {
        volumeBean.patchState(volumeId,state);
        return Response.ok().build();
    }

    @GET
    @Path("/{code_volume}")
    @Authenticated
    public Response getVolumeDetails(@PathParam("code_order") Long orderId,
                                     @PathParam("code_volume") Long volumeId) {
        VolumeDTO volumeDetails = VolumeDTO.from(volumeBean.find(volumeId));
        return Response.ok(volumeDetails).build();
    }
}
