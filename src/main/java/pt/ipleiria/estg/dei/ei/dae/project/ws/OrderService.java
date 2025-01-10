package pt.ipleiria.estg.dei.ei.dae.project.ws;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.jboss.resteasy.plugins.server.servlet.ServletSecurityContext;
import pt.ipleiria.estg.dei.ei.dae.project.dtos.*;
import pt.ipleiria.estg.dei.ei.dae.project.ejbs.ClientBean;
import pt.ipleiria.estg.dei.ei.dae.project.ejbs.OrderBean;
import pt.ipleiria.estg.dei.ei.dae.project.ejbs.UserBean;
import pt.ipleiria.estg.dei.ei.dae.project.entities.User;
import pt.ipleiria.estg.dei.ei.dae.project.security.Authenticated;

import java.util.ArrayList;
import java.util.List;


@Path("orders") // relative url web path for this service
@Authenticated
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class OrderService {
    @EJB
    private ClientBean clientBean;
    @EJB
    private UserBean userBean;

    @EJB
    private OrderBean orderBean;
    @Context
    private SecurityContext securityContext;

    @GET
    @Path("/") // means: the relative URL path is "/orders/"
    public Response getAllOrdersBasedOnRole() {
        // Get the current authenticated user
        String username = securityContext.getUserPrincipal().getName();
        User user = userBean.findOrFail(username);

        // Check the user's role (Client or Manager)
        if (securityContext.isUserInRole("Manager")) {
            // If the user is a Manager, return all orders
            List<OrdersManagerDTO> orders = OrdersManagerDTO.from(orderBean.findAll());
            return Response.ok(orders).build();
        } else if (securityContext.isUserInRole("Client")) {
            // If the user is a Client, return only the orders related to that client
            List<OrdersClientDTO> orders = OrdersClientDTO.from(orderBean.findByClientEmail(user.getEmail()));
            return Response.ok(orders).build();
        } else {
            // Unauthorized access if the user does not have the appropriate role
            return Response.status(Response.Status.FORBIDDEN).entity("Access Denied").build();
        }
    }
}