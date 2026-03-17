package edu.eci.dosw.tech_cup.model.users;
import java.util.UUID;

public class Staff extends User implements Player {
    private boolean availableForTeam;

    public Staff(UUID id, String name, String email, String password) {
        super(id, name, email, password);
        this.availableForTeam = true;
    }

    @Override
    public boolean isAvailableForTeam() { return availableForTeam; }

    @Override
    public void setAvailability(boolean status) { this.availableForTeam = status; }
}
