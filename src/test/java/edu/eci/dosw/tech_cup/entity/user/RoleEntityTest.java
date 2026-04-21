package edu.eci.dosw.tech_cup.entity.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("RoleEntity tests")
class RoleEntityTest {

    @Test
    @DisplayName("Empty constructor works")
    void emptyConstructor() {
        RoleEntity role = new RoleEntity();
        assertNull(role.getId());
        assertNull(role.getName());
        assertNull(role.getDescription());
        assertTrue(role.getUser().isEmpty());
        assertTrue(role.getPermissions().isEmpty());
    }

    @Test
    @DisplayName("Full constructor works")
    void fullConstructor() {
        RoleEntity role = new RoleEntity("ADMIN", "Admin role");

        assertEquals("ADMIN", role.getName());
        assertEquals("Admin role", role.getDescription());
    }

    @Test
    @DisplayName("Getters and Setters work")
    void gettersAndSetters() {
        RoleEntity role = new RoleEntity();
        UUID id = UUID.randomUUID();

        role.setId(id);
        role.setName("USER");
        role.setDescription("User role");

        List<UserEntity> users = new ArrayList<>();
        users.add(new UserEntity());
        role.setUser(users);

        List<PermissionEntity> permissions = new ArrayList<>();
        permissions.add(new PermissionEntity());
        role.setPermissions(permissions);

        assertEquals(id, role.getId());
        assertEquals("USER", role.getName());
        assertEquals("User role", role.getDescription());
        assertEquals(1, role.getUser().size());
        assertEquals(1, role.getPermissions().size());
    }
}
