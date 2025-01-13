package pt.ipleiria.estg.dei.ei.dae.project.ws;


import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.project.dtos.ClientDTO;
import pt.ipleiria.estg.dei.ei.dae.project.dtos.OrderDTO;
import pt.ipleiria.estg.dei.ei.dae.project.ejbs.ClientBean;
import pt.ipleiria.estg.dei.ei.dae.project.ejbs.OrderBean;

import pt.ipleiria.estg.dei.ei.dae.project.exceptions.MyEntityNotFoundException;

import pt.ipleiria.estg.dei.ei.dae.project.security.Authenticated;
import jakarta.ws.rs.core.SecurityContext;

import java.util.List;

@Path("clients") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class ClientService {


    @EJB
    private ClientBean clientBean;

    @EJB
    private OrderBean orderBean;

    @Context
    private SecurityContext securityContext;

    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/") // means: the relative url path is “/api/clients/”
    @Authenticated
    @RolesAllowed({"Manager"})
    public List<ClientDTO> getAllClients() {
        var principal = securityContext.getUserPrincipal();

        System.out.println("Authenticated User (principal.getName()): " + principal.getName());

        return ClientDTO.from(clientBean.findAll());
    }


    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("{id}") // means: the relative url path is “/api/clients/”
    @Authenticated
    @RolesAllowed({"Manager"})

    public Response getClient(@PathParam("id") Long id) throws MyEntityNotFoundException {

        var principal = securityContext.getUserPrincipal();
        if(securityContext.isUserInRole("Manager")){ // || principal.getName().equals(clientDTO.getEmail())){
            var client = clientBean.findWithOrders(id);
            var clientDTO = ClientDTO.from(client);
            clientDTO.setOrdersDTO(OrderDTO.from(client.getOrders()));
            return Response.ok(clientDTO).build();
        }
        return Response.status(Response.Status.FORBIDDEN).build();
    }
}
