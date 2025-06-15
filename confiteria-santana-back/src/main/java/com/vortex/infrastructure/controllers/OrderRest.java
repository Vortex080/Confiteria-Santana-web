package com.vortex.infrastructure.controllers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vortex.domain.dto.OrderCreateDTO;
import com.vortex.domain.dto.OrderDTO;
import com.vortex.domain.dto.PaymentMethodDTO;
import com.vortex.domain.entities.Order;
import com.vortex.domain.entities.OrderMapper;
import com.vortex.domain.entities.PaymentMethod;
import com.vortex.domain.entities.Product;
import com.vortex.domain.entities.ProductMapper;
import com.vortex.domain.entities.Sale;
import com.vortex.domain.entities.SaleLine;
import com.vortex.domain.entities.User;
import com.vortex.infrastructure.repositories.AddressDAO;
import com.vortex.infrastructure.repositories.OrderDAO;
import com.vortex.infrastructure.repositories.PaymentMethodDAO;
import com.vortex.infrastructure.repositories.ProductDAO;
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
	@Inject
	private ProductDAO productDAO;
	@Inject
	private PaymentMethodDAO paymentDAO;

	@POST
	@APIResponses({ @APIResponse(responseCode = "200", description = "Operación exitosa"),
			@APIResponse(responseCode = "201", description = "Creado correctamente"),
			@APIResponse(responseCode = "404", description = "No encontrado") })
	public Response create(OrderCreateDTO dto) {
		// Validación de campos obligatorios
		if (dto.getUser() == null || dto.getPaymentMethod() == null || dto.getSale() == null
				|| dto.getSale().getLine() == null || dto.getSale().getLine().isEmpty() || dto.getShipping() == null
				|| dto.getBillingAddress() == null || dto.getTotal() == null || dto.getCreated_at() == null) {
			return Response.status(Response.Status.BAD_REQUEST).entity("Faltan campos obligatorios en el pedido")
					.build();
		}

		// 1. Verificar que el usuario principal del pedido existe
		User user = UserDAO.find(dto.getUser().getId());
		if (user == null) {
			return Response.status(Response.Status.BAD_REQUEST).entity("Usuario del pedido no válido").build();
		}

		// 2. Buscar método de pago por ID
		PaymentMethodDTO pmDTO = dto.getPaymentMethod();
		if (pmDTO.getId() == null) {
			return Response.status(Response.Status.BAD_REQUEST).entity("ID del método de pago no proporcionado")
					.build();
		}

		PaymentMethod pm = paymentDAO.find(pmDTO.getId());
		if (pm == null) {
			return Response.status(Response.Status.BAD_REQUEST).entity("Método de pago no válido").build();
		}

		// 3. Procesar la venta y sus líneas
		Sale sale = new Sale();
		sale.setDate(dto.getSale().getDate());
		sale.setMetodoPago(pm.getBrand());
		sale.setTotal(dto.getSale().getTotal());

		List<SaleLine> lineas = new ArrayList<>();
		for (SaleLine lineDTO : dto.getSale().getLine()) {

			Product prod = productDAO.findById(lineDTO.getProduct().getId());
			if (prod == null) {
				return Response.status(Response.Status.BAD_REQUEST)
						.entity("Producto con ID inválido: " + lineDTO.getProduct().getId()).build();
			}

			SaleLine line = new SaleLine();

			line.setProduct(ProductMapper.toDTO(prod)); // ✅ ahora es ProductDTO
			line.setCuantity(lineDTO.getCuantity());
			line.setPrice(lineDTO.getPrice());
			line.setSubtotal(lineDTO.getSubtotal());
			line.setSale(sale);
			lineas.add(line);
		}

		sale.setLine(lineas);
		saleDAO.persist(sale);

		// 4. Convertir direcciones a JSON
		ObjectMapper mapper = new ObjectMapper();
		String shippingJson, billingJson;
		try {
			shippingJson = mapper.writeValueAsString(dto.getShipping());
			billingJson = mapper.writeValueAsString(dto.getBillingAddress());
		} catch (JsonProcessingException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("Error al procesar direcciones como JSON").build();
		}

		// 5. Crear y guardar el pedido
		Order order = new Order();
		order.setUser(user);
		order.setPaymentMethod(pm); // ya buscado en la BD
		order.setSale(sale);
		order.setTotal(dto.getTotal());
		order.setShippingAddress(shippingJson);
		order.setBillingAddress(billingJson);
		order.setCreated_at(dto.getCreated_at());

		OrderDAO.persist(order);

		return Response.status(Response.Status.CREATED).build();
	}

	/**
	 * Obtiene todos los pedidos y los convierte automáticamente a OrderDTO con
	 * MapStruct.
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
	@Produces(MediaType.APPLICATION_JSON)
	public Response getById(@PathParam("id") Long id) {
		try {
			Order order = OrderDAO.find(id);
			if (order == null) {
				return Response.status(Response.Status.NOT_FOUND).build();
			}

			OrderDTO dto = OrderMapper.toDTO(order);
			return Response.ok(dto).build(); // ✅ Devuelve solo datos planos

		} catch (Exception e) {
			e.printStackTrace(); // Muestra el stacktrace en consola/log
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("Error al convertir el pedido: " + e.getMessage()).build();
		}

	}

	@DELETE
	@Path("/{id}")
	public Response delete(@PathParam("id") Long id) {
	    boolean deleted = OrderDAO.deleteById(id);
	    if (!deleted) {
	        return Response.status(Response.Status.NOT_FOUND).build();
	    }
	    return Response.status(Response.Status.NO_CONTENT).build();
	}


	@PUT
	@Path("/{id}")
	@APIResponses({
	    @APIResponse(responseCode = "200", description = "Pedido actualizado correctamente"),
	    @APIResponse(responseCode = "404", description = "Pedido no encontrado")
	})
	public Response update(@PathParam("id") Long id, OrderCreateDTO dto) {
	    Order order = OrderDAO.find(id);
	    if (order == null) {
	        return Response.status(Response.Status.NOT_FOUND).build();
	    }

	    // Validaciones básicas
	    if (dto.getUser() == null || dto.getPaymentMethod() == null || dto.getSale() == null
	            || dto.getSale().getLine() == null || dto.getSale().getLine().isEmpty()
	            || dto.getShipping() == null || dto.getBillingAddress() == null
	            || dto.getTotal() == null || dto.getCreated_at() == null) {
	        return Response.status(Response.Status.BAD_REQUEST).entity("Faltan campos obligatorios").build();
	    }

	    // Validar usuario
	    User user = UserDAO.find(dto.getUser().getId());
	    if (user == null) {
	        return Response.status(Response.Status.BAD_REQUEST).entity("Usuario no válido").build();
	    }

	    // Validar método de pago
	    PaymentMethod pm = paymentDAO.find(dto.getPaymentMethod().getId());
	    if (pm == null) {
	        return Response.status(Response.Status.BAD_REQUEST).entity("Método de pago no válido").build();
	    }

	    // Actualizar venta
	    Sale venta = order.getSale();
	    venta.setDate(dto.getSale().getDate());
	    venta.setMetodoPago(pm.getBrand());
	    venta.setTotal(dto.getSale().getTotal());

	    // Limpiar líneas antiguas y reemplazar
	    venta.getLine().clear();
	    for (SaleLine l : dto.getSale().getLine()) {
	        Product prod = productDAO.findById(l.getProduct().getId());
	        if (prod == null) {
	            return Response.status(Response.Status.BAD_REQUEST)
	                    .entity("Producto con ID inválido: " + l.getProduct().getId()).build();
	        }

	        SaleLine nuevaLinea = new SaleLine();
	        nuevaLinea.setProduct(ProductMapper.toDTO(prod));
	        nuevaLinea.setCuantity(l.getCuantity());
	        nuevaLinea.setPrice(l.getPrice());
	        nuevaLinea.setSubtotal(l.getSubtotal());
	        nuevaLinea.setSale(venta);
	        venta.getLine().add(nuevaLinea);
	    }

	    saleDAO.update(venta); // asegúrate de que actualice correctamente si ya existía

	    // Convertir direcciones a JSON
	    ObjectMapper mapper = new ObjectMapper();
	    String shippingJson, billingJson;
	    try {
	        shippingJson = mapper.writeValueAsString(dto.getShipping());
	        billingJson = mapper.writeValueAsString(dto.getBillingAddress());
	    } catch (JsonProcessingException e) {
	        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
	                .entity("Error al procesar direcciones como JSON").build();
	    }

	    // Actualizar campos del pedido
	    order.setUser(user);
	    order.setPaymentMethod(pm);
	    order.setSale(venta);
	    order.setTotal(dto.getTotal());
	    order.setShippingAddress(shippingJson);
	    order.setBillingAddress(billingJson);
	    order.setCreated_at(dto.getCreated_at());

	    OrderDAO.update(order);

	    return Response.ok().build();
	}


}
