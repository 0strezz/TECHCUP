package edu.eci.dosw.tech_cup.model.users;

public interface Player {
    boolean isAvailableForTeam();
    void setAvailability(boolean status);
}
