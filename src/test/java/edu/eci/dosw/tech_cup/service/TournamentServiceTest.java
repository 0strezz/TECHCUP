package edu.eci.dosw.tech_cup.service;

import edu.eci.dosw.tech_cup.dto.CreateTournamentRequestDto;
import edu.eci.dosw.tech_cup.dto.TournamentResponseDto;
import edu.eci.dosw.tech_cup.dto.UpdateTournamentRequestDto;
import edu.eci.dosw.tech_cup.entity.tournament.TournamentEntity;
import edu.eci.dosw.tech_cup.model.enums.TournamentStatus;
import edu.eci.dosw.tech_cup.repository.TournamentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("TournamentService tests")
class TournamentServiceTest {

    @Mock
    private TournamentRepository tournamentRepository;

    @InjectMocks
    private TournamentService tournamentService;

    private TournamentEntity draftTournament;
    private TournamentEntity activeTournament;
    private TournamentEntity finishedTournament;

    @BeforeEach
    void setUp() {
        draftTournament = new TournamentEntity("Draft", LocalDate.now(), LocalDate.now(), 8, TournamentStatus.DRAFT, null);
        draftTournament.setId(UUID.randomUUID());

        activeTournament = new TournamentEntity("Active", LocalDate.now(), LocalDate.now(), 8, TournamentStatus.IN_PROGRESS, null);
        activeTournament.setId(UUID.randomUUID());

        finishedTournament = new TournamentEntity("Finished", LocalDate.now(), LocalDate.now(), 8, TournamentStatus.FINISHED, null);
        finishedTournament.setId(UUID.randomUUID());
    }

    @Test
    void createTournament() {
        CreateTournamentRequestDto req = new CreateTournamentRequestDto("New", LocalDate.now(), LocalDate.now(), 4);
        when(tournamentRepository.save(any(TournamentEntity.class))).thenAnswer(i -> {
            TournamentEntity e = i.getArgument(0);
            e.setId(UUID.randomUUID());
            return e;
        });

        TournamentResponseDto res = tournamentService.createTournament(req);
        assertEquals("Tournament created successfully", res.getMessage());
        assertEquals("New", res.getName());
    }

    @Test
    void getAllTournaments() {
        when(tournamentRepository.findAll()).thenReturn(List.of(draftTournament));
        List<TournamentResponseDto> res = tournamentService.getAllTournaments();
        assertEquals(1, res.size());
    }

    @Test
    void getTournamentById_Found() {
        when(tournamentRepository.findById(draftTournament.getId())).thenReturn(Optional.of(draftTournament));
        TournamentResponseDto res = tournamentService.getTournamentById(draftTournament.getId());
        assertNotNull(res);
        assertEquals("Draft", res.getName());
    }

    @Test
    void getTournamentById_NotFound() {
        when(tournamentRepository.findById(any())).thenReturn(Optional.empty());
        TournamentResponseDto res = tournamentService.getTournamentById(UUID.randomUUID());
        assertNull(res);
    }

    @Test
    void updateTournament_NotFound() {
        when(tournamentRepository.findById(any())).thenReturn(Optional.empty());
        TournamentResponseDto res = tournamentService.updateTournament(UUID.randomUUID(), new UpdateTournamentRequestDto());
        assertEquals("Tournament not found", res.getMessage());
    }

    @Test
    void updateTournament_Finished() {
        when(tournamentRepository.findById(finishedTournament.getId())).thenReturn(Optional.of(finishedTournament));
        TournamentResponseDto res = tournamentService.updateTournament(finishedTournament.getId(), new UpdateTournamentRequestDto());
        assertEquals("Cannot update a finished tournament", res.getMessage());
    }

    @Test
    void updateTournament_Success() {
        when(tournamentRepository.findById(draftTournament.getId())).thenReturn(Optional.of(draftTournament));
        when(tournamentRepository.save(any())).thenReturn(draftTournament);

        UpdateTournamentRequestDto req = new UpdateTournamentRequestDto("Updated", LocalDate.now(), LocalDate.now(), 10);
        TournamentResponseDto res = tournamentService.updateTournament(draftTournament.getId(), req);

        assertEquals("Tournament updated successfully", res.getMessage());
        assertEquals("Updated", draftTournament.getName());
        assertEquals(10, draftTournament.getNumberOfTeams());
    }

    @Test
    void deleteTournament_NotFound() {
        when(tournamentRepository.findById(any())).thenReturn(Optional.empty());
        TournamentResponseDto res = tournamentService.deleteTournament(UUID.randomUUID());
        assertEquals("Tournament not found", res.getMessage());
    }

    @Test
    void deleteTournament_NotDraft() {
        when(tournamentRepository.findById(activeTournament.getId())).thenReturn(Optional.of(activeTournament));
        TournamentResponseDto res = tournamentService.deleteTournament(activeTournament.getId());
        assertEquals("Only tournaments in DRAFT can be deleted", res.getMessage());
    }

    @Test
    void deleteTournament_Success() {
        when(tournamentRepository.findById(draftTournament.getId())).thenReturn(Optional.of(draftTournament));
        TournamentResponseDto res = tournamentService.deleteTournament(draftTournament.getId());
        assertEquals("Tournament deleted successfully", res.getMessage());
        verify(tournamentRepository).delete(draftTournament);
    }

    @Test
    void startTournament_NotFound() {
        when(tournamentRepository.findById(any())).thenReturn(Optional.empty());
        TournamentResponseDto res = tournamentService.startTournament(UUID.randomUUID());
        assertEquals("Tournament not found", res.getMessage());
    }

    @Test
    void startTournament_NotDraft() {
        when(tournamentRepository.findById(finishedTournament.getId())).thenReturn(Optional.of(finishedTournament));
        TournamentResponseDto res = tournamentService.startTournament(finishedTournament.getId());
        assertEquals("Tournament cannot be started", res.getMessage());
    }

    @Test
    void startTournament_Success() {
        when(tournamentRepository.findById(draftTournament.getId())).thenReturn(Optional.of(draftTournament));
        TournamentResponseDto res = tournamentService.startTournament(draftTournament.getId());
        assertEquals("Tournament started", res.getMessage());
        assertEquals(TournamentStatus.IN_PROGRESS, draftTournament.getStatus());
        verify(tournamentRepository).save(draftTournament);
    }

    @Test
    void finishTournament_NotFound() {
        when(tournamentRepository.findById(any())).thenReturn(Optional.empty());
        TournamentResponseDto res = tournamentService.finishTournament(UUID.randomUUID());
        assertEquals("Tournament not found", res.getMessage());
    }

    @Test
    void finishTournament_NotInProgress() {
        when(tournamentRepository.findById(draftTournament.getId())).thenReturn(Optional.of(draftTournament));
        TournamentResponseDto res = tournamentService.finishTournament(draftTournament.getId());
        assertEquals("Tournament cannot be finished", res.getMessage());
    }

    @Test
    void finishTournament_Success() {
        when(tournamentRepository.findById(activeTournament.getId())).thenReturn(Optional.of(activeTournament));
        TournamentResponseDto res = tournamentService.finishTournament(activeTournament.getId());
        assertEquals("Tournament finished", res.getMessage());
        assertEquals(TournamentStatus.FINISHED, activeTournament.getStatus());
        verify(tournamentRepository).save(activeTournament);
    }
}
