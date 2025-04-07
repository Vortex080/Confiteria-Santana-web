package com.vortex.infrastructure.controllers;

import com.vortex.domain.dto.AlergenDTO;
import com.vortex.domain.entities.Alergens;
import com.vortex.infrastructure.repositories.AlergensDAO;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

public class AlergenRest {

    @Inject
    private AlergensDAO alergenDAO;

    @POST
    public Response create(AlergenDTO alergenDTO) {
        if(alergenDTO.getName() == null || alergenDTO.getPhoto() == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        Alergens alergen = new Alergens();
        alergen.setName(alergenDTO.getName());
        alergen.setPhoto(alergenDTO.getPhoto());

        alergenDAO.persist(alergen);

        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @Path("/{id}")
    public Response get(@PathParam("id") Long id) {

    }


}
