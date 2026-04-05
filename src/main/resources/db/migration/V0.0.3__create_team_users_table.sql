CREATE TABLE team_users (
    team_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    PRIMARY KEY (team_id, user_id),
    FOREIGN KEY (team_id) REFERENCES teams(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);
