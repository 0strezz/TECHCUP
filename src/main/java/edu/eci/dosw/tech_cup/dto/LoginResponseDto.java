package edu.eci.dosw.tech_cup.dto;

import java.util.UUID;

public class LoginResponseDto {
    private UUID id;
    private String email;
    private String name;
    private String role;
    private String accessToken;
    private String refreshToken;
    private String tokenType;
    private Long expiresIn;
    private String message;

    public LoginResponseDto() {

    }

    public LoginResponseDto(UUID id, String name, String email, String role, String message) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.role = role;
        this.message = message;
    }

    public LoginResponseDto(UUID id, String name, String email, String role, String accessToken, String refreshToken, String tokenType, Long expiresIn, String message) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.role = role;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.tokenType = tokenType;
        this.expiresIn = expiresIn;
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
    public String getAccessToken() {
        return this.accessToken;
    }
    public String getRefreshToken() {
        return this.refreshToken;
    }
    public String getTokenType() {
        return this.tokenType;
    }
    public Long getExpiresIn() {
        return this.expiresIn;
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
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
