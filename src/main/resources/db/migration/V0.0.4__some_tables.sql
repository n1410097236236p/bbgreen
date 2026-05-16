CREATE TABLE players (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    is_deleted BOOLEAN DEFAULT FALSE,
    PRIMARY KEY (id)
);

CREATE TABLE leagues (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    is_deleted BOOLEAN DEFAULT FALSE,
    PRIMARY KEY (id)
);

CREATE TABLE seasons (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    start_date DATE,
    end_date DATE,
    is_deleted BOOLEAN DEFAULT FALSE,
    PRIMARY KEY (id)
);

CREATE TABLE games (
    id BIGINT NOT NULL AUTO_INCREMENT,
    season_id BIGINT NOT NULL,
    league_id BIGINT NOT NULL,
    top_team_id BIGINT,
    bottom_team_id BIGINT,
    current_status VARCHAR(63) NOT NULL DEFAULT 'PENDING',
    game_start_time TIMESTAMPTZ,
    description TEXT,
    current_sequence_number BIGINT NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    FOREIGN KEY (season_id) REFERENCES seasons(id),
    FOREIGN KEY (league_id) REFERENCES leagues(id),
    FOREIGN KEY (top_team_id) REFERENCES teams(id),
    FOREIGN KEY (bottom_team_id) REFERENCES teams(id),
);

CREATE TABLE events (
    id BIGINT NOT NULL AUTO_INCREMENT,
    game_id BIGINT NOT NULL,
    sequence_number BIGINT NOT NULL,
    parent_id BIGINT,
    type VARCHAR(63) NOT NULL,
    detail JSONB NOT NULL,
    result JSONB,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (game_id) REFERENCES games(id),
    FOREIGN KEY (parent_event_id) REFERENCES events(id)
);
