-- Watcher tables
ALTER TABLE team_users RENAME TO team_watchers;

CREATE TABLE league_watchers (
    league_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    PRIMARY KEY (league_id, user_id),
    FOREIGN KEY (league_id) REFERENCES leagues(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE season_watchers (
    season_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    PRIMARY KEY (season_id, user_id),
    FOREIGN KEY (season_id) REFERENCES seasons(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE player_watchers (
    player_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    PRIMARY KEY (player_id, user_id),
    FOREIGN KEY (player_id) REFERENCES players(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);
