package edu.eci.dosw.tech_cup.model.user;

public class Player extends User implements SportProfile {
    private boolean availableForTeam;
    private boolean underPlayingProcess;

    public Player() {}

    public Player(String name, String email, String password) {
        super(name, email, password);
        this.availableForTeam = true;
        this.underPlayingProcess = false;
    }

    @Override
    public boolean isAvailableForTeam() { return availableForTeam; }

    @Override
    public void setAvailableForTeam(boolean available) { this.availableForTeam = available; }

    @Override
    public boolean isUnderPlayingProcess() { return underPlayingProcess; }

    @Override
    public void setUnderPlayingProcess(boolean underPlayingProcess) {
        this.underPlayingProcess = underPlayingProcess;
    }
}
