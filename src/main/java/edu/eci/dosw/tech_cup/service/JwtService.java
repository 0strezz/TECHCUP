package edu.eci.dosw.tech_cup.service;

import edu.eci.dosw.tech_cup.entity.user.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.access-token-expiration-minutes}")
    private long accessTokenExpirationMinutes;

    @Value("${jwt.refresh-token-expiration-days}")
    private long refreshTokenExpirationDays;

    public String generateAccessToken(UserEntity user) {
        Instant now = Instant.now();
        Instant expiration = now.plus(accessTokenExpirationMinutes, ChronoUnit.MINUTES);

        return Jwts.builder()
                .subject(user.getEmail())
                .claim("role", user.getRole())
                .claim("type", "access")
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiration))
                .signWith(getSigningKey())
                .compact();
    }

    public String generateRefreshToken(UserEntity user) {
        Instant now = Instant.now();
        Instant expiration = now.plus(refreshTokenExpirationDays, ChronoUnit.DAYS);

        return Jwts.builder()
                .subject(user.getEmail())
                .claim("type", "refresh")
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiration))
                .signWith(getSigningKey())
                .compact();
    }

    public String extractSubject(String token) {
        return extractAllClaims(token).getSubject();
    }

    public boolean isAccessTokenValid(String token, UserEntity user) {
        Claims claims = extractAllClaims(token);
        return user.getEmail().equals(claims.getSubject())
                && "access".equals(claims.get("type", String.class))
                && claims.getExpiration().after(new Date());
    }

    public boolean isRefreshTokenValid(String token, UserEntity user) {
        Claims claims = extractAllClaims(token);
        return user.getEmail().equals(claims.getSubject())
                && "refresh".equals(claims.get("type", String.class))
                && claims.getExpiration().after(new Date());
    }

    public long getAccessTokenExpirationSeconds() {
        return accessTokenExpirationMinutes * 60;
    }

    public Instant extractExpirationInstant(String token) {
        return extractAllClaims(token).getExpiration().toInstant();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }
}
