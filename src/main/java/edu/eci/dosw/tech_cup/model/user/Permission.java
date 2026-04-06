package edu.eci.dosw.tech_cup.model.user;

import java.util.UUID;

public class Permission {
    private UUID id;
    private String name;
    private String description;

    public Permission() {
        this.id = UUID.randomUUID();
    }

    public Permission(String name, String description) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.description = description;
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
}