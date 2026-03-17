package edu.eci.dosw.tech_cup.model.users;

import java.util.UUID;

public class Referee extends User {

    public Referee(UUID id, String name, String email, String password) {
        super(id, name, email, password);
    }
}
