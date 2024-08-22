package com.dentalmanagementapp.security;

import  com.auth0.jwt.JWT;
import  com.auth0.jwt.JWTVerifier;
import  com.auth0.jwt.algorithms.Algorithm;
import  com.auth0.jwt.interfaces.DecodedJWT;
import  org.springframework.beans.factory.annotation.Value;
import  org.springframework.security.core.userdetails.UserDetails;
import  org.springframework.stereotype.Component;

import java.util.Date;

@Component

public class JwtUtil {
    @Value("${SECRET_KEY}") //check if works
    private String secretKey;

    public String generateToken(String email) {
        return JWT.create()
                .withSubject(email)
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                .sign(Algorithm.HMAC384(secretKey));
    }

    public String getUsername(String token) {
        return getDecodedJwt(token).getSubject();
    }

    private boolean isTokenExpired(String token) {
        return getDecodedJwt(token).getExpiresAt().before(new Date());
    }

    public boolean isTokenValid(String token, UserDetails details) {
        var username = getDecodedJwt(token).getSubject();
        return username.equals(details.getUsername()) && !isTokenExpired(token);
    }


    private DecodedJWT getDecodedJwt(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC384(secretKey)).build();
        return verifier.verify(token);
    }
}
