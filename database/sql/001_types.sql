CREATE TYPE gender_enum AS ENUM (
    'male',
    'female',
    'other',
    'prefer_not_to_say'
);

CREATE TYPE user_type_enum AS ENUM (
    'student',
    'graduate',
    'professor',
    'administrative_staff',
    'family_member',
    'organizer',
    'referee',
    'administrator'
);

CREATE TYPE preferred_foot_enum AS ENUM (
    'left',
    'right',
    'both'
);

CREATE TYPE tournament_status_enum AS ENUM (
    'draft',
    'active',
    'in_progress',
    'finished'
);

CREATE TYPE stage_type_enum AS ENUM (
    'group_stage',
    'round_of_16',
    'quarterfinal',
    'semifinal',
    'final'
);

CREATE TYPE payment_method_enum AS ENUM (
    'cash',
    'nequi',
    'bank_transfer',
    'other'
);

CREATE TYPE payment_status_enum AS ENUM (
    'pending',
    'under_review',
    'approved',
    'rejected'
);

CREATE TYPE match_status_enum AS ENUM (
    'scheduled',
    'in_progress',
    'finished',
    'cancelled'
);

CREATE TYPE lineup_status_enum AS ENUM (
    'draft',
    'submitted',
    'confirmed'
);

CREATE TYPE card_type_enum AS ENUM (
    'yellow',
    'red'
);

CREATE TYPE sanction_type_enum AS ENUM (
    'warning',
    'suspension',
    'fine',
    'expulsion',
    'other'
);

CREATE TYPE member_role_enum AS ENUM (
    'captain',
    'player',
    'coach',
    'assistant'
);