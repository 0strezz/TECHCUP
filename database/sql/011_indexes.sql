CREATE INDEX idx_app_user_last_name
    ON app_user(last_name);

CREATE INDEX idx_app_user_user_type
    ON app_user(user_type);

CREATE INDEX idx_player_profile_available
    ON player_profile(available_for_team);

CREATE INDEX idx_team_tournament_id
    ON team(tournament_id);

CREATE INDEX idx_team_member_user_id
    ON team_member(user_id);

CREATE INDEX idx_payment_team_id
    ON payment(team_id);

CREATE INDEX idx_tournament_status
    ON tournament(status);

CREATE INDEX idx_match_tournament_id
    ON match(tournament_id);

CREATE INDEX idx_match_stage_id
    ON match(tournament_stage_id);

CREATE INDEX idx_match_date
    ON match(match_date);

CREATE INDEX idx_match_goal_match_id
    ON match_goal(match_id);

CREATE INDEX idx_match_card_match_id
    ON match_card(match_id);

CREATE INDEX idx_sanction_tournament_id
    ON sanction(tournament_id);

COMMIT;