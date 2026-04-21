package edu.eci.dosw.tech_cup.entity.auth;

import edu.eci.dosw.tech_cup.entity.user.UserEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("TokenEntity tests")
class TokenEntityTest {

    @Test
    @DisplayName("Empty constructor works")
    void emptyConstructor() {
        TokenEntity token = new TokenEntity();
        assertNull(token.getId());
        assertNull(token.getToken());
        assertNull(token.getCreatedAt());
        assertNull(token.getExpiresAt());
        assertNull(token.getTokenType());
        assertFalse(token.isRevoked());
        assertNull(token.getUser());
    }

    @Test
    @DisplayName("Full constructor works")
    void fullConstructor() {
        LocalDateTime created = LocalDateTime.now();
        LocalDateTime expires = created.plusDays(1);
        UserEntity user = new UserEntity();

        TokenEntity token = new TokenEntity("mytoken", created, expires, TokenType.REFRESH, true, user);

        assertEquals("mytoken", token.getToken());
        assertEquals(created, token.getCreatedAt());
        assertEquals(expires, token.getExpiresAt());
        assertEquals(TokenType.REFRESH, token.getTokenType());
        assertTrue(token.isRevoked());
        assertEquals(user, token.getUser());
    }

    @Test
    @DisplayName("Getters and Setters work")
    void gettersAndSetters() {
        TokenEntity token = new TokenEntity();
        UUID id = UUID.randomUUID();
        LocalDateTime created = LocalDateTime.now();
        LocalDateTime expires = created.plusDays(1);
        UserEntity user = new UserEntity();

        token.setId(id);
        token.setToken("newtoken");
        token.setCreatedAt(created);
        token.setExpiresAt(expires);
        token.setTokenType(TokenType.REFRESH);
        token.setRevoked(true);
        token.setUser(user);

        assertEquals(id, token.getId());
        assertEquals("newtoken", token.getToken());
        assertEquals(created, token.getCreatedAt());
        assertEquals(expires, token.getExpiresAt());
        assertEquals(TokenType.REFRESH, token.getTokenType());
        assertTrue(token.isRevoked());
        assertEquals(user, token.getUser());
    }
}
