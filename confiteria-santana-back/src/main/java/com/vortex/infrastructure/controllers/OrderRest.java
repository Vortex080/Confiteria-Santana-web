package com.vortex.infrastructure.controllers;

import com.vortex.domain.dto.OrderDTO;
import com.vortex.domain.dto.UserDTO;
import com.vortex.domain.entities.Order;
import com.vortex.infrastructure.repositories.AddressDAO;
import com.vortex.infrastructure.repositories.OrderDAO;
import com.vortex.infrastructure.repositories.UserDAO;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;

@Tag(name = "Order")
@Path("/order")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderRest {

    @Inject
    private OrderDAO OrderDAO;
    private UserDAO UserDAO;
    private AddressDAO AddressDAO;

    @POST
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Operación exitosa"),
            @APIResponse(responseCode = "201", description = "Creado correctamente"),
            @APIResponse(responseCode = "404", description = "No encontrado")
    })
    public Response create(OrderDTO dto) {
        if (dto.getTotal() == null || dto.getUser() == null || dto.getShipping() == null || dto.getBillingAddress() == null || dto.getPaymentMethod() == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
 
        Order order = new Order();
        order.setTotal(dto.getTotal());

        if (UserDAO.findByEmail(dto.getUser().getEmail()) != null) {
            order.setUser(UserDAO.findByEmail(dto.getUser().getEmail()));
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        order.setShipping(dto.getShipping());
        if(dto.getBillingAddress() == null) {
            order.setBillingAddress(AddressDAO.findByFields(dto.getBillingAddress()));
        }

        OrderDAO.persist(order);
        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Operación exitosa"),
            @APIResponse(responseCode = "201", description = "Creado correctamente"),
            @APIResponse(responseCode = "404", description = "No encontrado")
    })
    public Response findAll() {
        List<Order> list = OrderDAO.findAll();
        return Response.ok(list).build();
    }

    @GET
    @Path("/{id}")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Operación exitosa"),
            @APIResponse(responseCode = "201", description = "Creado correctamente"),
            @APIResponse(responseCode = "404", description = "No encontrado")
    })
    public Response get(@PathParam("id") Long id) {
        Order order = OrderDAO.find(id);
        if (order == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(order).build();
    }

    @DELETE
    @Path("/{id}")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Operación exitosa"),
            @APIResponse(responseCode = "201", description = "Creado correctamente"),
            @APIResponse(responseCode = "404", description = "No encontrado")
    })
    public Response delete(@PathParam("id") Long id) {
        Order order = OrderDAO.find(id);
        if (order == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        OrderDAO.delete(order);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @PUT
    @Path("/{id}")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Operación exitosa"),
            @APIResponse(responseCode = "201", description = "Creado correctamente"),
            @APIResponse(responseCode = "404", description = "No encontrado")
    })
    public Response update(@PathParam("id") Long id, OrderDTO dto) {
        Order order = OrderDAO.find(id);
        if (order == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        if (dto.getTotal() == null || dto.getUser() == null || dto.getShipping() == null || dto.getBillingAddress() == null || dto.getPaymentMethod() == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        order.setTotal(dto.getTotal());

        if (UserDAO.findByEmail(dto.getUser().getEmail()) != null) {
            order.setUser(UserDAO.findByEmail(dto.getUser().getEmail()));
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        order.setShipping(dto.getShipping());
        if(dto.getBillingAddress() == null) {
            order.setBillingAddress(AddressDAO.findByFields(dto.getBillingAddress()));
        }

        OrderDAO.update(order);

        return Response.ok(order).build();
    }
}
