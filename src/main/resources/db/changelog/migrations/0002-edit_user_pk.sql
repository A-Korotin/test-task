--liquibase formatted sql

--changeset Alexey Korotin:0002

ALTER TABLE usrs
    DROP COLUMN id;
ALTER TABLE usrs
    ADD CONSTRAINT USR_PK PRIMARY KEY (username);

ALTER TABLE messages
    ALTER COLUMN user_id RENAME TO user_username;
ALTER TABLE messages
    ALTER COLUMN user_username VARCHAR(50);
