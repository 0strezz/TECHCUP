package edu.eci.dosw.tech_cup.model.users;

import java.util.UUID;

public class Administrator extends User {

    public Administrator(UUID id, String name, String email, String password) {
        super(id, name, email, password);
    }
}
