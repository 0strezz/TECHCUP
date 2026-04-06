package edu.eci.dosw.tech_cup.model.user;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Role {
    private UUID id;
    private String name;
    private String description;
    private List<Permission> permissions;

    public Role() {
        this.id = UUID.randomUUID();
        this.permissions = new ArrayList<>();
    }

    public Role(String name, String description) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.description = description;
        this.permissions = new ArrayList<>();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }
}