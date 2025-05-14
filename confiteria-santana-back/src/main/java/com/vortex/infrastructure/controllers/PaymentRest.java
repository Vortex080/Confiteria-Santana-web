package com.vortex.infrastructure.controllers;

import com.vortex.domain.dto.PaymentsDTO;
import com.vortex.domain.entities.Payments;
import com.vortex.infrastructure.repositories.OrderDAO;
import com.vortex.infrastructure.repositories.PaymentsDAO;
import com.vortex.infrastructure.repositories.UserDAO;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;

@Tag(name = "Payment")
@Path("/payment")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PaymentRest {

    @Inject
    private PaymentsDAO paymentsDAO;
    private UserDAO userDAO;
    private OrderDAO orderDAO;

    @POST
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Operación exitosa"),
            @APIResponse(responseCode = "201", description = "Creado correctamente"),
            @APIResponse(responseCode = "404", description = "No encontrado")
    })
    public Response create(PaymentsDTO dto){
        if (dto.getUser() == null || dto.getOrder() == null || dto.getPaymentMethod() == null || dto.getProvider() == null || dto.getStatus() == null || dto.getCurrency() == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        Payments payment = new Payments();

        if (userDAO.find(dto.getUser()) == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        payment.setUser(userDAO.find(dto.getUser()));

        if (orderDAO.find(dto.getOrder()) == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        payment.setOrder(orderDAO.find(dto.getOrder()));

        payment.setPaymentMethodId(dto.getPaymentMethod());

        payment.setProvider(dto.getProvider());

        payment.setStatus(dto.getStatus());

        payment.setCurrency(dto.getCurrency());

        paymentsDAO.persist(payment);

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
        Payments payment = paymentsDAO.find(id);
        if (payment == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(payment).build();
    }

    @GET
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Operación exitosa"),
            @APIResponse(responseCode = "201", description = "Creado correctamente"),
            @APIResponse(responseCode = "404", description = "No encontrado")
    })
    public Response findAll(){
        List<Payments> list = paymentsDAO.findAll();
        return Response.ok(list).build();
    }

    @DELETE
    @Path("/{id}")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Operación exitosa"),
            @APIResponse(responseCode = "201", description = "Creado correctamente"),
            @APIResponse(responseCode = "404", description = "No encontrado")
    })
    public Response delete(@PathParam("id") Long id){
        Payments payment = paymentsDAO.find(id);

        if(payment == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        paymentsDAO.delete(payment);

        return Response.status(Response.Status.OK).build();
    }

    @PUT
    @Path("/{id}")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Operación exitosa"),
            @APIResponse(responseCode = "201", description = "Creado correctamente"),
            @APIResponse(responseCode = "404", description = "No encontrado")
    })
    public Response update(@PathParam("id") Long id, PaymentsDTO dto){

        Payments payment = paymentsDAO.find(id);

        if (payment == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        if (dto.getUser() == null || dto.getOrder() == null || dto.getPaymentMethod() == null || dto.getProvider() == null || dto.getStatus() == null || dto.getCurrency() == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        if (userDAO.find(dto.getUser()) == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        payment.setUser(userDAO.find(dto.getUser()));

        if (orderDAO.find(dto.getOrder()) == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        payment.setOrder(orderDAO.find(dto.getOrder()));

        payment.setPaymentMethodId(dto.getPaymentMethod());

        payment.setProvider(dto.getProvider());

        payment.setStatus(dto.getStatus());

        payment.setCurrency(dto.getCurrency());

        paymentsDAO.persist(payment);

        return Response.status(Response.Status.CREATED).build();
    }

}
