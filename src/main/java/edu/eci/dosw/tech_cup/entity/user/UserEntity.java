package edu.eci.dosw.tech_cup.entity.user;

import edu.eci.dosw.tech_cup.entity.auth.TokenEntity;
import edu.eci.dosw.tech_cup.entity.tournament.TournamentEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    // Main change, role has a relation ManyToMany 
    @ManyToMany
    @JoinTable(
        name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<RoleEntity> roles = new ArrayList<>();



    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "organizer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TournamentEntity> tournaments = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TokenEntity> tokens = new ArrayList<>();

    public UserEntity() {
    }

    public UserEntity(String name, String email, String password, boolean active, LocalDateTime createdAt) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.active = active;
        this.createdAt = createdAt;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public List<RoleEntity> getRoles() {return roles;}
    public void setRoles(List<RoleEntity> roles) {this.roles = roles;}

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public List<TournamentEntity> getTournaments() { return tournaments; }
    public void setTournaments(List<TournamentEntity> tournaments) { this.tournaments = tournaments; }

    public List<TokenEntity> getTokens() { return tokens; }
    public void setTokens(List<TokenEntity> tokens) { this.tokens = tokens; }
}
