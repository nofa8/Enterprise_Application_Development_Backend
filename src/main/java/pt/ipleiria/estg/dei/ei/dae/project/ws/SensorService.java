package pt.ipleiria.estg.dei.ei.dae.project.ws;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.jboss.resteasy.plugins.server.servlet.ServletSecurityContext;
import pt.ipleiria.estg.dei.ei.dae.project.dtos.*;
import pt.ipleiria.estg.dei.ei.dae.project.ejbs.*;
import pt.ipleiria.estg.dei.ei.dae.project.entities.*;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.project.security.Authenticated;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static pt.ipleiria.estg.dei.ei.dae.project.entities.enums.VolumeState.DELIVERED;
import static pt.ipleiria.estg.dei.ei.dae.project.entities.enums.VolumeState.RETURNED;

//@Path("/orders/{id:code_order}/volumes/{id:code_volume}/sensors")
@Path("/orders/{code_order}/volumes/{code_volume}/sensors") // relative url web path for this service
//@Authenticated
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class SensorService {
    @EJB
    private ClientBean clientBean;
    @EJB
    private UserBean userBean;

    @EJB
    private OrderBean orderBean;

    @EJB
    private SensorBean sensorBean;
    @EJB
    private VolumeBean volumeBean;

    @Context
    private SecurityContext securityContext;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response receiveSensorData (
            @PathParam("code_order") Long codeOrder,
            @PathParam("code_volume") Long codeVolume,

            List<SensorDataDTO> sensorDataNew) throws  MyEntityNotFoundException{

        Volume volume = volumeBean.find(codeVolume);
        if(volume == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .build();
        }
        if(volume.getState()== DELIVERED || volume.getState()== RETURNED){
            return Response.status(Response.Status.BAD_REQUEST)
                    .build();
        }
        try {
            sensorBean.createNewValue(sensorDataNew);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .build();
        }
    }


    @GET
    @Authenticated
    public Response getSensorHistory(
            @PathParam("code_order") Long codeOrder,
            @PathParam("code_volume") Long codeVolume,
            @QueryParam("sensorType") String sensorType) {  // Parâmetro opcional para filtrar por tipo de sensor

        try {
            String username = securityContext.getUserPrincipal().getName();
            User user = userBean.findOrFail(username);
            Volume volume = volumeBean.find(codeVolume);
            Order order = volume.getOrder();

            if (order.getCode() == codeOrder &&
                    (securityContext.isUserInRole("Manager") ||
                            (securityContext.isUserInRole("Client") && order.getClient().getEmail().equals(user.getEmail())))) {

                // Verificar se o volume existe
                volume = volumeBean.findWithSensors(codeVolume);

                // Se sensorType for fornecido, filtrar os sensores por tipo
                List<Sensor> sensors;
                if (sensorType != null && !sensorType.isEmpty()) {

                    sensors = volume.getSensorsByType(sensorType);
                } else {

                    sensors = volume.getSensors();
                }
                if (sensors.isEmpty()) {
                    return Response.status(Response.Status.NOT_FOUND).build();}

                List<SensorDTO> sensorsDTO = SensorDTO.from(sensors);

                for (SensorDTO sensorDTO : sensorsDTO) {
                    List<SensorValueHistory> sensorValueHistories = sensorBean.getSensorHistory(sensorDTO.getCode());
                    List<SensorLogDTO> log = SensorLogDTO.from(sensorValueHistories);
                    sensorDTO.setLog(log);
                }

                return Response.ok(sensorsDTO).build();
            }

            return Response.status(Response.Status.BAD_REQUEST).build();

        } catch (MyEntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao obter o histórico dos sensores: " + e.getMessage())
                    .build();
        }
    }

}