package com.lightvision.simple_product_api.security;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {

    private static final String SECRET_STRING = "LightVisionBackendTestSecretKeyMustBeVeryLongToSecureTheTokenAndPreventBruteForceAttacks123456";
    
    private final Key key = Keys.hmacShaKeyFor(SECRET_STRING.getBytes());

    private final long EXPIRATION_TIME = 86400000L; // 24 * 60 * 60 * 1000

    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public String getEmailFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            System.out.println("Error: Token is not valid");
        } catch (ExpiredJwtException e) {
            System.out.println("Error: Token has expired");
        } catch (UnsupportedJwtException e) {
            System.out.println("Error: Token is not supported");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: Token string is empty or null");
        }
        return false;
    }
}