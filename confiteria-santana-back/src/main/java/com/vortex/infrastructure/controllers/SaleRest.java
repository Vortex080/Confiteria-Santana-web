package com.vortex.infrastructure.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.vortex.domain.entities.*;
import com.vortex.domain.enums.MovementReason;
import com.vortex.domain.enums.MovementType;
import com.vortex.infrastructure.repositories.*;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import com.vortex.domain.dto.ProductDTO;
import com.vortex.domain.dto.SaleDTO;
import com.vortex.domain.dto.SaleLineDTO;

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

@Tag(name = "Sale")
@Path("/sale")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SaleRest {

	@Inject
	private SaleDAO dao;
	@Inject
	private AlergensDAO alergens;
	@Inject
	private SaleLineDAO saleline;
	@Inject
	private ProductDAO productDAO;
	@Inject
	private StockMovementsDAO stockMoveDAO;
	@Inject
	private StockDAO stockDAO;

	@POST
	@APIResponses({ @APIResponse(responseCode = "200", description = "Operación exitosa"),
			@APIResponse(responseCode = "201", description = "Creado correctamente"),
			@APIResponse(responseCode = "404", description = "No encontrado") })
	public Response addSale(SaleDTO dto) {

		if (dto.getDate() == null || dto.getLine() == null || dto.getMetodoPago() == null || dto.getTotal() == null) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}

		Sale sale = new Sale();

		sale.setDate(dto.getDate());
		sale.setMetodoPago(dto.getMetodoPago());
		sale.setTotal(dto.getTotal());

		List<SaleLine> lineas = new ArrayList<>();

		for (SaleLineDTO lineDTO : dto.getLine()) {


			if (productDAO.findById(lineDTO.getProduct().getId()) == null) {
				return Response.status(Response.Status.BAD_REQUEST).entity("Producto con ID inválido").build();
			}

			SaleLine line = new SaleLine();
			line.setCuantity(lineDTO.getCuantity());
			line.setPrice(lineDTO.getPrice());
			line.setSubtotal(lineDTO.getSubtotal());
			line.setProduct(lineDTO.getProduct());
			line.setSale(sale);

			StockMovements stockmove = new StockMovements();
			stockmove.setProduct(ProductMapper.fromDTO(lineDTO.getProduct()));
			stockmove.setReason(MovementReason.VENTA);
			stockmove.setType(MovementType.SALIDA);
			stockmove.setUnit(String.valueOf(lineDTO.getCuantity()));
			stockmove.setCreated_at(LocalDateTime.now());

			stockMoveDAO.persist(stockmove);

			Stock stock = stockDAO.findByProductId(lineDTO.getProduct().getId());
			stock.setQuantity((int) (stock.getQuantity() - lineDTO.getCuantity()));
			stockDAO.update(stock);

			lineas.add(line);
		}

		sale.setLine(lineas);
		dao.persist(sale);

		return Response.status(Response.Status.CREATED).build();
	}

	@GET
	@APIResponses({ @APIResponse(responseCode = "200", description = "Operación exitosa"),
			@APIResponse(responseCode = "201", description = "Creado correctamente"),
			@APIResponse(responseCode = "404", description = "No encontrado") })
	public Response findAll() {
		List<Sale> sales = dao.findAll();

		List<SaleDTO> dtos = sales.stream().map(sale -> {
			SaleDTO dto = new SaleDTO();
			if (sale.getDate() != null) {
				dto.setDate(sale.getDate());
			}
			dto.setId(sale.getId());
			dto.setTotal(sale.getTotal());
			dto.setMetodoPago(sale.getMetodoPago());
			List<SaleLineDTO> lineasDTO = sale.getLine().stream().map(linea -> {
				SaleLineDTO l = new SaleLineDTO();
				l.setProduct(linea.getProduct());
				l.setCuantity(linea.getCuantity());
				l.setPrice(linea.getPrice());
				l.setSubtotal(linea.getSubtotal());
				return l;
			}).collect(Collectors.toList());

			dto.setLine(lineasDTO);
			return dto;
		}).collect(Collectors.toList());

		return Response.ok(dtos).build();
	}


	@GET
	@Path("/{id}")
	@APIResponses({ @APIResponse(responseCode = "200", description = "Operación exitosa"),
			@APIResponse(responseCode = "201", description = "Creado correctamente"),
			@APIResponse(responseCode = "404", description = "No encontrado") })
	public Response get(@PathParam("id") Long id) {

		Sale sale = dao.findById(id);

		if (sale == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		SaleDTO dto = new SaleDTO();
		dto.setDate(sale.getDate());
		dto.setTotal(sale.getTotal());
		dto.setMetodoPago(sale.getMetodoPago());

		List<SaleLineDTO> lineasDTO = sale.getLine().stream().map(linea -> {
			SaleLineDTO l = new SaleLineDTO();
			l.setProduct(linea.getProduct());
			l.setCuantity(linea.getCuantity());
			l.setPrice(linea.getPrice());
			l.setSubtotal(linea.getSubtotal());
			return l;
		}).collect(Collectors.toList());

		dto.setLine(lineasDTO);

		return Response.ok(dto).build();
	}

	@PUT
	@Path("/{id}")
	public Response update(@PathParam("id") Long id, SaleDTO dto) {
		Sale sale = dao.findById(id);
		if (sale == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		if (dto.getDate() == null || dto.getLine() == null || dto.getMetodoPago() == null || dto.getTotal() == null) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}

		sale.setDate(dto.getDate());
		sale.setMetodoPago(dto.getMetodoPago());
		sale.setTotal(dto.getTotal());


		List<SaleLine> nuevasLineas = new ArrayList<>();
		for (SaleLineDTO lineDTO : dto.getLine()) {
			Product productid = productDAO.findById(lineDTO.getProduct().getId());
			if (productid == null) {
				return Response.status(Response.Status.BAD_REQUEST).entity("Producto con ID inválido").build();
			}

			SaleLine line = new SaleLine();
			line.setProduct(lineDTO.getProduct());
			line.setCuantity(lineDTO.getCuantity());
			line.setPrice(lineDTO.getPrice());
			line.setSubtotal(lineDTO.getSubtotal());
			line.setSale(sale);

			nuevasLineas.add(line);
		}

		sale.setLine(nuevasLineas);
		dao.update(sale);

		return Response.ok().build();
	}


	@DELETE
	@Path("/{id}")
	@APIResponses({ @APIResponse(responseCode = "200", description = "Eliminado correctamente"),
			@APIResponse(responseCode = "404", description = "Venta no encontrada") })
	public Response delete(@PathParam("id") Long id) {

		Sale sale = dao.findById(id);
		if (sale == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		List<SaleLine> lineas = sale.getLine();
		for (SaleLine linea : lineas) {
			saleline.delete(linea);
		}

		dao.delete(sale);
		return Response.ok().build();
	}

}