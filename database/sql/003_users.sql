CREATE TABLE role (
    role_id          BIGSERIAL PRIMARY KEY,
    name             VARCHAR(50) NOT NULL UNIQUE,
    description      TEXT,
    created_at       TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE app_user (
    user_id                 BIGSERIAL PRIMARY KEY,
    document_number         VARCHAR(50) NOT NULL UNIQUE,
    first_name              VARCHAR(100) NOT NULL,
    last_name               VARCHAR(100) NOT NULL,
    institutional_email     VARCHAR(150) UNIQUE,
    personal_email          VARCHAR(150) UNIQUE,
    phone                   VARCHAR(30),
    birth_date              DATE,
    gender                  gender_enum,
    user_type               user_type_enum NOT NULL,
    photo_url               TEXT,
    active                  BOOLEAN NOT NULL DEFAULT TRUE,
    created_at              TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at              TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_user_email_at_least_one
        CHECK (institutional_email IS NOT NULL OR personal_email IS NOT NULL)
);

CREATE TABLE user_role (
    user_role_id       BIGSERIAL PRIMARY KEY,
    user_id            BIGINT NOT NULL,
    role_id            BIGINT NOT NULL,
    assigned_at        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_user_role_user
        FOREIGN KEY (user_id) REFERENCES app_user(user_id) ON DELETE CASCADE,
    CONSTRAINT fk_user_role_role
        FOREIGN KEY (role_id) REFERENCES role(role_id) ON DELETE RESTRICT,
    CONSTRAINT uk_user_role
        UNIQUE (user_id, role_id)
);

CREATE TABLE position (
    position_id        BIGSERIAL PRIMARY KEY,
    name               VARCHAR(50) NOT NULL UNIQUE,
    description        TEXT
);

CREATE TABLE player_profile (
    player_profile_id      BIGSERIAL PRIMARY KEY,
    user_id                BIGINT NOT NULL UNIQUE,
    shirt_number           INT,
    preferred_foot         preferred_foot_enum,
    height_m               NUMERIC(4,2),
    weight_kg              NUMERIC(5,2),
    available_for_team     BOOLEAN NOT NULL DEFAULT FALSE,
    bio                    TEXT,
    updated_at             TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_player_profile_user
        FOREIGN KEY (user_id) REFERENCES app_user(user_id) ON DELETE CASCADE,
    CONSTRAINT chk_shirt_number
        CHECK (shirt_number IS NULL OR shirt_number BETWEEN 1 AND 99),
    CONSTRAINT chk_height
        CHECK (height_m IS NULL OR height_m > 0),
    CONSTRAINT chk_weight
        CHECK (weight_kg IS NULL OR weight_kg > 0)
);

CREATE TABLE player_position (
    player_position_id     BIGSERIAL PRIMARY KEY,
    player_profile_id      BIGINT NOT NULL,
    position_id            BIGINT NOT NULL,
    CONSTRAINT fk_player_position_profile
        FOREIGN KEY (player_profile_id) REFERENCES player_profile(player_profile_id) ON DELETE CASCADE,
    CONSTRAINT fk_player_position_position
        FOREIGN KEY (position_id) REFERENCES position(position_id) ON DELETE RESTRICT,
    CONSTRAINT uk_player_position
        UNIQUE (player_profile_id, position_id)
);

CREATE TABLE field (
    field_id           BIGSERIAL PRIMARY KEY,
    name               VARCHAR(100) NOT NULL,
    location           VARCHAR(200),
    description        TEXT,
    active             BOOLEAN NOT NULL DEFAULT TRUE
);
