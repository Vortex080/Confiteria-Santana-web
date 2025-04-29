package com.vortex.infrastructure.controllers;


import com.vortex.domain.dto.AddressDTO;
import com.vortex.domain.entities.Address;
import com.vortex.infrastructure.repositories.AddressDAO;
import jakarta.inject.Inject;
import jakarta.jms.Message;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;

@Tag(name = "Address")
@Path("/address")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AddressRest {

    @Inject
    private AddressDAO addressDAO;

    @POST
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Operación exitosa"),
            @APIResponse(responseCode = "201", description = "Creado correctamente"),
            @APIResponse(responseCode = "404", description = "No encontrado")
    })
    public Response addAddress(AddressDTO dto) {
        Address address = new Address();

        address.setStreet(dto.getStreet());
        address.setCity(dto.getCity());
        address.setState(dto.getState());
        address.setDoor(dto.getDoor());
        address.setNumber(dto.getNumber());
        address.setFlat(dto.getFlat());
        address.setCountry(dto.getCountry());
        address.setPostalCode(dto.getPostalCode());

        addressDAO.persist(address);
        return Response.status(Response.Status.CREATED).entity(address).build();
    }

    @GET
    @Path("/{id}")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Operación exitosa"),
            @APIResponse(responseCode = "201", description = "Creado correctamente"),
            @APIResponse(responseCode = "404", description = "No encontrado")
    })
    public Response getAddress(@PathParam("id") Long id) {
        Address address = addressDAO.findById(id);
        if (address == null){
           return Response.status(Response.Status.NOT_FOUND).build();
        }

        AddressDTO dto = new AddressDTO();
        dto.setStreet(address.getStreet());
        dto.setCity(address.getCity());
        dto.setState(address.getState());
        dto.setDoor(address.getDoor());
        dto.setNumber(address.getNumber());
        dto.setFlat(address.getFlat());
        dto.setCountry(address.getCountry());
        dto.setPostalCode(address.getPostalCode());

        return Response.ok(dto).build();

    }

    @GET
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Operación exitosa"),
            @APIResponse(responseCode = "201", description = "Creado correctamente"),
            @APIResponse(responseCode = "404", description = "No encontrado")
    })
    public Response getAllAddresses() {return Response.ok(addressDAO.findAll()).build();}


    @DELETE
    @Path("/{id}")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Operación exitosa"),
            @APIResponse(responseCode = "201", description = "Creado correctamente"),
            @APIResponse(responseCode = "404", description = "No encontrado")
    })
    public Response deleteAddress(@PathParam("id") Long id) {
        Address address = addressDAO.findById(id);
        if (address == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        addressDAO.delete(address);
        return Response.ok().build();
    }



}
