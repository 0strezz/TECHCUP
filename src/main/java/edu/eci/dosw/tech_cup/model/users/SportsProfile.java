package edu.eci.dosw.tech_cup.model.users;
public class SportsProfile {
    private int jerseyNumber;

    public SportsProfile(int jerseyNumber) {
        this.jerseyNumber = jerseyNumber;
    }

    public int getJerseyNumber() { return jerseyNumber; }
    public void setJerseyNumber(int jerseyNumber) { this.jerseyNumber = jerseyNumber; }
}
