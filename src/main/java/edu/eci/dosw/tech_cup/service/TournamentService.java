package edu.eci.dosw.tech_cup.service;

import edu.eci.dosw.tech_cup.dto.CreateTournamentRequestDto;
import edu.eci.dosw.tech_cup.dto.TournamentResponseDto;
import edu.eci.dosw.tech_cup.dto.UpdateTournamentRequestDto;
import edu.eci.dosw.tech_cup.entity.tournament.TournamentEntity;
import edu.eci.dosw.tech_cup.model.enums.TournamentStatus;
import edu.eci.dosw.tech_cup.repository.TournamentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TournamentService {
    private static final Logger log = LoggerFactory.getLogger(TournamentService.class);

    private final TournamentRepository tournamentRepository;

    public TournamentService(TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }

    public TournamentResponseDto createTournament(CreateTournamentRequestDto request) {
        log.info("Creating tournament name={} teams={}", request.getName(), request.getNumberOfTeams());

        TournamentEntity entity = new TournamentEntity(
                request.getName(),
                request.getStartDate(),
                request.getEndDate(),
                request.getNumberOfTeams(),
                TournamentStatus.DRAFT,
                null
        );

        TournamentEntity saved = tournamentRepository.save(entity);
        log.debug("Tournament created with id={}", saved.getId());
        return toDto(saved, "Tournament created successfully");
    }

    public List<TournamentResponseDto> getAllTournaments() {
        log.debug("Fetching all tournaments");
        return tournamentRepository.findAll()
                .stream()
                .map(t -> toDto(t, null))
                .collect(Collectors.toList());
    }

    public TournamentResponseDto getTournamentById(UUID id) {
        log.debug("Searching tournament by id={}", id);
        Optional<TournamentEntity> opt = tournamentRepository.findById(id);
        if (opt.isEmpty()) {
            log.debug("Tournament not found for id={}", id);
            return null;
        }
        return toDto(opt.get(), null);
    }

    public TournamentResponseDto updateTournament(UUID id, UpdateTournamentRequestDto request) {
        log.info("Updating tournament id={}", id);
        Optional<TournamentEntity> opt = tournamentRepository.findById(id);

        if (opt.isEmpty()) {
            log.debug("Update rejected. tournament not found id={}", id);
            return error("Tournament not found");
        }

        TournamentEntity entity = opt.get();
        if (entity.getStatus() == TournamentStatus.FINISHED) {
            log.debug("Update rejected. tournament is FINISHED id={}", id);
            return error("Cannot update a finished tournament");
        }

        entity.setName(request.getName());
        entity.setStartDate(request.getStartDate());
        entity.setEndDate(request.getEndDate());
        entity.setNumberOfTeams(request.getNumberOfTeams());
        tournamentRepository.save(entity);
        log.info("Tournament updated successfully id={}", id);
        return toDto(entity, "Tournament updated successfully");
    }

    public TournamentResponseDto deleteTournament(UUID id) {
        log.info("Deleting tournament id={}", id);
        Optional<TournamentEntity> opt = tournamentRepository.findById(id);

        if (opt.isEmpty()) {
            log.debug("Delete rejected. tournament not found id={}", id);
            return error("Tournament not found");
        }

        TournamentEntity entity = opt.get();
        if (entity.getStatus() != TournamentStatus.DRAFT) {
            log.debug("Delete rejected. status is {} for id={}", entity.getStatus(), id);
            return error("Only tournaments in DRAFT can be deleted");
        }

        tournamentRepository.delete(entity);
        log.info("Tournament deleted id={}", id);
        return new TournamentResponseDto(null, null, null, null, 0, null, "Tournament deleted successfully");
    }

    public TournamentResponseDto startTournament(UUID id) {
        log.info("Starting tournament id={}", id);
        Optional<TournamentEntity> opt = tournamentRepository.findById(id);

        if (opt.isEmpty()) {
            log.debug("Start rejected. tournament not found id={}", id);
            return error("Tournament not found");
        }

        TournamentEntity entity = opt.get();
        if (entity.getStatus() != TournamentStatus.DRAFT) {
            log.debug("Start rejected. status is {} for id={}", entity.getStatus(), id);
            return error("Tournament cannot be started");
        }

        entity.setStatus(TournamentStatus.IN_PROGRESS);
        tournamentRepository.save(entity);
        log.info("Tournament started id={}", id);
        return toDto(entity, "Tournament started");
    }

    public TournamentResponseDto finishTournament(UUID id) {
        log.info("Finishing tournament id={}", id);
        Optional<TournamentEntity> opt = tournamentRepository.findById(id);

        if (opt.isEmpty()) {
            log.debug("Finish rejected. tournament not found id={}", id);
            return error("Tournament not found");
        }

        TournamentEntity entity = opt.get();
        if (entity.getStatus() != TournamentStatus.IN_PROGRESS) {
            log.debug("Finish rejected. status is {} for id={}", entity.getStatus(), id);
            return error("Tournament cannot be finished");
        }

        entity.setStatus(TournamentStatus.FINISHED);
        tournamentRepository.save(entity);
        log.info("Tournament finished id={}", id);
        return toDto(entity, "Tournament finished");
    }

    // --- Helpers ---

    private TournamentResponseDto toDto(TournamentEntity entity, String message) {
        return new TournamentResponseDto(
                entity.getId(),
                entity.getName(),
                entity.getStartDate(),
                entity.getEndDate(),
                entity.getNumberOfTeams(),
                entity.getStatus(),
                message
        );
    }

    private TournamentResponseDto error(String message) {
        log.debug("Returning tournament error response: {}", message);
        return new TournamentResponseDto(null, null, null, null, 0, null, message);
    }
}