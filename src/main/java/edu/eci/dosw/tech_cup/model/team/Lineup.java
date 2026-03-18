package edu.eci.dosw.tech_cup.model.team;

import edu.eci.dosw.tech_cup.model.enums.Formation;

public class Lineup {
    private Formation formation;

    public Lineup() {}

    public Lineup(Formation formation) {
        this.formation = formation;
    }

    public void setFormation(Formation formation) {
        this.formation = formation;
    }

    public Formation getFormation() { return formation; }
}
