package com.sweetmanager.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {

    private static final String RAW_SECRET = "MINHA_CHAVE_ULTRA_SECRETA_DE_32_BYTES_AQUI_123456";
    private static final long EXPIRATION = 1000 * 60 * 60 * 10; // 10 horas

    private final Key key = Keys.hmacShaKeyFor(Base64.getEncoder().encode(RAW_SECRET.getBytes()));

    public String generateToken(String email, Enum<?> role) {
        return Jwts.builder()
                .setSubject(email)
                .claim("role", "ROLE_" + role.name())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractEmail(String token) {
        return extractClaims(token).getSubject();
    }

    public boolean validateToken(String token, String email) {
        try {
            Claims claims = extractClaims(token);
            return claims.getSubject().equals(email) && !isExpired(claims);
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private boolean isExpired(Claims claims) {
        return claims.getExpiration().before(new Date());
    }


    private Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}