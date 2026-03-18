package main.java.edu.eci.dosw.tech_cup.dto;

import java.util.UUID;

public class UserResponse {
    private UUID id;
    private String name;
    private String email;
    private String role;

    public UserResponse() {

    }

    public UserResponse(UUID id, String name, String email, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public UUID getId() {
        return this.id;
    }
    public String getName() {
        return this.name;
    }
    public String getEmail() {
        return this.email;
    }
    public String getRole() {
        return this.role;
    }

    public void setId(UUID id) {
        this.id = id;
    }   
    public void setName(String name) {
        this.name = name;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setRole(String role) {
        this.role = role;
    }
}
