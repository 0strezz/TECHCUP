package edu.eci.dosw.tech_cup.model.tournament;

import java.util.UUID;

public class Field {
    private UUID id;
    private String name;

    public Field(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId() { return id; }
    public String getName() { return name; }

    public void setName(String name) { this.name = name; }
}
