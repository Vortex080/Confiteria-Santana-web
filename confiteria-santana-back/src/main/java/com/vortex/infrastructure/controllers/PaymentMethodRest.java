package com.vortex.infrastructure.controllers;

import com.vortex.domain.dto.PaymentMethodDTO;
import com.vortex.domain.entities.Order;
import com.vortex.domain.entities.PaymentMethod;
import com.vortex.infrastructure.repositories.PaymentMethodDAO;
import com.vortex.infrastructure.repositories.UserDAO;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;

@Tag(name = "PaymentMethod")
@Path("/paymentmethod")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PaymentMethodRest {

    @Inject
    private PaymentMethodDAO paymentMethodDAO;
    private UserDAO userDAO;

    @POST
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Operación exitosa"),
            @APIResponse(responseCode = "201", description = "Creado correctamente"),
            @APIResponse(responseCode = "404", description = "No encontrado")
    })
    public Response create(PaymentMethodDTO dto){
        if (dto.getUser() == null || dto.getExpiryMonth() == -1 || dto.getExpiryYear() == -1 || dto.getProvider() == null || dto.getToken() == null || dto.getLast4() == null || dto.getType() == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        PaymentMethod payment = new PaymentMethod();

        if(userDAO.find(dto.getUser()) == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        payment.setUser(userDAO.find(dto.getUser()));

        payment.setExpiryMonth(dto.getExpiryMonth());

        payment.setExpiryYear(dto.getExpiryYear());

        payment.setProvider(dto.getProvider());

        payment.setToken(dto.getToken());

        payment.setLast4(dto.getLast4());

        payment.setType(dto.getType());

        paymentMethodDAO.persist(payment);

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
        PaymentMethod payment = paymentMethodDAO.find(id);
        if (payment == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(payment).build();
    }

    @GET
    public Response findAll(){
        List<PaymentMethod> list = paymentMethodDAO.findAll();
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
        PaymentMethod payment = paymentMethodDAO.find(id);
        if (payment == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        paymentMethodDAO.delete(payment);

        return Response.status(Response.Status.OK).build();
    }

    @PUT
    @Path("/{id}")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Operación exitosa"),
            @APIResponse(responseCode = "201", description = "Creado correctamente"),
            @APIResponse(responseCode = "404", description = "No encontrado")
    })
    public Response update(@PathParam("id") Long id, PaymentMethodDTO dto){
        PaymentMethod payment = paymentMethodDAO.find(id);
        if (payment == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        if (dto.getUser() == null || dto.getExpiryMonth() == -1 || dto.getExpiryYear() == -1 || dto.getProvider() == null || dto.getToken() == null || dto.getLast4() == null || dto.getType() == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        if(userDAO.find(dto.getUser()) == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        payment.setUser(userDAO.find(dto.getUser()));

        payment.setExpiryMonth(dto.getExpiryMonth());

        payment.setExpiryYear(dto.getExpiryYear());

        payment.setProvider(dto.getProvider());

        payment.setToken(dto.getToken());

        payment.setLast4(dto.getLast4());

        payment.setType(dto.getType());

        return Response.status(Response.Status.OK).build();
    }
}
