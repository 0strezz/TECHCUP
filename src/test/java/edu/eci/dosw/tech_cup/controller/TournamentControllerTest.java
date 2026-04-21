package edu.eci.dosw.tech_cup.controller;

import edu.eci.dosw.tech_cup.dto.CreateTournamentRequestDto;
import edu.eci.dosw.tech_cup.dto.TournamentResponseDto;
import edu.eci.dosw.tech_cup.dto.UpdateTournamentRequestDto;
import edu.eci.dosw.tech_cup.service.TournamentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("TournamentController tests")
class TournamentControllerTest {

    @Mock
    private TournamentService tournamentService;

    @InjectMocks
    private TournamentController tournamentController;

    @Test
    void createTournament() {
        CreateTournamentRequestDto req = new CreateTournamentRequestDto();
        TournamentResponseDto res = new TournamentResponseDto();
        when(tournamentService.createTournament(req)).thenReturn(res);

        ResponseEntity<TournamentResponseDto> response = tournamentController.createTournament(req);
        assertEquals(201, response.getStatusCode().value());
    }

    @Test
    void getAllTournaments() {
        when(tournamentService.getAllTournaments()).thenReturn(List.of(new TournamentResponseDto()));
        ResponseEntity<List<TournamentResponseDto>> response = tournamentController.getAllTournaments();
        assertEquals(200, response.getStatusCode().value());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void getTournamentById_Found() {
        UUID id = UUID.randomUUID();
        when(tournamentService.getTournamentById(id)).thenReturn(new TournamentResponseDto());
        ResponseEntity<TournamentResponseDto> response = tournamentController.getTournamentById(id);
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void getTournamentById_NotFound() {
        UUID id = UUID.randomUUID();
        when(tournamentService.getTournamentById(id)).thenReturn(null);
        ResponseEntity<TournamentResponseDto> response = tournamentController.getTournamentById(id);
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void updateTournament_Found() {
        UUID id = UUID.randomUUID();
        UpdateTournamentRequestDto req = new UpdateTournamentRequestDto();
        TournamentResponseDto res = new TournamentResponseDto();
        res.setMessage("Tournament updated successfully");
        when(tournamentService.updateTournament(id, req)).thenReturn(res);

        ResponseEntity<TournamentResponseDto> response = tournamentController.updateTournament(id, req);
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void updateTournament_Failure() {
        UUID id = UUID.randomUUID();
        UpdateTournamentRequestDto req = new UpdateTournamentRequestDto();
        TournamentResponseDto res = new TournamentResponseDto();
        res.setMessage("Tournament not found");
        when(tournamentService.updateTournament(id, req)).thenReturn(res);

        ResponseEntity<TournamentResponseDto> response = tournamentController.updateTournament(id, req);
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void deleteTournament_Found() {
        UUID id = UUID.randomUUID();
        TournamentResponseDto res = new TournamentResponseDto();
        res.setMessage("Tournament deleted successfully");
        when(tournamentService.deleteTournament(id)).thenReturn(res);

        ResponseEntity<TournamentResponseDto> response = tournamentController.deleteTournament(id);
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void deleteTournament_Failure() {
        UUID id = UUID.randomUUID();
        TournamentResponseDto res = new TournamentResponseDto();
        res.setMessage("Only tournaments in DRAFT can be deleted");
        when(tournamentService.deleteTournament(id)).thenReturn(res);

        ResponseEntity<TournamentResponseDto> response = tournamentController.deleteTournament(id);
        assertEquals(400, response.getStatusCode().value());
    }

    @Test
    void startTournament_Success() {
        UUID id = UUID.randomUUID();
        TournamentResponseDto res = new TournamentResponseDto();
        res.setMessage("Tournament started");
        when(tournamentService.startTournament(id)).thenReturn(res);

        ResponseEntity<TournamentResponseDto> response = tournamentController.startTournament(id);
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void startTournament_Failure() {
        UUID id = UUID.randomUUID();
        TournamentResponseDto res = new TournamentResponseDto();
        res.setMessage("Tournament cannot be started");
        when(tournamentService.startTournament(id)).thenReturn(res);

        ResponseEntity<TournamentResponseDto> response = tournamentController.startTournament(id);
        assertEquals(400, response.getStatusCode().value());
    }

    @Test
    void finishTournament_Success() {
        UUID id = UUID.randomUUID();
        TournamentResponseDto res = new TournamentResponseDto();
        res.setMessage("Tournament finished");
        when(tournamentService.finishTournament(id)).thenReturn(res);

        ResponseEntity<TournamentResponseDto> response = tournamentController.finishTournament(id);
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void finishTournament_Failure() {
        UUID id = UUID.randomUUID();
        TournamentResponseDto res = new TournamentResponseDto();
        res.setMessage("error");
        when(tournamentService.finishTournament(id)).thenReturn(res);

        ResponseEntity<TournamentResponseDto> response = tournamentController.finishTournament(id);
        assertEquals(200, response.getStatusCode().value());
    }
}
