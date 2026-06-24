package com.bloodlink.common.auth;

import com.bloodlink.common.model.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

public class JwtService {
    private final SecretKey key;
    private final long expirationMinutes;

    public JwtService(String secret, long expirationMinutes) {
        if (secret == null || secret.length() < 32) {
            throw new IllegalArgumentException("JWT secret must be at least 32 characters.");
        }
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expirationMinutes = expirationMinutes;
    }

    public String createToken(Long userId, String email, UserRole role) {
        Instant now = Instant.now();
        return Jwts.builder()
                .subject(email)
                .claim("userId", userId)
                .claim("role", role.name())
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusSeconds(expirationMinutes * 60)))
                .signWith(key)
                .compact();
    }

    public JwtClaims parse(String bearerToken) {
        String token = bearerToken == null ? "" : bearerToken.replace("Bearer ", "");
        Claims claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
        Integer userId = claims.get("userId", Integer.class);
        return new JwtClaims(userId.longValue(), claims.getSubject(), UserRole.valueOf(claims.get("role", String.class)));
    }
}
