CREATE TABLE formation (
    formation_id        BIGSERIAL PRIMARY KEY,
    name                VARCHAR(20) NOT NULL UNIQUE,
    description         TEXT
);

CREATE TABLE match (
    match_id                BIGSERIAL PRIMARY KEY,
    tournament_id           BIGINT NOT NULL,
    tournament_stage_id     BIGINT NOT NULL,
    field_id                BIGINT NOT NULL,
    home_team_id            BIGINT NOT NULL,
    away_team_id            BIGINT NOT NULL,
    referee_user_id         BIGINT,
    created_by              BIGINT NOT NULL,
    match_date              TIMESTAMP NOT NULL,
    status                  match_status_enum NOT NULL DEFAULT 'scheduled',
    home_score              INT NOT NULL DEFAULT 0,
    away_score              INT NOT NULL DEFAULT 0,
    created_at              TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at              TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_match_tournament
        FOREIGN KEY (tournament_id) REFERENCES tournament(tournament_id) ON DELETE CASCADE,
    CONSTRAINT fk_match_stage
        FOREIGN KEY (tournament_stage_id) REFERENCES tournament_stage(tournament_stage_id) ON DELETE RESTRICT,
    CONSTRAINT fk_match_field
        FOREIGN KEY (field_id) REFERENCES field(field_id) ON DELETE RESTRICT,
    CONSTRAINT fk_match_home_team
        FOREIGN KEY (home_team_id) REFERENCES team(team_id) ON DELETE RESTRICT,
    CONSTRAINT fk_match_away_team
        FOREIGN KEY (away_team_id) REFERENCES team(team_id) ON DELETE RESTRICT,
    CONSTRAINT fk_match_referee
        FOREIGN KEY (referee_user_id) REFERENCES app_user(user_id) ON DELETE SET NULL,
    CONSTRAINT fk_match_created_by
        FOREIGN KEY (created_by) REFERENCES app_user(user_id) ON DELETE RESTRICT,
    CONSTRAINT chk_match_teams_different
        CHECK (home_team_id <> away_team_id),
    CONSTRAINT chk_match_scores
        CHECK (home_score >= 0 AND away_score >= 0)
);

CREATE TABLE team_lineup (
    team_lineup_id       BIGSERIAL PRIMARY KEY,
    match_id             BIGINT NOT NULL,
    team_id              BIGINT NOT NULL,
    formation_id         BIGINT,
    status               lineup_status_enum NOT NULL DEFAULT 'draft',
    submitted_at         TIMESTAMP,
    CONSTRAINT fk_team_lineup_match
        FOREIGN KEY (match_id) REFERENCES match(match_id) ON DELETE CASCADE,
    CONSTRAINT fk_team_lineup_team
        FOREIGN KEY (team_id) REFERENCES team(team_id) ON DELETE CASCADE,
    CONSTRAINT fk_team_lineup_formation
        FOREIGN KEY (formation_id) REFERENCES formation(formation_id) ON DELETE SET NULL,
    CONSTRAINT uk_team_lineup_one_per_match
        UNIQUE (match_id, team_id)
);

CREATE TABLE match_lineup_player (
    match_lineup_player_id    BIGSERIAL PRIMARY KEY,
    team_lineup_id            BIGINT NOT NULL,
    player_profile_id         BIGINT NOT NULL,
    position_id               BIGINT,
    tactical_position_x       INT,
    tactical_position_y       INT,
    starter                   BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_lineup_player_lineup
        FOREIGN KEY (team_lineup_id) REFERENCES team_lineup(team_lineup_id) ON DELETE CASCADE,
    CONSTRAINT fk_lineup_player_profile
        FOREIGN KEY (player_profile_id) REFERENCES player_profile(player_profile_id) ON DELETE RESTRICT,
    CONSTRAINT fk_lineup_player_position
        FOREIGN KEY (position_id) REFERENCES position(position_id) ON DELETE SET NULL,
    CONSTRAINT uk_lineup_player_unique
        UNIQUE (team_lineup_id, player_profile_id),
    CONSTRAINT chk_tactical_x
        CHECK (tactical_position_x IS NULL OR tactical_position_x BETWEEN 0 AND 100),
    CONSTRAINT chk_tactical_y
        CHECK (tactical_position_y IS NULL OR tactical_position_y BETWEEN 0 AND 100)
);
