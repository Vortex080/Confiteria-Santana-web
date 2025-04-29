package com.vortex.infrastructure.controllers;

import com.vortex.domain.dto.AlergenDTO;
import com.vortex.domain.dto.UserDTO;
import com.vortex.domain.entities.Address;
import com.vortex.domain.entities.Alergens;
import com.vortex.domain.entities.LoginRequest;
import com.vortex.domain.entities.User;
import com.vortex.infrastructure.repositories.AddressDAO;
import com.vortex.infrastructure.repositories.UserDAO;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;

@Tag(name = "User")
@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserRest {

    @Inject
    private UserDAO dao;
    private AddressDAO addressDAO;
    private AddressRest addressRest;

    @POST
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Operación exitosa"),
            @APIResponse(responseCode = "201", description = "Creado correctamente"),
            @APIResponse(responseCode = "404", description = "No encontrado")
    })
    public Response create(UserDTO dto){
        if (dto.getAddress() == null || dto.getEmail() == null || dto.getLastname() == null || dto.getName() == null || dto.getPhone() == -1 || dto.getRol() == null || dto.getPhoto() == null || dto.getUsername() == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        User user = new User();

        if (addressDAO.findByFields(dto.getAddress()) == null){
            Address address = new Address();
            address.setStreet(dto.getAddress().getStreet());
            address.setCity(dto.getAddress().getCity());
            address.setState(dto.getAddress().getState());
            address.setDoor(dto.getAddress().getDoor());
            address.setNumber(dto.getAddress().getNumber());
            address.setFlat(dto.getAddress().getFlat());
            address.setCountry(dto.getAddress().getCountry());
            address.setPostalCode(dto.getAddress().getPostalCode());

            addressDAO.persist(address);
        }

        user.setAddress(addressDAO.findByFields(dto.getAddress()).getId());
        user.setEmail(dto.getEmail());
        user.setName(dto.getName());
        user.setLastname(dto.getLastname());
        user.setPassword(dto.getPass());
        user.setPhoto(dto.getPhoto());
        user.setUsername(dto.getUsername());
        user.setName(dto.getName());
        user.setRol(dto.getRol());
        user.setPhone(dto.getPhone());

        // Verificar si el email ya existe
        if (dao.findByEmail(user.getEmail()) != null) {
            return Response.status(Response.Status.CONFLICT) // 409 Conflict
                    .entity("El email ya está en uso").build();
        }

        dao.persist(user);

        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Operación exitosa"),
            @APIResponse(responseCode = "201", description = "Creado correctamente"),
            @APIResponse(responseCode = "404", description = "No encontrado")
    })
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

        return Response.ok(user).build();
    }

    @DELETE
    @Path("/{id}")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Operación exitosa"),
            @APIResponse(responseCode = "201", description = "Creado correctamente"),
            @APIResponse(responseCode = "404", description = "No encontrado")
    })
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
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Operación exitosa"),
            @APIResponse(responseCode = "201", description = "Creado correctamente"),
            @APIResponse(responseCode = "404", description = "No encontrado")
    })
    public Response update(@PathParam("id") Long id, UserDTO dto) {
        User user = dao.find(id);

        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        if (addressDAO.findByFields(dto.getAddress()) == null){
            Address address = new Address();
            address.setStreet(dto.getAddress().getStreet());
            address.setCity(dto.getAddress().getCity());
            address.setState(dto.getAddress().getState());
            address.setDoor(dto.getAddress().getDoor());
            address.setNumber(dto.getAddress().getNumber());
            address.setFlat(dto.getAddress().getFlat());
            address.setCountry(dto.getAddress().getCountry());
            address.setPostalCode(dto.getAddress().getPostalCode());

            addressDAO.persist(address);
        }

        user.setAddress(addressDAO.findByFields(dto.getAddress()).getId());
        user.setEmail(dto.getEmail());
        user.setName(dto.getName());
        user.setLastname(dto.getLastname());
        user.setPassword(dto.getPass());
        user.setPhoto(dto.getPhoto());
        user.setUsername(dto.getUsername());
        user.setName(dto.getName());
        user.setRol(dto.getRol());
        user.setPhone(dto.getPhone());

        return Response.ok(user).build();
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(LoginRequest request) {
        boolean isValid = dao.verifyUser(request.getEmail(), request.getPass());
        if (isValid) {
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Credenciales inválidas").build();
        }
    }

}
