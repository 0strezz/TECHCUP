package edu.eci.dosw.tech_cup.model.user;

import java.util.UUID;

public abstract class User {
    protected UUID id;
    protected String name;
    protected String email;
    protected String password;

    public User() {
        this.id = UUID.randomUUID();
    }

    public User(String name, String email, String password) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public boolean checkPassword(String password) {
        return this.password != null && this.password.equals(password);
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
