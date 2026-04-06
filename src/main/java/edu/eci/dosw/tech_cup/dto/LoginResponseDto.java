package edu.eci.dosw.tech_cup.dto;

import java.util.List;
import java.util.UUID;

public class LoginResponseDto {
    private UUID id;
    private String email;
    private String name;
    private List<String> roles;
    private String accessToken;
    private String refreshToken;
    private String tokenType;
    private Long expiresIn;
    private String message;

    public LoginResponseDto() {
    }

    public LoginResponseDto(UUID id, String name, String email, List<String> roles, String message) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.roles = roles;
        this.message = message;
    }

    public LoginResponseDto(UUID id, String name, String email, List<String> roles,
                            String accessToken, String refreshToken, String tokenType,
                            Long expiresIn, String message) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.roles = roles;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.tokenType = tokenType;
        this.expiresIn = expiresIn;
        this.message = message;
    }

    public UUID getId() { return this.id; }
    public void setId(UUID id) { this.id = id; }

    public String getEmail() { return this.email; }
    public void setEmail(String email) { this.email = email; }

    public String getName() {return this.name; }
    public void setName(String name) { this.name = name; }

    public List<String> getRoles() { return this.roles; }
    public void setRoles(List<String> roles) { this.roles = roles; }
    
    public String getAccessToken() { return this.accessToken; }
    public void setAccessToken(String accessToken) { this.accessToken = accessToken; }

    public String getRefreshToken() { return this.refreshToken; }
    public void setRefreshToken(String refreshToken) { this.refreshToken = refreshToken; }

    public String getTokenType() { return this.tokenType; }
    public void setTokenType(String tokenType) { this.tokenType = tokenType; }

    public Long getExpiresIn() { return this.expiresIn; }
    public void setExpiresIn(Long expiresIn) { this.expiresIn = expiresIn; }
    
    public String getMessage() { return this.message; }
    public void setMessage(String message) { this.message = message; }

}