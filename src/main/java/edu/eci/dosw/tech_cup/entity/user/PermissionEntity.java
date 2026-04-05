package edu.eci.dosw.tech_cup.entity.user;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "permissions")
public class PermissionEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy = "permissions")
    private List<RoleEntity> roles = new ArrayList<>();

    public PermissionEntity(){
    }

    public PermissionEntity(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public UUID getId() {return this.id;}
    public void setId(UUID id) {this.id = id;}

    public String getName() {return this.name;}
    public void setName(String name) {this.name = name;}

    public String getDescription() {return this.description;}
    public void setDescription(String description) {this.description = description;}

    public List<RoleEntity> getRoles() {return this.roles;}
    public void setRoles(List<RoleEntity> roles) {this.roles = roles;}

}
