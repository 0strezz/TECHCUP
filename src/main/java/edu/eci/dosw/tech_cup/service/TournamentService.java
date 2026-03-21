package edu.eci.dosw.tech_cup.service;

import edu.eci.dosw.tech_cup.dto.CreateTournamentRequestDto;
import edu.eci.dosw.tech_cup.dto.TournamentResponseDto;
import edu.eci.dosw.tech_cup.dto.UpdateTournamentRequestDto;
import edu.eci.dosw.tech_cup.model.enums.TournamentStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TournamentService {
    private static final Logger log = LoggerFactory.getLogger(TournamentService.class);

    private final List<TournamentResponseDto> tournaments;

    public TournamentService() {
        this.tournaments = new ArrayList<>();
        log.info("Initializing TournamentService with dummy tournaments");

        tournaments.add(new TournamentResponseDto(
                UUID.randomUUID(),
                "Copa ECI 2026",
                LocalDate.now(),
                LocalDate.now().plusDays(10),
                8,
                TournamentStatus.DRAFT,
                "Dummy tournament"
        ));
    }

    public TournamentResponseDto createTournament(CreateTournamentRequestDto request) {
        log.info("Creating tournament name={} teams={}", request.getName(), request.getNumberOfTeams());
        TournamentResponseDto tournament = new TournamentResponseDto(
                UUID.randomUUID(),
                request.getName(),
                request.getStartDate(),
                request.getEndDate(),
                request.getNumberOfTeams(),
                TournamentStatus.DRAFT,
                "Tournament created successfully"
        );

        tournaments.add(tournament);
        log.debug("Tournament created with id={}", tournament.getId());
        return tournament;
    }

    public List<TournamentResponseDto> getAllTournaments() {
        log.debug("Fetching all tournaments. count={}", tournaments.size());
        return new ArrayList<>(tournaments);
    }

    public TournamentResponseDto getTournamentById(UUID id) {
        log.debug("Searching tournament by id={}", id);
        for (TournamentResponseDto t : tournaments) {
            if (t.getId().equals(id)) {
                log.debug("Tournament found for id={}", id);
                return t;
            }
        }
        log.debug("Tournament not found for id={}", id);
        return null;
    }

    public TournamentResponseDto updateTournament(UUID id, UpdateTournamentRequestDto request) {
        log.info("Updating tournament id={}", id);
        TournamentResponseDto tournament = getTournamentById(id);

        if (tournament == null) {
            log.debug("Update rejected. tournament not found id={}", id);
            return error("Tournament not found");
        }

        if (tournament.getStatus() == TournamentStatus.FINISHED) {
            log.debug("Update rejected. tournament is FINISHED id={}", id);
            return error("Cannot update a finished tournament");
        }

        tournament.setName(request.getName());
        tournament.setStartDate(request.getStartDate());
        tournament.setEndDate(request.getEndDate());
        tournament.setNumberOfTeams(request.getNumberOfTeams());
        tournament.setMessage("Tournament updated successfully");
        log.info("Tournament updated successfully id={}", id);

        return tournament;
    }

    public TournamentResponseDto deleteTournament(UUID id) {
        log.info("Deleting tournament id={}", id);
        TournamentResponseDto tournament = getTournamentById(id);

        if (tournament == null) {
            log.debug("Delete rejected. tournament not found id={}", id);
            return error("Tournament not found");
        }

        if (tournament.getStatus() != TournamentStatus.DRAFT) {
            log.debug("Delete rejected. status is {} for id={}", tournament.getStatus(), id);
            return error("Only tournaments in DRAFT can be deleted");
        }

        tournaments.remove(tournament);
        log.info("Tournament deleted id={}", id);

        return new TournamentResponseDto(
                null,
                null,
                null,
                null,
                0,
                null,
                "Tournament deleted successfully"
        );
    }

    public TournamentResponseDto startTournament(UUID id) {
        log.info("Starting tournament id={}", id);
        TournamentResponseDto tournament = getTournamentById(id);

        if (tournament == null) {
            log.debug("Start rejected. tournament not found id={}", id);
            return error("Tournament not found");
        }

        if (tournament.getStatus() != TournamentStatus.DRAFT) {
            log.debug("Start rejected. status is {} for id={}", tournament.getStatus(), id);
            return error("Tournament cannot be started");
        }

        tournament.setStatus(TournamentStatus.IN_PROGRESS);
        tournament.setMessage("Tournament started");
        log.info("Tournament started id={}", id);

        return tournament;
    }

    public TournamentResponseDto finishTournament(UUID id) {
        log.info("Finishing tournament id={}", id);
        TournamentResponseDto tournament = getTournamentById(id);

        if (tournament == null) {
            log.debug("Finish rejected. tournament not found id={}", id);
            return error("Tournament not found");
        }

        if (tournament.getStatus() != TournamentStatus.IN_PROGRESS) {
            log.debug("Finish rejected. status is {} for id={}", tournament.getStatus(), id);
            return error("Tournament cannot be finished");
        }

        tournament.setStatus(TournamentStatus.FINISHED);
        tournament.setMessage("Tournament finished");
        log.info("Tournament finished id={}", id);

        return tournament;
    }

    private TournamentResponseDto error(String message) {
        log.debug("Returning tournament error response: {}", message);
        return new TournamentResponseDto(
                null,
                null,
                null,
                null,
                0,
                null,
                message
        );
    }
}