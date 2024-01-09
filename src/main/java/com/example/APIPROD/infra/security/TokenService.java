package com.example.APIPROD.infra.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.example.APIPROD.domain.user.User;

@Service
public class TokenService {
        @Value("${api.security.token.secret}")
        private String secret;
    public String generateToken(User user){

        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                            .withIssuer("products-api2")
                            .withSubject(user.getLogin())
                            .withExpiresAt(getExpirationDate())
                            .sign(algorithm);
                            return token;
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error while generating token", exception);
        }
    }
    private Instant getExpirationDate(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
