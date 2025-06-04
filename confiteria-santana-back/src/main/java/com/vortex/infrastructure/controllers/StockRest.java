package com.vortex.infrastructure.controllers;

import com.vortex.domain.dto.PaymentsDTO;
import com.vortex.domain.dto.StockDTO;
import com.vortex.domain.entities.Payments;
import com.vortex.domain.entities.Product;
import com.vortex.domain.entities.Stock;
import com.vortex.infrastructure.repositories.ProductDAO;
import com.vortex.infrastructure.repositories.StockDAO;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;

@Tag(name = "Stock")
@Path("/stock")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StockRest {

    @Inject
    private StockDAO dao;
    @Inject
    private ProductDAO productDAO;

    @POST
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Operación exitosa"),
            @APIResponse(responseCode = "201", description = "Creado correctamente"),
            @APIResponse(responseCode = "404", description = "No encontrado")
    })
    public Response create(StockDTO dto){
        if (dto.getProduct() == null || dto.getQuantity() == -1 ) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        Stock stock = new Stock();

        Product product = productDAO.findById(dto.getProduct());

        stock.setProduct(product);
        stock.setQuantity(dto.getQuantity());

        dao.persist(stock);

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

        Stock stock = dao.find(id);
        if (stock == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(stock).build();
    }

    @GET
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Operación exitosa"),
            @APIResponse(responseCode = "201", description = "Creado correctamente"),
            @APIResponse(responseCode = "404", description = "No encontrado")
    })
    public Response findAll(){
        List<Stock> list = dao.findAll();
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
        Stock stock = dao.find(id);

        if (stock == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        dao.delete(stock);

        return Response.status(Response.Status.OK).build();
    }

    @PUT
    @Path("/{id}")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Operación exitosa"),
            @APIResponse(responseCode = "201", description = "Creado correctamente"),
            @APIResponse(responseCode = "404", description = "No encontrado")
    })
    public Response update(@PathParam("id") Long id, StockDTO dto){
        if (dto.getProduct() == null || dto.getQuantity() == -1 ) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        Stock stock = dao.find(id);

        Product product = productDAO.findById(dto.getProduct());

        stock.setProduct(product);
        stock.setQuantity(dto.getQuantity());

        dao.update(stock);

        return Response.status(Response.Status.OK).build();
    }



}
