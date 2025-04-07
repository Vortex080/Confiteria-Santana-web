package com.vortex.infrastructure.controllers;


import com.vortex.domain.dto.AddressDTO;
import com.vortex.domain.entities.Address;
import com.vortex.infrastructure.repositories.AddressDAO;
import jakarta.inject.Inject;
import jakarta.jms.Message;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/address")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AddressRest {

    @Inject
    private AddressDAO addressDAO;

    @POST
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
    @Path("/idAll")
    public Response getAllAddresses() {return Response.ok(addressDAO.findAll()).build();}


    @DELETE
    @Path("/{id}")
    public Response deleteAddress(@PathParam("id") Long id) {
        Address address = addressDAO.findById(id);
        if (address == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        addressDAO.delete(address);
        return Response.ok().build();
    }



}
