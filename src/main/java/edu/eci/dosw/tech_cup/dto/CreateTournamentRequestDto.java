package main.java.edu.eci.dosw.tech_cup.dto;

import java.time.LocalDate;

public class CreateTournamentRequestDto {
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private int numberOfTeams;

    public CreateTournamentRequestDto() {

    }

    public CreateTournamentRequestDto(String name, LocalDate startDate, LocalDate endDate, int numberOfTeams) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.numberOfTeams = numberOfTeams;
    }

    public String getName() {
        return this.name;
    }
    public LocalDate startDate() {
        return this.startDate;
    }
    public LocalDate endDate() {
        return this.endDate;
    }
    public int getNumberOfTeams() {
        return this.numberOfTeams;
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
}
