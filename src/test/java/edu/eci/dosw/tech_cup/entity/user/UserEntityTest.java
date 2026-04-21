package edu.eci.dosw.tech_cup.entity.user;

import edu.eci.dosw.tech_cup.entity.auth.TokenEntity;
import edu.eci.dosw.tech_cup.entity.tournament.TournamentEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("UserEntity tests")
class UserEntityTest {

    @Test
    @DisplayName("Empty constructor works")
    void emptyConstructor() {
        UserEntity user = new UserEntity();
        assertNull(user.getId());
        assertNull(user.getName());
        assertNull(user.getEmail());
        assertNull(user.getPassword());
        assertTrue(user.getRoles().isEmpty());
        assertTrue(user.getTournaments().isEmpty());
        assertTrue(user.getTokens().isEmpty());
    }

    @Test
    @DisplayName("Full constructor works")
    void fullConstructor() {
        LocalDateTime now = LocalDateTime.now();
        UserEntity user = new UserEntity("Test User", "test@test.com", "password", true, now);

        assertEquals("Test User", user.getName());
        assertEquals("test@test.com", user.getEmail());
        assertEquals("password", user.getPassword());
        assertTrue(user.isActive());
        assertEquals(now, user.getCreatedAt());
        assertTrue(user.getRoles().isEmpty());
    }

    @Test
    @DisplayName("Getters and Setters work")
    void gettersAndSetters() {
        UserEntity user = new UserEntity();
        UUID id = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        user.setId(id);
        user.setName("New Name");
        user.setEmail("new@test.com");
        user.setPassword("newpass");
        user.setActive(false);
        user.setCreatedAt(now);

        List<RoleEntity> roles = new ArrayList<>();
        roles.add(new RoleEntity());
        user.setRoles(roles);

        List<TournamentEntity> tournaments = new ArrayList<>();
        tournaments.add(new TournamentEntity());
        user.setTournaments(tournaments);

        List<TokenEntity> tokens = new ArrayList<>();
        tokens.add(new TokenEntity());
        user.setTokens(tokens);

        assertEquals(id, user.getId());
        assertEquals("New Name", user.getName());
        assertEquals("new@test.com", user.getEmail());
        assertEquals("newpass", user.getPassword());
        assertFalse(user.isActive());
        assertEquals(now, user.getCreatedAt());
        assertEquals(1, user.getRoles().size());
        assertEquals(1, user.getTournaments().size());
        assertEquals(1, user.getTokens().size());
    }
}
