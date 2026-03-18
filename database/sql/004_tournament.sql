CREATE TABLE tournament (
    tournament_id      BIGSERIAL PRIMARY KEY,
    name               VARCHAR(150) NOT NULL,
    description        TEXT,
    start_date         DATE NOT NULL,
    end_date           DATE NOT NULL,
    max_teams          INT NOT NULL,
    cost_per_team      NUMERIC(12,2) NOT NULL DEFAULT 0,
    status             tournament_status_enum NOT NULL DEFAULT 'draft',
    format             VARCHAR(50),
    created_by         BIGINT NOT NULL,
    created_at         TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at         TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_tournament_created_by
        FOREIGN KEY (created_by) REFERENCES app_user(user_id) ON DELETE RESTRICT,
    CONSTRAINT chk_tournament_dates
        CHECK (end_date >= start_date),
    CONSTRAINT chk_tournament_max_teams
        CHECK (max_teams > 1),
    CONSTRAINT chk_tournament_cost
        CHECK (cost_per_team >= 0)
);

CREATE TABLE important_date (
    important_date_id  BIGSERIAL PRIMARY KEY,
    tournament_id      BIGINT NOT NULL,
    title              VARCHAR(150) NOT NULL,
    description        TEXT,
    event_date         DATE NOT NULL,
    event_type         VARCHAR(50),
    created_at         TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_important_date_tournament
        FOREIGN KEY (tournament_id) REFERENCES tournament(tournament_id) ON DELETE CASCADE
);

CREATE TABLE tournament_stage (
    tournament_stage_id    BIGSERIAL PRIMARY KEY,
    tournament_id          BIGINT NOT NULL,
    name                   VARCHAR(100) NOT NULL,
    stage_order            INT NOT NULL,
    stage_type             stage_type_enum NOT NULL,
    is_knockout            BOOLEAN NOT NULL DEFAULT FALSE,
    active                 BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT fk_stage_tournament
        FOREIGN KEY (tournament_id) REFERENCES tournament(tournament_id) ON DELETE CASCADE,
    CONSTRAINT uk_stage_order
        UNIQUE (tournament_id, stage_order),
    CONSTRAINT uk_stage_name
        UNIQUE (tournament_id, name),
    CONSTRAINT chk_stage_order
        CHECK (stage_order > 0)
);
