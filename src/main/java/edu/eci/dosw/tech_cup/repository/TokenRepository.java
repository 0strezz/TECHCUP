package edu.eci.dosw.tech_cup.repository;

import edu.eci.dosw.tech_cup.entity.auth.TokenEntity;
import edu.eci.dosw.tech_cup.entity.auth.TokenType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TokenRepository extends JpaRepository<TokenEntity, UUID> {

    Optional<TokenEntity> findByToken(String token);

    Optional<TokenEntity> findByTokenAndRevokedFalseAndTokenType(String token, TokenType tokenType);

    List<TokenEntity> findByUserIdAndRevokedFalseAndTokenType(UUID userId, TokenType tokenType);

    void deleteByUserId(UUID userId);
}
