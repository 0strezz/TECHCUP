package edu.eci.dosw.tech_cup.entity.tournament;

import edu.eci.dosw.tech_cup.entity.user.UserEntity;
import edu.eci.dosw.tech_cup.model.enums.TournamentStatus;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "tournaments")
public class TournamentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "number_of_teams", nullable = false)
    private int numberOfTeams;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TournamentStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizer_id")
    private UserEntity organizer;

    public TournamentEntity() {
    }

    public TournamentEntity(String name, LocalDate startDate, LocalDate endDate, int numberOfTeams, TournamentStatus status, UserEntity organizer) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.numberOfTeams = numberOfTeams;
        this.status = status;
        this.organizer = organizer;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public int getNumberOfTeams() { return numberOfTeams; }
    public void setNumberOfTeams(int numberOfTeams) { this.numberOfTeams = numberOfTeams; }

    public TournamentStatus getStatus() { return status; }
    public void setStatus(TournamentStatus status) { this.status = status; }

    public UserEntity getOrganizer() { return organizer; }
    public void setOrganizer(UserEntity organizer) { this.organizer = organizer; }
}
