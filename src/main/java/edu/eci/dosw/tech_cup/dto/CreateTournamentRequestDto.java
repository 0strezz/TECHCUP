package edu.eci.dosw.tech_cup.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class CreateTournamentRequestDto {

    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name must not exceed 100 characters")
    private String name;

    @NotNull(message = "Start date is required")
    @FutureOrPresent(message = "Start date must be today or a future date")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    private LocalDate endDate;

    @Min(value = 2, message = "Number of teams must be at least 2")
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

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public LocalDate getEndDate() {
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