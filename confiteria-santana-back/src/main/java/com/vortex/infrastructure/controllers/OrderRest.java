package com.vortex.infrastructure.controllers;

import com.vortex.domain.dto.OrderDTO;
import com.vortex.domain.dto.UserDTO;
import com.vortex.domain.entities.Order;
import com.vortex.infrastructure.repositories.OrderDAO;
import com.vortex.infrastructure.repositories.UserDAO;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/Order")
public class OrderRest {

    @Inject
    private OrderDAO OrderDAO;
    private UserDAO UserDAO;

    @POST
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
        order.setBillingAddress(dto.getBillingAddress());


        OrderDAO.persist(order);
        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    public Response findAll() {
        List<Order> list = OrderDAO.findAll();
        return Response.ok(list).build();
    }

    @GET
    @Path("/{id}")
    public Response get(@PathParam("id") Long id) {
        Order order = OrderDAO.find(id);
        if (order == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(order).build();
    }

    @DELETE
    @Path("/{id}")
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
    public Response update(@PathParam("id") Long id, OrderDTO dto) {
        Order order = OrderDAO.find(id);
        if (order == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        order.setDate(dto.getDate());
        order.setState(dto.getState());
        order.setUser(dto.getUser());
        OrderDAO.update(order);

        return Response.ok(order).build();
    }
}
