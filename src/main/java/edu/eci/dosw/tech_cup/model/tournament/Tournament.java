package edu.eci.dosw.tech_cup.model.tournament;

import edu.eci.dosw.tech_cup.model.enums.TournamentStatus;
import edu.eci.dosw.tech_cup.model.team.Team;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Tournament {
    private UUID id;
    private String name;
    private Date startDate;
    private Date endDate;
    private TournamentStatus status;
    private TournamentConfig config;
    private List<Team> teams;

    public Tournament() {
        this.id = UUID.randomUUID();
        this.status = TournamentStatus.DRAFT;
        this.teams = new ArrayList<>();
    }

    public Tournament(String name, Date startDate, Date endDate) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = TournamentStatus.DRAFT;
        this.teams = new ArrayList<>();
    }

    public void startTournament() {
        if (this.status == TournamentStatus.DRAFT) {
            this.status = TournamentStatus.ACTIVE;
        }
    }

    public void finishTournament() {
        if (this.status == TournamentStatus.IN_PROGRESS || this.status == TournamentStatus.ACTIVE) {
            this.status = TournamentStatus.FINISHED;
        }
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Date getStartDate() { return startDate; }
    public void setStartDate(Date startDate) { this.startDate = startDate; }
    public Date getEndDate() { return endDate; }
    public void setEndDate(Date endDate) { this.endDate = endDate; }
    public TournamentStatus getStatus() { return status; }
    public void setStatus(TournamentStatus status) { this.status = status; }
    public TournamentConfig getConfig() { return config; }
    public void setConfig(TournamentConfig config) { this.config = config; }
    public List<Team> getTeams() { return teams; }
    public void setTeams(List<Team> teams) { this.teams = teams; }
}
