package main.java.edu.eci.dosw.tech_cup.controller;

import edu.eci.dosw.tech_cup.dto.CreateTournamentRequestDto;
import edu.eci.dosw.tech_cup.dto.TournamentResponseDto;
import edu.eci.dosw.tech_cup.dto.UpdateTournamentRequestDto;
import edu.eci.dosw.tech_cup.service.TournamentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tournaments")
public class TournamentController {

    private final TournamentService tournamentService;

    public TournamentController(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    @PostMapping
    public ResponseEntity<TournamentResponseDto> createTournament(@RequestBody CreateTournamentRequestDto request) {
        TournamentResponseDto response = tournamentService.createTournament(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<TournamentResponseDto>> getAllTournaments() {
        List<TournamentResponseDto> tournaments = tournamentService.getAllTournaments();
        return ResponseEntity.ok(tournaments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TournamentResponseDto> getTournamentById(@PathVariable UUID id) {
        TournamentResponseDto tournament = tournamentService.getTournamentById(id);

        if (tournament == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(tournament);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TournamentResponseDto> updateTournament(
            @PathVariable UUID id,
            @RequestBody UpdateTournamentRequestDto request) {

        TournamentResponseDto tournament = tournamentService.updateTournament(id, request);

        if (tournament == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        if ("Tournament not found".equals(tournament.getMessage())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(tournament);
        }

        if ("Cannot update a finished tournament".equals(tournament.getMessage())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(tournament);
        }

        return ResponseEntity.ok(tournament);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TournamentResponseDto> deleteTournament(@PathVariable UUID id) {
        TournamentResponseDto tournament = tournamentService.deleteTournament(id);

        if (tournament == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        if ("Tournament not found".equals(tournament.getMessage())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(tournament);
        }

        if ("Only tournaments in DRAFT can be deleted".equals(tournament.getMessage())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(tournament);
        }

        return ResponseEntity.ok(tournament);
    }

    @PatchMapping("/{id}/start")
    public ResponseEntity<TournamentResponseDto> startTournament(@PathVariable UUID id) {
        TournamentResponseDto tournament = tournamentService.startTournament(id);

        if (tournament == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        if ("Tournament not found".equals(tournament.getMessage())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(tournament);
        }

        if ("Tournament cannot be started".equals(tournament.getMessage())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(tournament);
        }

        return ResponseEntity.ok(tournament);
    }

    @PatchMapping("/{id}/finish")
    public ResponseEntity<TournamentResponseDto> finishTournament(@PathVariable UUID id) {
        TournamentResponseDto tournament = tournamentService.finishTournament(id);

        if (tournament == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        if ("Tournament not found".equals(tournament.getMessage())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(tournament);
        }

        if ("Tournament cannot be finished".equals(tournament.getMessage())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(tournament);
        }

        return ResponseEntity.ok(tournament);
    }
}