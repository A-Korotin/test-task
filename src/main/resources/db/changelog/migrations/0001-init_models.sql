--liquibase formatted sql

--changeset Alexey Korotin:0001

CREATE TABLE usrs
(
    id       UUID PRIMARY KEY,
    role     VARCHAR(10) CHECK ( role in ('ADMIN', 'USER') ) NOT NULL,
    username VARCHAR(50) UNIQUE                              NOT NULL,
    password VARCHAR(255)                                    NOT NULL
);

CREATE TABLE topics
(
    id   UUID PRIMARY KEY,
    name TEXT NOT NULL
);

CREATE TABLE messages
(
    id           UUID PRIMARY KEY,
    text         TEXT                     NOT NULL,
    topic_id     UUID                     NOT NULL REFERENCES topics (id),
    user_id      UUID                     NOT NULL REFERENCES usrs (id),
    published_at TIMESTAMP WITH TIME ZONE NOT NULL
);