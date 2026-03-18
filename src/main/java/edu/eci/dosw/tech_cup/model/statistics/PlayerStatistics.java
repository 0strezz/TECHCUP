package edu.eci.dosw.tech_cup.model.statistics;

public class PlayerStatistics {
    private int goals;
    private int yellowCards;
    private int redCards;

    public PlayerStatistics() {}

    public PlayerStatistics(int goals, int yellowCards, int redCards) {
        this.goals = goals;
        this.yellowCards = yellowCards;
        this.redCards = redCards;
    }

    public int getGoals() { return goals; }
    public void setGoals(int goals) { this.goals = goals; }
    public int getYellowCards() { return yellowCards; }
    public void setYellowCards(int yellowCards) { this.yellowCards = yellowCards; }
    public int getRedCards() { return redCards; }
    public void setRedCards(int redCards) { this.redCards = redCards; }
}
