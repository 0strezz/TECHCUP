package edu.eci.dosw.tech_cup.controller;

import edu.eci.dosw.tech_cup.dto.CreateTournamentRequestDto;
import edu.eci.dosw.tech_cup.dto.TournamentResponseDto;
import edu.eci.dosw.tech_cup.dto.UpdateTournamentRequestDto;
import edu.eci.dosw.tech_cup.service.TournamentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tournaments")
@Tag(name = "Tournaments", description = "Operations related to tournament management")
public class TournamentController {

    private final TournamentService tournamentService;

    public TournamentController(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    @PostMapping
    @Operation(
            summary = "Create tournament",
            description = "Creates a new tournament. Every new tournament starts in DRAFT status by default."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tournament created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    public ResponseEntity<TournamentResponseDto> createTournament(@Valid @RequestBody CreateTournamentRequestDto request) {
        TournamentResponseDto response = tournamentService.createTournament(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(
            summary = "Get all tournaments",
            description = "Returns the complete list of tournaments."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tournaments retrieved successfully")
    })
    public ResponseEntity<List<TournamentResponseDto>> getAllTournaments() {
        List<TournamentResponseDto> tournaments = tournamentService.getAllTournaments();
        return ResponseEntity.ok(tournaments);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get tournament by id",
            description = "Returns a tournament by its identifier."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tournament retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Tournament not found")
    })
    public ResponseEntity<TournamentResponseDto> getTournamentById(@PathVariable UUID id) {
        TournamentResponseDto tournament = tournamentService.getTournamentById(id);

        if (tournament == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(tournament);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update tournament",
            description = "Updates an existing tournament. A finished tournament cannot be updated."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tournament updated successfully"),
            @ApiResponse(responseCode = "404", description = "Tournament not found"),
            @ApiResponse(responseCode = "400", description = "Tournament cannot be updated")
    })
    public ResponseEntity<TournamentResponseDto> updateTournament(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateTournamentRequestDto request) {

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
    @Operation(
            summary = "Delete tournament",
            description = "Deletes a tournament only if it is in DRAFT status."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tournament deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Tournament not found"),
            @ApiResponse(responseCode = "400", description = "Tournament cannot be deleted")
    })
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
    @Operation(
            summary = "Start tournament",
            description = "Starts a tournament when it is allowed by the business rules."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tournament started successfully"),
            @ApiResponse(responseCode = "404", description = "Tournament not found"),
            @ApiResponse(responseCode = "400", description = "Tournament cannot be started")
    })
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
    @Operation(
            summary = "Finish tournament",
            description = "Finishes a tournament when it is allowed by the business rules."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tournament finished successfully"),
            @ApiResponse(responseCode = "404", description = "Tournament not found"),
            @ApiResponse(responseCode = "400", description = "Tournament cannot be finished")
    })
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