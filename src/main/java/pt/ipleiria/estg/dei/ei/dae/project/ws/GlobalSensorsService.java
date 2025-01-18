package pt.ipleiria.estg.dei.ei.dae.project.ws;


import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.project.dtos.*;
import pt.ipleiria.estg.dei.ei.dae.project.ejbs.SensorBean;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Sensor;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Volume;
import pt.ipleiria.estg.dei.ei.dae.project.entities.enums.OrderState;
import pt.ipleiria.estg.dei.ei.dae.project.security.Authenticated;

import java.util.List;

import static pt.ipleiria.estg.dei.ei.dae.project.entities.enums.VolumeState.DELIVERED;
import static pt.ipleiria.estg.dei.ei.dae.project.entities.enums.VolumeState.RETURNED;

@Path("sensors") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON})
public class GlobalSensorsService {
    @EJB
    private SensorBean sensorBean;

    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/")
    public List<SimpleSensorDTO> getAllSensors() {
        return SimpleSensorDTO.from(sensorBean.findAll());
    }

    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/{id}") // means: the relative url path is “/api/clients/”
    @Authenticated
    @RolesAllowed("Manager")
    public SensorDTO getSensor(@PathParam("id") Long sensorId) {
        return SensorDTO.from(sensorBean.find(sensorId));
    }

    //List<SensorValueHistory> sensorValueHistories = sensorBean.getSensorHistory(sensorDTO.getCode());
    //                    List<SensorLogDTO> log = SensorLogDTO.from(sensorValueHistories);

    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/{id}/log") // means: the relative url path is “/api/clients/”
    public List<SensorLogDTO> getSensorHistory(@PathParam("id") Long sensorLogId) {
        return SensorLogDTO.from(sensorBean.getSensorHistory(sensorLogId));
    }


    @PATCH
    @Path("/{sensorId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response receiveSensorData(@PathParam("sensorId") Long sensorId,
                                      String value) {
        Sensor sensor = sensorBean.find(sensorId);
        if (sensor == null){
            return Response.status(Response.Status.BAD_REQUEST)
                    .build();
        }
        Volume volume = sensor.getVolume();
        if(volume.getState()== DELIVERED || volume.getState()== RETURNED){
            return Response.status(Response.Status.BAD_REQUEST)
                    .build();
        }
        SensorDataDTO sensorDataDTO = new SensorDataDTO(value,sensorId);
        try {
            sensorBean.createNewSingleValue(sensorDataDTO);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(400,sensorBean.find(sensorDataDTO.getCode()).getType().getName())
                    .build();
        }
    }
}
