package com.vortex.infrastructure.controllers;

import java.util.List;

import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.mindrot.jbcrypt.BCrypt;

import com.vortex.domain.dto.AddressDTO;
import com.vortex.domain.dto.UserDTO;
import com.vortex.domain.entities.Address;
import com.vortex.domain.entities.LoginRequest;
import com.vortex.domain.entities.User;
import com.vortex.infrastructure.repositories.AddressDAO;
import com.vortex.infrastructure.repositories.UserDAO;
import com.vortex.infrastructure.security.TokenUtil;

import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonObject;
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

@Tag(name = "User")
@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserRest {

	@Inject
	private UserDAO dao;
	@Inject
	private AddressDAO addressDAO;
	@Inject
	private AddressRest addressRest;

	@POST
	@APIResponses({ @APIResponse(responseCode = "200", description = "Operación exitosa"),
			@APIResponse(responseCode = "201", description = "Creado correctamente"),
			@APIResponse(responseCode = "404", description = "No encontrado") })
	public Response create(UserDTO dto) {
		if (dto.getAddress() == null || dto.getEmail() == null || dto.getLastname() == null || dto.getName() == null
				|| dto.getPhone() == -1 || dto.getRol() == null || dto.getPhoto() == null || dto.getUsername() == null
				|| dto.getPass() == null) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}

		// Verificar si el email ya existe
		if (dao.findByEmail(dto.getEmail()) != null) {
			return Response.status(Response.Status.CONFLICT).entity("El email ya está en uso").build();
		}

		User user = new User();

		if (addressDAO.findByFields(dto.getAddress()) == null) {
			Address address = new Address();
			address.setStreet(dto.getAddress().getStreet());
			address.setNumber(dto.getAddress().getNumber());
			address.setFlat(dto.getAddress().getFlat());
			address.setDoor(dto.getAddress().getDoor());
			address.setCity(dto.getAddress().getCity());
			address.setState(dto.getAddress().getState());
			address.setCountry(dto.getAddress().getCountry());
			address.setPostalCode(dto.getAddress().getPostalCode());
			addressDAO.persist(address);
		}

		user.setAddress(addressDAO.findByFields(dto.getAddress()));
		user.setEmail(dto.getEmail());
		user.setName(dto.getName());
		user.setLastname(dto.getLastname());

		// Encriptar la contraseña
		String hashedPassword = BCrypt.hashpw(dto.getPass(), BCrypt.gensalt());
		user.setPassword(hashedPassword);

		user.setPhoto(dto.getPhoto());
		user.setUsername(dto.getUsername());
		user.setRol(dto.getRol());
		user.setPhone(dto.getPhone());

		dao.persist(user);

		return Response.status(Response.Status.CREATED).build();
	}

	@GET
	@APIResponses({ @APIResponse(responseCode = "200", description = "Operación exitosa"),
			@APIResponse(responseCode = "201", description = "Creado correctamente"),
			@APIResponse(responseCode = "404", description = "No encontrado") })
	public Response findAll() {
		List<User> list = dao.findAll();
		return Response.ok(list).build();
	}

	@GET
	@Path("/{id}")
	@APIResponses({
	    @APIResponse(responseCode = "200", description = "Operación exitosa"),
	    @APIResponse(responseCode = "201", description = "Creado correctamente"),
	    @APIResponse(responseCode = "404", description = "No encontrado")
	})
	public Response get(@PathParam("id") Long id) {
	    User user = dao.find(id);

	    if (user == null) {
	        return Response.status(Response.Status.NOT_FOUND).build();
	    }

	    // Mapeo manual a DTO
	    UserDTO dto = new UserDTO();
	    dto.setUsername(user.getUsername());
	    dto.setName(user.getName());
	    dto.setLastname(user.getLastname());
	    dto.setPhoto(user.getPhoto());
	    dto.setEmail(user.getEmail());
	    dto.setRol(user.getRol());
	    dto.setPhone(user.getPhone());
	    
	    AddressDTO addresdto = new AddressDTO();
        addresdto.setStreet(user.getAddress().getStreet());
        addresdto.setNumber(user.getAddress().getNumber());
        addresdto.setFlat(user.getAddress().getFlat());
        addresdto.setDoor(user.getAddress().getDoor());
        addresdto.setCity(user.getAddress().getCity());
        addresdto.setState(user.getAddress().getState());
        addresdto.setCountry(user.getAddress().getCountry());
        addresdto.setPostalCode(user.getAddress().getPostalCode());
	    
	    dto.setAddress(addresdto);

	    return Response.ok(dto).build();
	}


	@DELETE
	@Path("/{id}")
	@APIResponses({ @APIResponse(responseCode = "200", description = "Operación exitosa"),
			@APIResponse(responseCode = "201", description = "Creado correctamente"),
			@APIResponse(responseCode = "404", description = "No encontrado") })
	public Response delete(@PathParam("id") Long id) {
		User user = dao.find(id);

		if (user == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		dao.delete(user);
		return Response.status(Response.Status.NO_CONTENT).build();
	}

	@PUT
	@Path("/{id}")
	@APIResponses({ @APIResponse(responseCode = "200", description = "Operación exitosa"),
			@APIResponse(responseCode = "201", description = "Creado correctamente"),
			@APIResponse(responseCode = "404", description = "No encontrado") })
	public Response update(@PathParam("id") Long id, UserDTO dto) {
		User user = dao.find(id);

		if (addressDAO.findByFields(dto.getAddress()) == null) {
			Address address = new Address();
			address.setStreet(dto.getAddress().getStreet());
			address.setNumber(dto.getAddress().getNumber());
			address.setFlat(dto.getAddress().getFlat());
			address.setDoor(dto.getAddress().getDoor());
			address.setCity(dto.getAddress().getCity());
			address.setState(dto.getAddress().getState());
			address.setCountry(dto.getAddress().getCountry());
			address.setPostalCode(dto.getAddress().getPostalCode());
			user.setAddress(address);
		} else {
			user.setAddress(addressDAO.findByFields(dto.getAddress()));
		}

		user.setEmail(dto.getEmail());
		user.setName(dto.getName());
		user.setLastname(dto.getLastname());
		user.setPhoto(dto.getPhoto());
		user.setUsername(dto.getUsername());
		user.setName(dto.getName());
		user.setRol(dto.getRol());
		user.setPhone(dto.getPhone());
		
		dao.update(user);

		return Response.ok(user).build();
	}

	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response login(LoginRequest request) {
	    // Buscar usuario por email
	    User user = dao.findByEmail(request.getEmail());

	    // Si no existe o contraseña incorrecta, retornar 401
	    if (user == null || !BCrypt.checkpw(request.getPass(), user.getPassword())) {
	        return Response.status(Response.Status.UNAUTHORIZED).entity("Credenciales inválidas").build();
	    }

	    try {
	        // Generar token con email y rol
	        String token = TokenUtil.generateToken(user.getEmail(), user.getRol());

	        JsonObject response = Json.createObjectBuilder()
	            .add("token", token)
	            .add("user", Json.createObjectBuilder()
	                .add("id", user.getId())
	                .add("name", user.getName())
	                .add("email", user.getEmail())
	                .add("photoUrl", user.getPhoto())
	            	.add("rol", user.getRol()))

	            .build();

	        return Response.ok(response).build();

	    } catch (Exception e) {
	        return Response.serverError().entity("Error generando token").build();
	    }
	}

}
