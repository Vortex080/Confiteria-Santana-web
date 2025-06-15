package com.vortex.infrastructure.controllers;

import com.vortex.domain.dto.StockMovementsDTO;
import com.vortex.domain.entities.Payments;
import com.vortex.domain.entities.Product;
import com.vortex.domain.entities.Stock;
import com.vortex.domain.entities.StockMovements;
import com.vortex.domain.enums.MovementReason;
import com.vortex.infrastructure.repositories.ProductDAO;
import com.vortex.infrastructure.repositories.StockDAO;
import com.vortex.infrastructure.repositories.StockMovementsDAO;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "StockMovements")
@Path("/stockmovements")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StockMovementsRest {

    @Inject
    private StockMovementsDAO dao;
    @Inject
    private ProductDAO productDAO;
    @Inject
    private StockDAO stockDAO;

    @POST
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Operación exitosa"),
            @APIResponse(responseCode = "201", description = "Creado correctamente"),
            @APIResponse(responseCode = "404", description = "No encontrado")
    })
    public Response create(StockMovementsDTO dto){
        if (dto.getUnit() == null || dto.getProduct() == null || dto.getReason() == null || dto.getType() == null ) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        Product product = productDAO.findById(dto.getProduct());
        if (product == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        // Crear y guardar el movimiento
        StockMovements stock = new StockMovements();
        stock.setProduct(product);
        stock.setUnit(dto.getUnit());
        stock.setType(dto.getType());
        stock.setReason(dto.getReason());
        stock.setCreated_at(LocalDateTime.now());


        dao.persist(stock);

        Stock stockraw = stockDAO.findByProductId(product.getId());


        if (dto.getReason() == MovementReason.AÑADIDO) {
            stockraw.setQuantity(stockraw.getQuantity() + dto.getQuantity());
        } else {
            stockraw.setQuantity(stockraw.getQuantity() - dto.getQuantity());
        }

        stockDAO.update(stockraw);

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
        StockMovements stock = dao.find(id);
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
        List<StockMovements> list = dao.findAll();
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
        StockMovements stock = dao.find(id);

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
    public Response update(@PathParam("id") Long id, StockMovementsDTO dto){
        if (dto.getUnit() == null || dto.getProduct() == null || dto.getReason() == null || dto.getType() == null ) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        StockMovements stock = dao.find(id);

        Product product = productDAO.findById(dto.getProduct());

        stock.setProduct(product);
        stock.setUnit(dto.getUnit());
        stock.setType(dto.getType());
        stock.setReason(dto.getReason());

        dao.persist(stock);

        return Response.status(Response.Status.OK).build();
    }

}


