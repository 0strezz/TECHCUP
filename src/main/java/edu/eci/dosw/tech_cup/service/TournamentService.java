package main.java.edu.eci.dosw.tech_cup.service;

import edu.eci.dosw.tech_cup.dto.CreateTournamentRequestDto;
import edu.eci.dosw.tech_cup.dto.TournamentResponseDto;
import edu.eci.dosw.tech_cup.dto.UpdateTournamentRequestDto;
import edu.eci.dosw.tech_cup.model.TournamentStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TournamentService {

    private final List<TournamentResponseDto> tournaments;

    public TournamentService() {
        this.tournaments = new ArrayList<>();

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
        return tournament;
    }

    public List<TournamentResponseDto> getAllTournaments() {
        return new ArrayList<>(tournaments);
    }

    public TournamentResponseDto getTournamentById(UUID id) {
        for (TournamentResponseDto t : tournaments) {
            if (t.getId().equals(id)) {
                return t;
            }
        }
        return null;
    }

    public TournamentResponseDto updateTournament(UUID id, UpdateTournamentRequestDto request) {
        TournamentResponseDto tournament = getTournamentById(id);

        if (tournament == null) {
            return error("Tournament not found");
        }

        if (tournament.getStatus() == TournamentStatus.FINALIZADO) {
            return error("Cannot update a finished tournament");
        }

        tournament.setName(request.getName());
        tournament.setStartDate(request.getStartDate());
        tournament.setEndDate(request.getEndDate());
        tournament.setNumberOfTeams(request.getNumberOfTeams());
        tournament.setMessage("Tournament updated successfully");

        return tournament;
    }

    public TournamentResponseDto deleteTournament(UUID id) {
        TournamentResponseDto tournament = getTournamentById(id);

        if (tournament == null) {
            return error("Tournament not found");
        }

        if (tournament.getStatus() != TournamentStatus.DRAFT) {
            return error("Only tournaments in DRAFT can be deleted");
        }

        tournaments.remove(tournament);

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
        TournamentResponseDto tournament = getTournamentById(id);

        if (tournament == null) {
            return error("Tournament not found");
        }

        if (tournament.getStatus() != TournamentStatus.DRAFT) {
            return error("Tournament cannot be started");
        }

        tournament.setStatus(TournamentStatus.IN_PROGRESS);
        tournament.setMessage("Tournament started");

        return tournament;
    }

    public TournamentResponseDto finishTournament(UUID id) {
        TournamentResponseDto tournament = getTournamentById(id);

        if (tournament == null) {
            return error("Tournament not found");
        }

        if (tournament.getStatus() != TournamentStatus.EN_PROGRESO) {
            return error("Tournament cannot be finished");
        }

        tournament.setStatus(TournamentStatus.FINALIZADO);
        tournament.setMessage("Tournament finished");

        return tournament;
    }

    private TournamentResponseDto error(String message) {
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