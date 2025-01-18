package pt.ipleiria.estg.dei.ei.dae.project.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import pt.ipleiria.estg.dei.ei.dae.project.dtos.SimplePackageTypeDTO;
import pt.ipleiria.estg.dei.ei.dae.project.ejbs.PackageTypeBean;

import java.util.List;

@Path("package-types") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON})
public class PackageTypeService {

    @EJB
    private PackageTypeBean packageTypeBean;

    @GET
    @Path("/")
    public List<SimplePackageTypeDTO> getAllPackages() {
        return SimplePackageTypeDTO.from(packageTypeBean.findAll());
    }
}
