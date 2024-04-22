--liquibase formatted sql

--changeset andrey:1
CREATE TABLE IF NOT EXISTS revision
(
    id        SERIAL PRIMARY KEY,
    timestamp BIGINT NOT NULL
);

--changeset andrey:2
CREATE TABLE IF NOT EXISTS users_aud
(
    id         BIGINT,
    rev        INT REFERENCES revision (id),
    revtype    SMALLINT,
    firstname  VARCHAR(64)  NOT NULL,
    lastname   VARCHAR(64),
    email      VARCHAR(64)  NOT NULL UNIQUE,
    birth_date DATE,
    password   VARCHAR(128) NOT NULL DEFAULT '{noop}123'
)