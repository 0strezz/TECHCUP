package main.java.edu.eci.dosw.tech_cup.dto;

import java.util.UUID;

public class LoginResponseDto {
    private UUID id;
    private String email;
    private String name;
    private String role;
    private String message;

    public LoginResponseDto() {

    }

    public LoginResponseDto(UUID id, String email, String name, String role, String message) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.role = role;
        this.message = message;
    }

    public UUID getId() {
        return this.id;
    }
    public String getEmail() {
        return this.email;
    }
    public String getName() {
        return this.name;
    }
    public String getRole() {
        return this.role;
    }
    public String getMessage() {
        return this.message;
    }   

    public void setId(UUID id) {
        this.id = id;
    }
    public void setEmail(String email) {
        this.email = email;
    }      
    public void setName(String name) {
        this.name = name;
    }  
    public void setRole(String role) {
        this.role = role;
    }   
    public void setMessage(String message) {
        this.message = message;
    }
}
