CREATE TABLE match_goal (
    match_goal_id        BIGSERIAL PRIMARY KEY,
    match_id             BIGINT NOT NULL,
    team_id              BIGINT NOT NULL,
    player_profile_id    BIGINT NOT NULL,
    minute               INT NOT NULL,
    own_goal             BOOLEAN NOT NULL DEFAULT FALSE,
    penalty              BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_match_goal_match
        FOREIGN KEY (match_id) REFERENCES match(match_id) ON DELETE CASCADE,
    CONSTRAINT fk_match_goal_team
        FOREIGN KEY (team_id) REFERENCES team(team_id) ON DELETE RESTRICT,
    CONSTRAINT fk_match_goal_player
        FOREIGN KEY (player_profile_id) REFERENCES player_profile(player_profile_id) ON DELETE RESTRICT,
    CONSTRAINT chk_match_goal_minute
        CHECK (minute >= 0 AND minute <= 130)
);

CREATE TABLE match_card (
    match_card_id        BIGSERIAL PRIMARY KEY,
    match_id             BIGINT NOT NULL,
    team_id              BIGINT NOT NULL,
    player_profile_id    BIGINT NOT NULL,
    card_type            card_type_enum NOT NULL,
    minute               INT NOT NULL,
    reason               TEXT,
    CONSTRAINT fk_match_card_match
        FOREIGN KEY (match_id) REFERENCES match(match_id) ON DELETE CASCADE,
    CONSTRAINT fk_match_card_team
        FOREIGN KEY (team_id) REFERENCES team(team_id) ON DELETE RESTRICT,
    CONSTRAINT fk_match_card_player
        FOREIGN KEY (player_profile_id) REFERENCES player_profile(player_profile_id) ON DELETE RESTRICT,
    CONSTRAINT chk_match_card_minute
        CHECK (minute >= 0 AND minute <= 130)
);

CREATE TABLE sanction (
    sanction_id          BIGSERIAL PRIMARY KEY,
    tournament_id        BIGINT NOT NULL,
    match_id             BIGINT,
    team_id              BIGINT,
    player_profile_id    BIGINT,
    sanction_type        sanction_type_enum NOT NULL,
    description          TEXT NOT NULL,
    created_at           TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    active               BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT fk_sanction_tournament
        FOREIGN KEY (tournament_id) REFERENCES tournament(tournament_id) ON DELETE CASCADE,
    CONSTRAINT fk_sanction_match
        FOREIGN KEY (match_id) REFERENCES match(match_id) ON DELETE SET NULL,
    CONSTRAINT fk_sanction_team
        FOREIGN KEY (team_id) REFERENCES team(team_id) ON DELETE SET NULL,
    CONSTRAINT fk_sanction_player
        FOREIGN KEY (player_profile_id) REFERENCES player_profile(player_profile_id) ON DELETE SET NULL,
    CONSTRAINT chk_sanction_target
        CHECK (team_id IS NOT NULL OR player_profile_id IS NOT NULL)
);
