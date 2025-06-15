package com.vortex.infrastructure.controllers;

import com.vortex.domain.dto.PaymentsDTO;
import com.vortex.domain.dto.ShippingTrackingDTO;
import com.vortex.domain.entities.Order;
import com.vortex.domain.entities.Payments;
import com.vortex.domain.entities.ShippingTracking;
import com.vortex.infrastructure.repositories.OrderDAO;
import com.vortex.infrastructure.repositories.ShippingTrackingDAO;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;

@Tag(name = "ShippingTracking")
@Path("/shippingtracking")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ShippingTrackingRest {

    @Inject
    private ShippingTrackingDAO dao;
    @Inject
    private OrderDAO orderDAO;

    @POST
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Operación exitosa"),
            @APIResponse(responseCode = "201", description = "Creado correctamente"),
            @APIResponse(responseCode = "404", description = "No encontrado")
    })
    public Response create(ShippingTrackingDTO dto){
        if (dto.getCarrier() == null || dto.getOrder() == null || dto.getStatus() == null || dto.getTrakingNumber() == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        ShippingTracking shipping = new ShippingTracking();

        shipping.setCarrier(dto.getCarrier());
        Order order = orderDAO.find(dto.getOrder());
        shipping.setOrder(order);
        shipping.setStatus(dto.getStatus());
        shipping.setTracking_number(dto.getTrakingNumber());

        dao.persist(shipping);

        return Response.status(Response.Status.CREATED).build();

    }

    @GET
    @Path("/{id}")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Operación exitosa"),
            @APIResponse(responseCode = "201", description = "Creado correctamente"),
            @APIResponse(responseCode = "404", description = "No encontrado")
    })
    public Response get(@PathParam("id") Long id){
        ShippingTracking shipping = dao.find(id);
        if ( shipping == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(shipping).build();

    }

    @DELETE
    @Path("/{id}")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Operación exitosa"),
            @APIResponse(responseCode = "201", description = "Creado correctamente"),
            @APIResponse(responseCode = "404", description = "No encontrado")
    })
    public Response delete(@PathParam("id") Long id){
        ShippingTracking shipping = dao.find(id);
        if ( shipping == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        dao.delete(shipping);
        return Response.status(Response.Status.OK).build();
    }

    @GET
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Operación exitosa"),
            @APIResponse(responseCode = "201", description = "Creado correctamente"),
            @APIResponse(responseCode = "404", description = "No encontrado")
    })
    public Response findAll(){
        List<ShippingTracking> list = dao.findAll();
        return Response.ok(list).build();
    }

    @PUT
    @Path("/{id}")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Operación exitosa"),
            @APIResponse(responseCode = "201", description = "Creado correctamente"),
            @APIResponse(responseCode = "404", description = "No encontrado")
    })
    public Response update(@PathParam("id") Long id, ShippingTrackingDTO dto){
        if (dto.getCarrier() == null || dto.getOrder() == null || dto.getStatus() == null || dto.getTrakingNumber() == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        ShippingTracking shipping = dao.find(id);

        shipping.setCarrier(dto.getCarrier());
        Order order = orderDAO.find(dto.getOrder());
        shipping.setOrder(order);
        shipping.setStatus(dto.getStatus());
        shipping.setTracking_number(dto.getTrakingNumber());

        dao.update(shipping);

        return Response.status(Response.Status.CREATED).build();
    }
}
