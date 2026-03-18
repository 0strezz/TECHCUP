package edu.eci.dosw.tech_cup.model.match;

import edu.eci.dosw.tech_cup.model.enums.CardType;

public class Card {
    private int minute;
    private CardType type;

    public Card() {}

    public Card(int minute, CardType type) {
        this.minute = minute;
        this.type = type;
    }

    public int getMinute() { return minute; }
    public void setMinute(int minute) { this.minute = minute; }
    public CardType getType() { return type; }
    public void setType(CardType type) { this.type = type; }
}
