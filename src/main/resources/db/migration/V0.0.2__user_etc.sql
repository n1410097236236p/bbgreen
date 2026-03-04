CREATE TABLE users
(
    id         BIGSERIAL PRIMARY KEY,
    username   VARCHAR(50)  NOT NULL UNIQUE,
    password   VARCHAR(255) NOT NULL,
    email      VARCHAR(100) UNIQUE,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- 初期ユーザー (パスワード: password)
INSERT INTO users (username, password)
VALUES ('admin', '$2a$10$fm/M8ZDsmfLjr1wWFZXJCeMJscXjRamIDnVnyqH0GAxI3fqXhfBDa');

ALTER TABLE teams
    ADD COLUMN is_deleted BOOLEAN DEFAULT FALSE;