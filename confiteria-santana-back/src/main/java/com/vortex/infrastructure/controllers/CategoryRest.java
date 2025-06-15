package com.vortex.infrastructure.controllers;

import com.vortex.domain.dto.CategoryDTO;
import com.vortex.domain.entities.Category;
import com.vortex.infrastructure.repositories.CategoryDAO;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;

@Tag(name = "Category")
@Path("/category")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CategoryRest {

    @Inject
    private CategoryDAO categoryDAO;

    @POST
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Operación exitosa"),
            @APIResponse(responseCode = "201", description = "Creado correctamente"),
            @APIResponse(responseCode = "404", description = "No encontrado")
    })
    public Response create(CategoryDTO categoryDTO) {
        if (categoryDTO.getName() == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        Category category = new Category();
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());

        categoryDAO.persist(category);

        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Operación exitosa"),
            @APIResponse(responseCode = "201", description = "Creado correctamente"),
            @APIResponse(responseCode = "404", description = "No encontrado")
    })
    public Response findAll() {
        List<Category> Category = categoryDAO.findAll();
        return Response.ok(Category).build();
    }

    @GET
    @Path("/{id}")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Operación exitosa"),
            @APIResponse(responseCode = "201", description = "Creado correctamente"),
            @APIResponse(responseCode = "404", description = "No encontrado")
    })
    public Response get(@PathParam("id") Long id) {
        Category category = categoryDAO.find(id);
        if (category == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(category).build();
    }

    @DELETE
    @Path("/{id}")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Operación exitosa"),
            @APIResponse(responseCode = "201", description = "Creado correctamente"),
            @APIResponse(responseCode = "404", description = "No encontrado")
    })
    public Response delete(@PathParam("id") Long id) {
        Category category = categoryDAO.find(id);
        if (category == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        categoryDAO.delete(category);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @PUT
    @Path("/{id}")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Operación exitosa"),
            @APIResponse(responseCode = "201", description = "Creado correctamente"),
            @APIResponse(responseCode = "404", description = "No encontrado")
    })
    public Response update(@PathParam("id") Long id, CategoryDTO categoryDTO) {
        Category category = categoryDAO.find(id);
        if (category == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        categoryDAO.update(category);

        return Response.ok(category).build();
    }
}
