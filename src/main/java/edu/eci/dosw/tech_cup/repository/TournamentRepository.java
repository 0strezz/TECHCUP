package edu.eci.dosw.tech_cup.repository;

import edu.eci.dosw.tech_cup.entity.tournament.TournamentEntity;
import edu.eci.dosw.tech_cup.model.enums.TournamentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TournamentRepository extends JpaRepository<TournamentEntity, UUID> {

    List<TournamentEntity> findByStatus(TournamentStatus status);

    List<TournamentEntity> findByOrganizerId(UUID organizerId);

    List<TournamentEntity> findByNameContainingIgnoreCase(String name);
}
