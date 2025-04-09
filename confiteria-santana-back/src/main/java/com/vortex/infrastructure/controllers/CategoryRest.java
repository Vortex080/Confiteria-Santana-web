package com.vortex.infrastructure.controllers;

import com.vortex.domain.dto.CategoryDTO;
import com.vortex.domain.entities.Category;
import com.vortex.infrastructure.repositories.CategoryDAO;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/Category")
public class CategoryRest {

    @Inject
    private CategoryDAO categoryDAO;

    @POST
    public Response create(CategoryDTO categoryDTO) {
        if (categoryDTO.getName() == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        Category category = new Category();
        category.setName(categoryDTO.getName());

        categoryDAO.persist(category);

        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    public Response findAll() {
        List<Category> Category = categoryDAO.findAll();
        return Response.ok(Category).build();
    }

    @GET
    @Path("/{id}")
    public Response get(@PathParam("id") Long id) {
        Category category = categoryDAO.find(id);
        if (category == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(category).build();
    }

    @DELETE
    @Path("/{id}")
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
    public Response update(@PathParam("id") Long id, CategoryDTO categoryDTO) {
        Category category = categoryDAO.find(id);
        if (category == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        category.setName(categoryDTO.getName());
        categoryDAO.update(category);

        return Response.ok(category).build();
    }
}
