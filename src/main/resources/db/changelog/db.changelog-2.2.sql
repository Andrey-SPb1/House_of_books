--liquibase formatted sql

--changeset andrey:1
ALTER TABLE users
    ADD COLUMN role VARCHAR(16);

--changeset andrey:2
ALTER TABLE users_aud
    ADD COLUMN role VARCHAR(16);