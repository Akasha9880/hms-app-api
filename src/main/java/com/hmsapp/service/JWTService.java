package com.hmsapp.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class JWTService {
    @Value("${jwt.algorithm.key}")
            private String algorithmKey;

    @Value("{jwt.issuer}")
private String issuer;

    @Value("${jwt.expiry.duration}")
private String expiry;

    private Algorithm algorithm;
    @PostConstruct
public void postConstruct()throws UnsupportedEncodingException{
        algorithm  = Algorithm.HMAC256(algorithmKey);
    }

    //ToGenerate Token
    public String generateToken(String username){
        // Calculate the expiration time using java.time.Instant
        Instant expirationTime = Instant.now().plus(Long.parseLong(expiry), ChronoUnit.MILLIS);

        // Convert Instant to java.util.Date (required by JWT library)
        Date expiryDate = Date.from(expirationTime);
        return JWT.create()
                .withClaim("name", username)
                .withExpiresAt(expiryDate)
                .withIssuer(issuer)
                .sign(algorithm);
    }
    
    public String getUsername(String token){
        DecodedJWT decodeJWT = JWT.require(algorithm)
                .withIssuer(issuer)
                .build()
                .verify(token);
        return decodeJWT.getClaim("name").asString();
    }
}

