package edu.eci.dosw.tech_cup.model.user;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class User {
    protected UUID id;
    protected String name;
    protected String email;
    protected String password;
    protected List<Role> roles;

    public User() {
        this.id = UUID.randomUUID();
        this.roles = new ArrayList<>();
    }

    public User(String name, String email, String password) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.email = email;
        this.password = password;
        this.roles = new ArrayList<>();
    }

    public boolean checkPassword(String password) {
        return this.password != null && this.password.equals(password);
    }

    public boolean hasRole(String roleName) {
        return roles.stream().anyMatch(role -> role.getName().equalsIgnoreCase(roleName));
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}