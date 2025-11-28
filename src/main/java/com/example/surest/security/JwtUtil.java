package com.example.surest.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

import com.example.surest.entity.User;

@Component
public class JwtUtil {
    @Value("${app.jwt.secret}")
    private String secret;

    @Value("${app.jwt.expiration-ms}")
    private long expirationMs;

    public String generateToken(User user) {
        Algorithm alg = Algorithm.HMAC256(secret);
        Date now = new Date();
        return JWT.create()
                .withSubject(user.getUsername())
                .withClaim("role", user.getRole() != null ? user.getRole().getName() : "")
                .withIssuedAt(now)
                .withExpiresAt(new Date(now.getTime() + expirationMs))
                .sign(alg);
    }

    public DecodedJWT validateToken(String token) {
        Algorithm alg = Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(alg).build();
        return verifier.verify(token);
    }
}
