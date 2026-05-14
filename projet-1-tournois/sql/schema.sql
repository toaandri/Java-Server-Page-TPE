CREATE TABLE IF NOT EXISTS tournament (
    id SERIAL PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    sport VARCHAR(80) NOT NULL,
    type VARCHAR(20) NOT NULL CHECK (type IN ('CHAMPIONNAT', 'KO')),
    location VARCHAR(150) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    match_duration_minutes INTEGER NOT NULL CHECK (match_duration_minutes > 0),
    available_fields INTEGER NOT NULL CHECK (available_fields > 0)
);

CREATE TABLE IF NOT EXISTS team (
    id SERIAL PRIMARY KEY,
    tournament_id INTEGER NOT NULL REFERENCES tournament(id) ON DELETE CASCADE,
    name VARCHAR(120) NOT NULL,
    logo_url TEXT,
    contact VARCHAR(120)
);

CREATE TABLE IF NOT EXISTS match_schedule (
    id SERIAL PRIMARY KEY,
    tournament_id INTEGER NOT NULL REFERENCES tournament(id) ON DELETE CASCADE,
    home_team_id INTEGER NOT NULL REFERENCES team(id) ON DELETE CASCADE,
    away_team_id INTEGER NOT NULL REFERENCES team(id) ON DELETE CASCADE,
    home_score INTEGER,
    away_score INTEGER,
    stage VARCHAR(50) NOT NULL,
    scheduled_at TIMESTAMP NOT NULL,
    field_number INTEGER NOT NULL CHECK (field_number > 0),
    CHECK (home_team_id <> away_team_id),
    CHECK (home_score IS NULL OR home_score >= 0),
    CHECK (away_score IS NULL OR away_score >= 0)
);
