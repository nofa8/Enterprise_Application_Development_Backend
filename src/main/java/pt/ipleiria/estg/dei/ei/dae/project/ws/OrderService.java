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
import pt.ipleiria.estg.dei.ei.dae.project.ejbs.ClientBean;
import pt.ipleiria.estg.dei.ei.dae.project.ejbs.OrderBean;
import pt.ipleiria.estg.dei.ei.dae.project.ejbs.UserBean;
import pt.ipleiria.estg.dei.ei.dae.project.ejbs.VolumeBean;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Order;
import pt.ipleiria.estg.dei.ei.dae.project.entities.User;
import pt.ipleiria.estg.dei.ei.dae.project.entities.enums.OrderState;
import pt.ipleiria.estg.dei.ei.dae.project.entities.enums.VolumeState;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.project.security.Authenticated;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Path("orders") // relative url web path for this service
//@Authenticated
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
    @Authenticated
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

    @GET
    @Path("{code}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    @Authenticated
    public Response getOrderBasedOnRole(@PathParam("code") long code) throws MyEntityNotFoundException {
        String username = securityContext.getUserPrincipal().getName();
        User user = userBean.findOrFail(username);
        Order order = orderBean.find(code);

        if (securityContext.isUserInRole("Manager")) {
            return Response.ok(OrderManagerDTO.from(order)).build();
        } else if (securityContext.isUserInRole("Client")) {
            if (order.getClient().getEmail().equals(user.getEmail())) {
                return Response.ok(OrderClientDTO.from(order)).build();
            } else {
                return Response.status(Response.Status.FORBIDDEN)
                        .entity("Access Denied: You can only view your own orders")
                        .build();
            }
        } else {
            return Response.status(Response.Status.FORBIDDEN)
                    .entity("Access Denied")
                    .build();
        }
    }


    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createNewOrder (OrderDTO orderDTO) throws MyEntityExistsException, MyEntityNotFoundException, MyConstraintViolationException {
        orderBean.create(
                orderDTO.getCode(),
                orderDTO.getPrice(),
                orderDTO.getState(),
                orderDTO.getPurchaseDate(),
                Date.from(Instant.now()),
                orderDTO.getClientId()
        );

        Order newOrder = orderBean.find(orderDTO.getCode());
        return Response.status(Response.Status.CREATED)
                .entity(OrderDTO.from(newOrder))
                .build();
    }


    @PATCH
    @Path("/{id:code_order}")
    public Response patchOrder(@PathParam("id:code_order") Long orderId,
                                OrderState state)
            throws MyEntityNotFoundException, MyConstraintViolationException {


        orderBean.patchState(orderId,state);
        return Response.ok().build();
    }
}