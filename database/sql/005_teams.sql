CREATE TABLE team (
    team_id             BIGSERIAL PRIMARY KEY,
    tournament_id       BIGINT NOT NULL,
    captain_user_id     BIGINT NOT NULL,
    name                VARCHAR(120) NOT NULL,
    shield_url          TEXT,
    primary_color       VARCHAR(50),
    secondary_color     VARCHAR(50),
    active              BOOLEAN NOT NULL DEFAULT TRUE,
    created_at          TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_team_tournament
        FOREIGN KEY (tournament_id) REFERENCES tournament(tournament_id) ON DELETE CASCADE,
    CONSTRAINT fk_team_captain
        FOREIGN KEY (captain_user_id) REFERENCES app_user(user_id) ON DELETE RESTRICT,
    CONSTRAINT uk_team_name_per_tournament
        UNIQUE (tournament_id, name)
);

CREATE TABLE team_member (
    team_member_id      BIGSERIAL PRIMARY KEY,
    team_id             BIGINT NOT NULL,
    user_id             BIGINT NOT NULL,
    member_role         member_role_enum NOT NULL DEFAULT 'player',
    joined_at           TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    left_at             TIMESTAMP,
    active              BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT fk_team_member_team
        FOREIGN KEY (team_id) REFERENCES team(team_id) ON DELETE CASCADE,
    CONSTRAINT fk_team_member_user
        FOREIGN KEY (user_id) REFERENCES app_user(user_id) ON DELETE RESTRICT,
    CONSTRAINT uk_team_member_active_membership
        UNIQUE (team_id, user_id),
    CONSTRAINT chk_team_member_dates
        CHECK (left_at IS NULL OR left_at >= joined_at)
);