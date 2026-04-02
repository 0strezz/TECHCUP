package edu.eci.dosw.tech_cup.entity.auth;

import edu.eci.dosw.tech_cup.entity.user.UserEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tokens")
public class TokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "token", nullable = false, unique = true)
    private String token;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "token_type", nullable = false)
    private TokenType tokenType;

    @Column(name = "revoked", nullable = false)
    private boolean revoked;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    public TokenEntity() {
    }

    public TokenEntity(String token, LocalDateTime createdAt, LocalDateTime expiresAt, TokenType tokenType, boolean revoked, UserEntity user) {
        this.token = token;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.tokenType = tokenType;
        this.revoked = revoked;
        this.user = user;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getExpiresAt() { return expiresAt; }
    public void setExpiresAt(LocalDateTime expiresAt) { this.expiresAt = expiresAt; }

    public TokenType getTokenType() { return tokenType; }
    public void setTokenType(TokenType tokenType) { this.tokenType = tokenType; }

    public boolean isRevoked() { return revoked; }
    public void setRevoked(boolean revoked) { this.revoked = revoked; }

    public UserEntity getUser() { return user; }
    public void setUser(UserEntity user) { this.user = user; }
}
