package edu.eci.dosw.tech_cup.dto;

import java.util.List;
import java.util.UUID;

public class UserResponseDto {
    private UUID id;
    private String name;
    private String email;
    private List<String> roles;
    private boolean active;
    private String message;

    public UserResponseDto() {

    }

    public UserResponseDto(UUID id, String name, String email, List<String> roles, boolean active, String message) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.roles = roles;
        this.active = active;
        this.message = message;
    }

    public UUID getId() { return this.id;}
    public void setId(UUID id) {this.id = id;} 
    
    public String getName() { return this.name; }
    public void setName(String name) {this.name = name; }

    public String getEmail() { return this.email; }
    public void setEmail(String email) { this.email = email; }

    public List<String> getRoles() { return this.roles; }
    public void setRoles(List<String> roles) { this.roles = roles; }

    public boolean getActive() { return this.active; }
    public void setActive(boolean active){ this.active= active; }

    public String getMessage() { return this.message; }
    public void setMessage(String message) { this.message = message; }
    
}
