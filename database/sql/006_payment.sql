CREATE TABLE payment (
    payment_id          BIGSERIAL PRIMARY KEY,
    team_id             BIGINT NOT NULL,
    amount              NUMERIC(12,2) NOT NULL,
    payment_method      payment_method_enum NOT NULL,
    voucher_url         TEXT,
    reference_number    VARCHAR(100) NOT NULL UNIQUE,
    approved_by         BIGINT,
    status              payment_status_enum NOT NULL DEFAULT 'pending',
    reviewed_at         TIMESTAMP,
    comments            TEXT,
    created_at          TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_payment_team
        FOREIGN KEY (team_id) REFERENCES team(team_id) ON DELETE CASCADE,
    CONSTRAINT fk_payment_approved_by
        FOREIGN KEY (approved_by) REFERENCES app_user(user_id) ON DELETE SET NULL,
    CONSTRAINT chk_payment_amount
        CHECK (amount >= 0),
    CONSTRAINT chk_payment_reviewed
        CHECK (
            (status IN ('pending', 'under_review') AND reviewed_at IS NULL)
            OR
            (status IN ('approved', 'rejected'))
        )
);
