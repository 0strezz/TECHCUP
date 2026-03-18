package edu.eci.dosw.tech_cup.model.match;

public class MatchResult {
    private int homeGoals;
    private int awayGoals;

    public MatchResult() {}

    public MatchResult(int homeGoals, int awayGoals) {
        this.homeGoals = homeGoals;
        this.awayGoals = awayGoals;
    }

    public int getHomeGoals() { return homeGoals; }
    public void setHomeGoals(int homeGoals) { this.homeGoals = homeGoals; }
    public int getAwayGoals() { return awayGoals; }
    public void setAwayGoals(int awayGoals) { this.awayGoals = awayGoals; }
}
