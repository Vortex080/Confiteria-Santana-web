package com.vortex.infrastructure.controllers;

import java.util.List;

import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import com.vortex.domain.dto.OrderDTO;
import com.vortex.domain.entities.Order;
import com.vortex.domain.entities.OrderMapper;
import com.vortex.infrastructure.repositories.AddressDAO;
import com.vortex.infrastructure.repositories.OrderDAO;
import com.vortex.infrastructure.repositories.SaleDAO;
import com.vortex.infrastructure.repositories.UserDAO;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Tag(name = "Order")
@Path("/order")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderRest {

	@Inject
	private OrderDAO OrderDAO;
	@Inject
	private UserDAO UserDAO;
	@Inject
	private AddressDAO AddressDAO;
	@Inject
	private SaleDAO saleDAO;

	@POST
	@APIResponses({ @APIResponse(responseCode = "200", description = "Operación exitosa"),
			@APIResponse(responseCode = "201", description = "Creado correctamente"),
			@APIResponse(responseCode = "404", description = "No encontrado") })
	public Response create(OrderDTO dto) {
		if (dto.getTotal() == null || dto.getUser() == null || dto.getShipping() == null
				|| dto.getBillingAddress() == null || dto.getPaymentMethod() == null || dto.getSale() == null
				|| dto.getCreated_at() == null) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}

		Order order = new Order();
		order.setTotal(dto.getTotal());

		if (UserDAO.find(dto.getUser()) != null) {
			order.setUser(UserDAO.find(dto.getUser()));
		} else {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}

		order.setShipping(dto.getShipping());
		order.setBillingAddress(AddressDAO.findById(dto.getBillingAddress()));
		order.setSale(saleDAO.findById(dto.getSale()));
		order.setCreated_at(dto.getCreated_at());

		OrderDAO.persist(order);
		return Response.status(Response.Status.CREATED).build();
	}


    /**
     * Obtiene todos los pedidos y los convierte automáticamente a OrderDTO con MapStruct.
     */
    @GET
    public Response findAll() {
        List<Order> entities = OrderDAO.findAll();
        List<OrderDTO> dtos = OrderMapper.toDTOs(entities);
        return Response.ok(dtos).build();
    }

    /**
     * Obtiene un pedido por ID y lo convierte automáticamente a OrderDTO.
     */
    @GET
    @Path("/{id}")
    public Response get(@PathParam("id") Long id) {
        Order order = OrderDAO.find(id);
        if (order == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        OrderDTO dto = OrderMapper.toDTO(order);
        return Response.ok(dto).build();
    }


	@DELETE
	@Path("/{id}")
	@APIResponses({ @APIResponse(responseCode = "200", description = "Operación exitosa"),
			@APIResponse(responseCode = "201", description = "Creado correctamente"),
			@APIResponse(responseCode = "404", description = "No encontrado") })
	public Response delete(@PathParam("id") Long id) {
		Order order = OrderDAO.find(id);
		if (order == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		OrderDAO.delete(order);
		return Response.status(Response.Status.NO_CONTENT).build();
	}

	@PUT
	@Path("/{id}")
	@APIResponses({ @APIResponse(responseCode = "200", description = "Operación exitosa"),
			@APIResponse(responseCode = "201", description = "Creado correctamente"),
			@APIResponse(responseCode = "404", description = "No encontrado") })
	public Response update(@PathParam("id") Long id, OrderDTO dto) {
		Order order = OrderDAO.find(id);
		if (order == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		if (dto.getTotal() == null || dto.getUser() == null || dto.getShipping() == null
				|| dto.getBillingAddress() == null || dto.getPaymentMethod() == null) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}

		order.setTotal(dto.getTotal());

		if (UserDAO.find(dto.getUser()) != null) {
			order.setUser(UserDAO.find(dto.getUser()));
		} else {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}

		order.setShipping(dto.getShipping());
		order.setBillingAddress(AddressDAO.findById(dto.getBillingAddress()));
		order.setSale(saleDAO.findById(dto.getSale()));
		order.setCreated_at(dto.getCreated_at());

		OrderDAO.update(order);

		return Response.ok(order).build();
	}
}
