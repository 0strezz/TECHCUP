package edu.eci.dosw.tech_cup.model.match;

import edu.eci.dosw.tech_cup.model.enums.MatchStatus;
import edu.eci.dosw.tech_cup.model.team.Team;
import edu.eci.dosw.tech_cup.model.tournament.Field;
import edu.eci.dosw.tech_cup.model.tournament.Tournament;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Match {
    private UUID id;
    private Date date;
    private MatchStatus status;
    private MatchResult result;
    private Team homeTeam;
    private Team awayTeam;
    private Field field;
    private Tournament tournament;
    private List<Goal> goals;
    private List<Card> cards;

    public Match() {
        this.id = UUID.randomUUID();
        this.status = MatchStatus.SCHEDULED;
        this.goals = new ArrayList<>();
        this.cards = new ArrayList<>();
    }

    public Match(Date date, Team homeTeam, Team awayTeam, Field field, Tournament tournament) {
        this.id = UUID.randomUUID();
        this.date = date;
        this.status = MatchStatus.SCHEDULED;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.field = field;
        this.tournament = tournament;
        this.goals = new ArrayList<>();
        this.cards = new ArrayList<>();
    }

    public void setResult(MatchResult result) {
        this.result = result;
        this.status = MatchStatus.PLAYED;
    }

    public UUID getId() { return id; }
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
    public MatchStatus getStatus() { return status; }
    public void setStatus(MatchStatus status) { this.status = status; }
    public MatchResult getResult() { return result; }
    public Team getHomeTeam() { return homeTeam; }
    public void setHomeTeam(Team homeTeam) { this.homeTeam = homeTeam; }
    public Team getAwayTeam() { return awayTeam; }
    public void setAwayTeam(Team awayTeam) { this.awayTeam = awayTeam; }
    public Field getField() { return field; }
    public void setField(Field field) { this.field = field; }
    public Tournament getTournament() { return tournament; }
    public void setTournament(Tournament tournament) { this.tournament = tournament; }
    public List<Goal> getGoals() { return goals; }
    public List<Card> getCards() { return cards; }
}
