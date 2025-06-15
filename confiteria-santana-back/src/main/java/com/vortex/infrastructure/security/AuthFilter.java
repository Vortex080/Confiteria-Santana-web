package com.vortex.infrastructure.security;

import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.SignedJWT;
import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

import java.util.Date;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthFilter implements ContainerRequestFilter {

    private static final String SECRET = "aB1cD2eF3gH4iJ5kL6mN7oP8qR9sT0uVwXyZ";

    @Override
    public void filter(ContainerRequestContext requestContext) {
        String path = requestContext.getUriInfo().getPath();

        // EXCLUIR login
        if (path != null && path.toLowerCase().endsWith("user/login") || path.toLowerCase().endsWith("user") || path.toLowerCase().endsWith("product")) {
            return;
        }

        String authHeader = requestContext.getHeaderString("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity("Token no proporcionado").build());
            return;
        }

        String token = authHeader.substring("Bearer".length()).trim();

        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            JWSVerifier verifier = new MACVerifier(SECRET);

            if (!signedJWT.verify(verifier)) {
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity("Firma del token no válida").build());
                return;
            }

            Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();
            if (expirationTime == null || new Date().after(expirationTime)) {
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity("Token expirado").build());
                return;
            }

        } catch (Exception e) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity("Token no válido").build());
        }
    }
}
