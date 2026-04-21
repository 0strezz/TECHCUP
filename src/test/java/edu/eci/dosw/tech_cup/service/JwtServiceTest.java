package edu.eci.dosw.tech_cup.service;

import edu.eci.dosw.tech_cup.entity.user.RoleEntity;
import edu.eci.dosw.tech_cup.entity.user.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("JwtService tests")
class JwtServiceTest {

    private JwtService jwtService;
    private UserEntity user;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
        // A 256-bit secret key (32 bytes) is required for HS256
        String secret = "this-is-a-very-long-secret-key-that-is-at-least-32-bytes";
        ReflectionTestUtils.setField(jwtService, "jwtSecret", secret);
        ReflectionTestUtils.setField(jwtService, "accessTokenExpirationMinutes", 15L);
        ReflectionTestUtils.setField(jwtService, "refreshTokenExpirationDays", 7L);

        user = new UserEntity();
        user.setId(UUID.randomUUID());
        user.setEmail("user@test.com");
        user.getRoles().add(new RoleEntity("ADMIN", "admin"));
    }

    @Test
    void generateAccessToken() {
        String token = jwtService.generateAccessToken(user);
        assertNotNull(token);
        
        String subject = jwtService.extractSubject(token);
        assertEquals("user@test.com", subject);
    }

    @Test
    void generateRefreshToken() {
        String token = jwtService.generateRefreshToken(user);
        assertNotNull(token);
        
        String subject = jwtService.extractSubject(token);
        assertEquals("user@test.com", subject);
    }

    @Test
    void isAccessTokenValid() {
        String token = jwtService.generateAccessToken(user);
        assertTrue(jwtService.isAccessTokenValid(token, user));

        UserEntity otherUser = new UserEntity();
        otherUser.setEmail("other@test.com");
        assertFalse(jwtService.isAccessTokenValid(token, otherUser));
    }

    @Test
    void isRefreshTokenValid() {
        String token = jwtService.generateRefreshToken(user);
        assertTrue(jwtService.isRefreshTokenValid(token, user));

        assertFalse(jwtService.isAccessTokenValid(token, user)); // It's not an access token
    }

    @Test
    void getAccessTokenExpirationSeconds() {
        assertEquals(900L, jwtService.getAccessTokenExpirationSeconds()); // 15 min = 900 s
    }

    @Test
    void extractExpirationInstant() {
        String token = jwtService.generateAccessToken(user);
        Instant expiration = jwtService.extractExpirationInstant(token);
        assertNotNull(expiration);
        assertTrue(expiration.isAfter(Instant.now()));
    }
}
