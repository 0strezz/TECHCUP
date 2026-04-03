package edu.eci.dosw.tech_cup.repository;

import edu.eci.dosw.tech_cup.entity.tournament.TournamentEntity;
import edu.eci.dosw.tech_cup.entity.user.UserEntity;
import edu.eci.dosw.tech_cup.model.enums.TournamentStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class TournamentRepositoryTest {

    @Autowired
    private TournamentRepository tournamentRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("should save tournament with organizer")
    void shouldSaveTournament() {
        UserEntity user = userRepository.save(
                new UserEntity("Angela", "angela@mail.com", "123", "ADMIN", true, LocalDateTime.now())
        );

        TournamentEntity tournament = new TournamentEntity(
                "Copa",
                LocalDate.now(),
                LocalDate.now().plusDays(5),
                8,
                TournamentStatus.ACTIVE,
                user
        );

        TournamentEntity saved = tournamentRepository.save(tournament);

        assertNotNull(saved.getId());
        assertNotNull(saved.getOrganizer());
    }

    @Test
    @DisplayName("should find by status")
    void shouldFindByStatus() {
        UserEntity user = userRepository.save(
                new UserEntity("Angela", "angela@mail.com", "123", "ADMIN", true, LocalDateTime.now())
        );

        tournamentRepository.save(new TournamentEntity(
                "A", LocalDate.now(), LocalDate.now(), 8, TournamentStatus.ACTIVE, user
        ));

        tournamentRepository.save(new TournamentEntity(
                "B", LocalDate.now(), LocalDate.now(), 8, TournamentStatus.FINISHED, user
        ));

        List<TournamentEntity> result = tournamentRepository.findByStatus(TournamentStatus.ACTIVE);

        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("should find by organizer id")
    void shouldFindByOrganizerId() {
        UserEntity user = userRepository.save(
                new UserEntity("Angela", "angela@mail.com", "123", "ADMIN", true, LocalDateTime.now())
        );

        tournamentRepository.save(new TournamentEntity(
                "Copa", LocalDate.now(), LocalDate.now(), 8, TournamentStatus.ACTIVE, user
        ));

        List<TournamentEntity> result = tournamentRepository.findByOrganizerId(user.getId());

        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("should find by name containing")
    void shouldFindByName() {
        UserEntity user = userRepository.save(
                new UserEntity("Angela", "angela@mail.com", "123", "ADMIN", true, LocalDateTime.now())
        );

        tournamentRepository.save(new TournamentEntity(
                "Copa America", LocalDate.now(), LocalDate.now(), 8, TournamentStatus.ACTIVE, user
        ));

        List<TournamentEntity> result = tournamentRepository.findByNameContainingIgnoreCase("america");

        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("should delete tournament")
    void shouldDeleteTournament() {
        UserEntity user = userRepository.save(
                new UserEntity("Angela", "angela@mail.com", "123", "ADMIN", true, LocalDateTime.now())
        );

        TournamentEntity tournament = tournamentRepository.save(new TournamentEntity(
                "Copa", LocalDate.now(), LocalDate.now(), 8, TournamentStatus.ACTIVE, user
        ));

        tournamentRepository.deleteById(tournament.getId());

        assertTrue(tournamentRepository.findById(tournament.getId()).isEmpty());
    }
}