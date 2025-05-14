package com.vortex.domain.entities;

import java.util.Date;
import java.util.List;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

public class TokenUtil {
	 private static final String SECRET = "aB1cD2eF3gH4iJ5kL6mN7oP8qR9sT0uVwXyZ";

	    public static String generateToken(String username, String role) throws Exception {
	        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
	            .subject(username)
	            .issuer("mi-app")
	            .claim("groups", List.of(role))
	            .expirationTime(new Date(new Date().getTime() + 3600 * 1000)) // 1h
	            .build();

	        SignedJWT signedJWT = new SignedJWT(
	            new JWSHeader(JWSAlgorithm.HS256),
	            claimsSet
	        );

	        signedJWT.sign(new MACSigner(SECRET));
	        return signedJWT.serialize();
	    }

}
