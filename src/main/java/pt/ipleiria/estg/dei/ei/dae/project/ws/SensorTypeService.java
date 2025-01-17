package pt.ipleiria.estg.dei.ei.dae.project.ws;


import jakarta.ejb.EJB;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import pt.ipleiria.estg.dei.ei.dae.project.dtos.SimpleSensorTypeDTO;
import pt.ipleiria.estg.dei.ei.dae.project.ejbs.SensorTypeBean;
import pt.ipleiria.estg.dei.ei.dae.project.security.Authenticated;

import java.util.List;

@Path("sensor-types") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON})
public class SensorTypeService {

    @EJB
    private SensorTypeBean sensorTypeBean;

    @GET
    @Path("/")
    public List<SimpleSensorTypeDTO> getAllSensors() {
        return SimpleSensorTypeDTO.from(sensorTypeBean.findAll());
    }
}
