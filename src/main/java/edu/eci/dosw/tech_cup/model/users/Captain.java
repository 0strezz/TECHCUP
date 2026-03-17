package edu.eci.dosw.tech_cup.model.users;

import java.util.UUID;

public class Captain extends User implements Player {
    private boolean availableForTeam;

    public Captain(UUID id, String name, String email, String password) {
        super(id, name, email, password);
        this.availableForTeam = true;
    }

    public void createTeam(String name) {
        System.out.println("Team '" + name + "' created by captain " + getName());
    }

    @Override
    public boolean isAvailableForTeam() { return availableForTeam; }

    @Override
    public void setAvailability(boolean status) { this.availableForTeam = status; }
}
