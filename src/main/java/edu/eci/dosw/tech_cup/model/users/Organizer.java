package edu.eci.dosw.tech_cup.model.users;

import java.util.UUID;

public class Organizer extends User {

    public Organizer(UUID id, String name, String email, String password) {
        super(id, name, email, password);
    }
}
