package edu.eci.dosw.tech_cup.entity.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("PermissionEntity tests")
class PermissionEntityTest {

    @Test
    @DisplayName("Empty constructor works")
    void emptyConstructor() {
        PermissionEntity permission = new PermissionEntity();
        assertNull(permission.getId());
        assertNull(permission.getName());
        assertNull(permission.getDescription());
        assertTrue(permission.getRoles().isEmpty());
    }

    @Test
    @DisplayName("Full constructor works")
    void fullConstructor() {
        PermissionEntity permission = new PermissionEntity("READ", "Read access");

        assertEquals("READ", permission.getName());
        assertEquals("Read access", permission.getDescription());
    }

    @Test
    @DisplayName("Getters and Setters work")
    void gettersAndSetters() {
        PermissionEntity permission = new PermissionEntity();
        UUID id = UUID.randomUUID();

        permission.setId(id);
        permission.setName("WRITE");
        permission.setDescription("Write access");

        List<RoleEntity> roles = new ArrayList<>();
        roles.add(new RoleEntity());
        permission.setRoles(roles);

        assertEquals(id, permission.getId());
        assertEquals("WRITE", permission.getName());
        assertEquals("Write access", permission.getDescription());
        assertEquals(1, permission.getRoles().size());
    }
}
