package main.java.edu.eci.dosw.tech_cup.dto;

public class TournamentResponseDto {
    private UUID id;
    private String name;
    private LocalDate startDate;    
    private LocalDate endDate;
    private int numberOfTeams;
    private TournamentStatus status;
    private String message;

    public TournamentResponseDto() {

    }

    public TournamentResponseDto(UUID id, String name, LocalDate startDate, LocalDate endDate, int numberOfTeams, TournamentStatus status, String message) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.numberOfTeams = numberOfTeams;
        this.status = status;
        this.message = message;
    }

    public UUID getId() {
        return this.id;
    }
    public String getName() {
        return this.name;
    }
    public LocalDate getStartDate() {
        return this.startDate;
    }
    public LocalDate getEndDate() {
        return this.endDate;
    }
    public int getNumberOfTeams() {
        return this.numberOfTeams;
    }
    public TournamentStatus getStatus() {
        return this.status;
    }
    public String getMessage() {
        return this.message;
    }
    
    public void setId(UUID id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    public void setNumberOfTeams(int numberOfTeams) {
        this.numberOfTeams = numberOfTeams;
    }
    public void setStatus(TournamentStatus status) {
        this.status = status;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
