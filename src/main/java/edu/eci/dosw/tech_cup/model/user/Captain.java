package edu.eci.dosw.tech_cup.model.user;

import edu.eci.dosw.tech_cup.model.team.Team;
import java.util.UUID;

public class Captain extends Player {

    public Captain() {}

    public Captain(String name, String email, String password) {
        super(name, email, password);
    }

    public UUID createTeam(String name) {
        // Delegates to TeamService in a real application
        return UUID.randomUUID();
    }
}
