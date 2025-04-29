package com.vortex.infrastructure.controllers;

import com.vortex.domain.dto.ProductDTO;
import com.vortex.domain.dto.ProductPhotoDTO;
import com.vortex.domain.entities.Product;
import com.vortex.domain.entities.ProductPhoto;
import com.vortex.infrastructure.repositories.CategoryDAO;
import com.vortex.infrastructure.repositories.ProductDAO;
import com.vortex.infrastructure.repositories.ProductPhotoDAO;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;

@Tag(name = "Product")
@Path("/product")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductRest {

    @Inject
    private ProductDAO productDAO;
    private CategoryDAO categoryDAO;
    private ProductPhotoDAO productPhotoDAO;


    @POST
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Operación exitosa"),
            @APIResponse(responseCode = "201", description = "Creado correctamente"),
            @APIResponse(responseCode = "404", description = "No encontrado")
    })
    public Response create(ProductDTO dto){
        if (dto.getName() == null || dto.getDescription() == null || dto.getPrice() == -1 || dto.getUnit() == null || dto.getAlergens() == null || dto.getCategory() == null || dto.getPhotos() == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        Product product = new Product();

        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setUnit(dto.getUnit());
        product.setAlergens(dto.getAlergens());

        if (categoryDAO.findByFields(dto.getCategory()) == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        product.setCategory(categoryDAO.findByFields(dto.getCategory()));

        productDAO.persist(product);

        product = productDAO.findByFields(dto);

        for (ProductPhoto photo : dto.getPhotos()){

            photo.setProduct(product);
            photo.setUrl(dto.getPhotos().get(0).getUrl());
            photo.setAltText(dto.getPhotos().get(0).getAltText());

            productPhotoDAO.persist(photo);
        }

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
        Product product = productDAO.findById(id);

        if (product == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(product).build();
    }


    @GET
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Operación exitosa"),
            @APIResponse(responseCode = "201", description = "Creado correctamente"),
            @APIResponse(responseCode = "404", description = "No encontrado")
    })
    public Response getAll() {
        List<Product> list = productDAO.findAll();
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
        Product product = productDAO.findById(id);

        if (product.getName() == null || product.getDescription() == null || product.getPrice() == -1 || product.getUnit() == null || product.getAlergens() == null || product.getCategory() == null ){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        productDAO.delete(product);

        return Response.status(Response.Status.OK).build();

    }

    @PUT
    @Path("/{id}")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Operación exitosa"),
            @APIResponse(responseCode = "201", description = "Creado correctamente"),
            @APIResponse(responseCode = "404", description = "No encontrado")
    })
    public Response update(@PathParam("id") Long id, ProductDTO dto){

        Product product = productDAO.findById(id);

        if (dto.getName() == null || dto.getDescription() == null || dto.getPrice() == -1 || dto.getUnit() == null || dto.getAlergens() == null || dto.getCategory() == null || dto.getPhotos() == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setUnit(dto.getUnit());
        product.setAlergens(dto.getAlergens());

        if (categoryDAO.findByFields(dto.getCategory()) == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        product.setCategory(categoryDAO.findByFields(dto.getCategory()));

        productDAO.update(product);

        List<ProductPhoto> list = productPhotoDAO.findByProduct(product.getId());


        for (ProductPhoto photo : list){
            for (ProductPhoto photo2 : dto.getPhotos()){
                photo.setUrl(photo2.getUrl());

                photo.setProduct(photo2.getProduct());

                photo.setUrl(photo2.getUrl());

                photo.setAltText(photo2.getAltText());
                productPhotoDAO.update(photo);
            }

        }

        return Response.status(Response.Status.OK).build();

    }

}
