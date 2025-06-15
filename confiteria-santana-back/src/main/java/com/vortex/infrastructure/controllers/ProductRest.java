
package com.vortex.infrastructure.controllers;

import com.vortex.domain.dto.AlergenDTO;
import com.vortex.domain.dto.ProductDTO;
import com.vortex.domain.dto.ProductPhotoDTO;
import com.vortex.domain.entities.*;
import com.vortex.domain.enums.MovementReason;
import com.vortex.domain.enums.MovementType;
import com.vortex.infrastructure.repositories.*;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Tag(name = "Product")
@Path("/product")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductRest {

	@Inject
	private ProductDAO productDAO;
	@Inject
	private CategoryDAO categoryDAO;
	@Inject
	private ProductPhotoDAO productPhotoDAO;
	@Inject
	private AlergensDAO alergensDAO;
	@Inject
	private StockDAO stockDAO;
	@Inject
	private StockMovementsDAO stockMovementsDAO;

	/**
	 * Creates the.
	 *
	 * @param dto the dto
	 * @return the response
	 */
	@POST
	@APIResponses({ @APIResponse(responseCode = "200", description = "Operación exitosa"),
			@APIResponse(responseCode = "201", description = "Creado correctamente"),
			@APIResponse(responseCode = "404", description = "No encontrado") })
	public Response create(ProductDTO dto) {
		if (dto.getName() == null || dto.getDescription() == null || dto.getPrice() == -1 || dto.getUnit() == null
				|| dto.getAlergens() == null || dto.getCategory() == null || dto.getPhotos() == null) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}

		Product product = new Product();

		product.setName(dto.getName());
		product.setDescription(dto.getDescription());
		product.setPrice(dto.getPrice());
		product.setUnit(dto.getUnit());

		List<Alergens> list = new ArrayList<>();
		for (AlergenDTO alergenDTO : dto.getAlergens()) {
		    Alergens alergen = alergensDAO.find(alergenDTO.getId());
		    if (alergen == null) {
		        return Response.status(Response.Status.BAD_REQUEST).build();
		    }
		    list.add(alergen);
		}

		product.setAlergens((ArrayList<Alergens>) list);

		if (categoryDAO.find(dto.getCategory().getId()) == null) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}

		product.setCategory(categoryDAO.find(dto.getCategory().getId()));

		productDAO.persist(product);

		product = productDAO.findByFields(dto);

		for (ProductPhotoDTO photo : dto.getPhotos()) {

			ProductPhoto photo1 = new ProductPhoto();

			photo1.setUrl(dto.getPhotos().get(0).getUrl());
			photo1.setAltText(dto.getPhotos().get(0).getAltText());
			photo1.setProduct(product);
			
			productPhotoDAO.persist(photo1);
		}

		Stock stock = new Stock();
		stock.setProduct(product);
		stock.setQuantity(1);

		stockDAO.persist(stock);

		StockMovements stockMovements = new StockMovements();

		stockMovements.setProduct(product);
		stockMovements.setType(MovementType.ENTRADA);
		stockMovements.setReason(MovementReason.AÑADIDO);
		stockMovements.setUnit("unidad");
		stockMovements.setCreated_at(LocalDateTime.now());


		stockMovementsDAO.persist(stockMovements);

		return Response.status(Response.Status.CREATED).build();

	}

	@GET
	@Path("/{id}")
	@APIResponses({ @APIResponse(responseCode = "200", description = "Operación exitosa"),
			@APIResponse(responseCode = "201", description = "Creado correctamente"),
			@APIResponse(responseCode = "404", description = "No encontrado") })
	public Response get(@PathParam("id") Long id) {
		Product product = productDAO.findById(id);

		if (product == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		return Response.ok(product).build();
	}

	@GET
	@APIResponses({ @APIResponse(responseCode = "200", description = "Operación exitosa"),
			@APIResponse(responseCode = "201", description = "Creado correctamente"),
			@APIResponse(responseCode = "404", description = "No encontrado") })
	public Response getAll() {
	    List<Product> list = productDAO.findAllWithEverything();
	    return Response.ok(list).build();
	}


	@DELETE
	@Path("/{id}")
	@APIResponses({ @APIResponse(responseCode = "200", description = "Operación exitosa"),
			@APIResponse(responseCode = "201", description = "Creado correctamente"),
			@APIResponse(responseCode = "404", description = "No encontrado") })
	public Response delete(@PathParam("id") Long id) {
		Product product = productDAO.findById(id);

		if (product.getName() == null || product.getDescription() == null || product.getPrice() == -1
				|| product.getUnit() == null || product.getAlergens() == null || product.getCategory() == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		productDAO.delete(product);

		return Response.status(Response.Status.OK).build();

	}

	@PUT
	@Path("/{id}")
	@APIResponses({ @APIResponse(responseCode = "200", description = "Operación exitosa"),
			@APIResponse(responseCode = "201", description = "Creado correctamente"),
			@APIResponse(responseCode = "404", description = "No encontrado") })
	public Response update(@PathParam("id") Long id, ProductDTO dto) {

		Product product = productDAO.findById(id);

		if (dto.getName() == null || dto.getDescription() == null || dto.getPrice() == -1 || dto.getUnit() == null
				|| dto.getAlergens() == null || dto.getCategory() == null || dto.getPhotos() == null) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		product.setName(dto.getName());
		product.setDescription(dto.getDescription());
		product.setPrice(dto.getPrice());
		product.setUnit(dto.getUnit());

		List<Alergens> list = new ArrayList<>();
		for (AlergenDTO alergenDTO : dto.getAlergens()) {
		    Alergens alergen = alergensDAO.find(alergenDTO.getId());
		    if (alergen == null) {
		        return Response.status(Response.Status.BAD_REQUEST).build();
		    }
		    list.add(alergen);
		}

		product.setAlergens((ArrayList<Alergens>) list);


		if (categoryDAO.find(dto.getCategory().getId()) == null) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}

		product.setCategory(categoryDAO.find(dto.getCategory().getId()));

		productDAO.update(product);

		List<ProductPhoto> listphoto = productPhotoDAO.findByProduct(product.getId());

		for (ProductPhoto photo : listphoto) {
			for (ProductPhotoDTO photo2 : dto.getPhotos()) {
				photo.setUrl(photo2.getUrl());
				photo.setProduct(product);

				photo.setUrl(photo2.getUrl());

				photo.setAltText(photo2.getAltText());
				productPhotoDAO.update(photo);
			}
		}
		
		return Response.status(Response.Status.OK).build();

	}

}
