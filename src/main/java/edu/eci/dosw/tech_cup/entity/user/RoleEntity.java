package edu.eci.dosw.tech_cup.entity.user;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "roles")

public class RoleEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy = "roles")
    private List<UserEntity> user = new ArrayList<>();

    @ManyToMany
    @JoinTable(
        name = "role_permissions",
        joinColumns = @JoinColumn(name = "role_id"),
        inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private List<PermissionEntity> permissions = new ArrayList<>();

    public RoleEntity() {
    }

    public RoleEntity(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public UUID getId() {return this.id;}
    public void setId(UUID id) {this.id = id;}

    public String getName() {return this.name;}
    public void setName(String name) {this.name = name;}

    public String getDescription() {return this.description;}
    public void setDescription(String description) {this.description = description;}  

    public List<UserEntity> getUser() {return this.user;}
    public void setUser(List<UserEntity> user) {this.user = user;}

    public List<PermissionEntity> getPermissions() {return this.permissions;}
    public void setPermissions(List<PermissionEntity> permissions) {this.permissions = permissions;}
 

}
