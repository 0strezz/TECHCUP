package edu.eci.dosw.tech_cup.model.tournament;
import java.util.Date;
import java.util.UUID;

public class Tournament {
    private UUID id;
    private String name;
    private Date startDate;
    private Date endDate;
    private TournamentStatus status;
    private TournamentConfig config;
    private Field field;

    public Tournament(UUID id, String name, Date startDate, Date endDate, TournamentConfig config, Field field) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = TournamentStatus.DRAFT;
        this.config = config;
        this.field = field;
    }

    public void startTournament() {
        this.status = TournamentStatus.IN_PROGRESS;
    }

    public void finishTournament() {
        this.status = TournamentStatus.FINISHED;
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public Date getStartDate() { return startDate; }
    public Date getEndDate() { return endDate; }
    public TournamentStatus getStatus() { return status; }
    public TournamentConfig getConfig() { return config; }
    public Field getField() { return field; }

    public void setName(String name) { this.name = name; }
    public void setStartDate(Date startDate) { this.startDate = startDate; }
    public void setEndDate(Date endDate) { this.endDate = endDate; }
    public void setStatus(TournamentStatus status) { this.status = status; }
    public void setConfig(TournamentConfig config) { this.config = config; }
    public void setField(Field field) { this.field = field; }
}
