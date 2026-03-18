package edu.eci.dosw.tech_cup.model.match;

public class Goal {
    private int minute;

    public Goal() {}

    public Goal(int minute) {
        this.minute = minute;
    }

    public int getMinute() { return minute; }
    public void setMinute(int minute) { this.minute = minute; }
}
