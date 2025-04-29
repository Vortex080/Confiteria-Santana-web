package com.vortex.infrastructure.controllers;

import com.vortex.domain.dto.AlergenDTO;
import com.vortex.domain.entities.Alergens;
import com.vortex.infrastructure.repositories.AlergensDAO;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;

@Tag(name = "Alegen")
@Path("/alergen")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AlergenRest {

    @Inject
    private AlergensDAO alergenDAO;

    @POST
    @Path("/{id}")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Operación exitosa"),
            @APIResponse(responseCode = "201", description = "Creado correctamente"),
            @APIResponse(responseCode = "404", description = "No encontrado")
    })
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
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Operación exitosa"),
            @APIResponse(responseCode = "201", description = "Creado correctamente"),
            @APIResponse(responseCode = "404", description = "No encontrado")
    })
    public Response findAll() {
        List<Alergens> alergens = alergenDAO.findAll();
        return Response.ok(alergens).build();
    }


    @GET
    @Path("/{id}")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Operación exitosa"),
            @APIResponse(responseCode = "201", description = "Creado correctamente"),
            @APIResponse(responseCode = "404", description = "No encontrado")
    })
    public Response get(@PathParam("id") Long id) {
        Alergens alergen = alergenDAO.find(id);

        if (alergen == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(alergen).build();
    }

    @DELETE
    @Path("/{id}")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Operación exitosa"),
            @APIResponse(responseCode = "201", description = "Creado correctamente"),
            @APIResponse(responseCode = "404", description = "No encontrado")
    })
    public Response delete(@PathParam("id") Long id) {
        Alergens alergen = alergenDAO.find(id);

        if (alergen == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        alergenDAO.delete(alergen);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @PUT
    @Path("/{id}")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Operación exitosa"),
            @APIResponse(responseCode = "201", description = "Creado correctamente"),
            @APIResponse(responseCode = "404", description = "No encontrado")
    })
    public Response update(@PathParam("id") Long id, AlergenDTO alergenDTO) {
        Alergens alergen = alergenDAO.find(id);

        if (alergen == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        alergen.setName(alergenDTO.getName());
        alergen.setPhoto(alergenDTO.getPhoto());
        alergenDAO.update(alergen);

        return Response.ok(alergen).build();
    }
}