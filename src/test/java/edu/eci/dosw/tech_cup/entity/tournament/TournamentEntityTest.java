package edu.eci.dosw.tech_cup.entity.tournament;

import edu.eci.dosw.tech_cup.entity.user.UserEntity;
import edu.eci.dosw.tech_cup.model.enums.TournamentStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("TournamentEntity tests")
class TournamentEntityTest {

    @Test
    @DisplayName("Empty constructor works")
    void emptyConstructor() {
        TournamentEntity tournament = new TournamentEntity();
        assertNull(tournament.getId());
        assertNull(tournament.getName());
        assertNull(tournament.getStartDate());
        assertNull(tournament.getEndDate());
        assertEquals(0, tournament.getNumberOfTeams());
        assertNull(tournament.getStatus());
        assertNull(tournament.getOrganizer());
    }

    @Test
    @DisplayName("Full constructor works")
    void fullConstructor() {
        LocalDate start = LocalDate.now();
        LocalDate end = start.plusDays(10);
        UserEntity organizer = new UserEntity();

        TournamentEntity tournament = new TournamentEntity(
                "MyTourney", start, end, 8, TournamentStatus.DRAFT, organizer
        );

        assertEquals("MyTourney", tournament.getName());
        assertEquals(start, tournament.getStartDate());
        assertEquals(end, tournament.getEndDate());
        assertEquals(8, tournament.getNumberOfTeams());
        assertEquals(TournamentStatus.DRAFT, tournament.getStatus());
        assertEquals(organizer, tournament.getOrganizer());
    }

    @Test
    @DisplayName("Getters and Setters work")
    void gettersAndSetters() {
        TournamentEntity tournament = new TournamentEntity();
        UUID id = UUID.randomUUID();
        LocalDate start = LocalDate.now();
        LocalDate end = start.plusDays(10);
        UserEntity organizer = new UserEntity();

        tournament.setId(id);
        tournament.setName("TourneyName");
        tournament.setStartDate(start);
        tournament.setEndDate(end);
        tournament.setNumberOfTeams(16);
        tournament.setStatus(TournamentStatus.IN_PROGRESS);
        tournament.setOrganizer(organizer);

        assertEquals(id, tournament.getId());
        assertEquals("TourneyName", tournament.getName());
        assertEquals(start, tournament.getStartDate());
        assertEquals(end, tournament.getEndDate());
        assertEquals(16, tournament.getNumberOfTeams());
        assertEquals(TournamentStatus.IN_PROGRESS, tournament.getStatus());
        assertEquals(organizer, tournament.getOrganizer());
    }
}
