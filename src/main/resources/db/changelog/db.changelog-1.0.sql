--liquibase formatted sql

--changeset andrey:1
CREATE TABLE IF NOT EXISTS users
(
    id         BIGSERIAL PRIMARY KEY,
    firstname  VARCHAR(64)  NOT NULL,
    lastname   VARCHAR(64),
    email      VARCHAR(64)  NOT NULL UNIQUE,
    birth_date DATE,
    password   VARCHAR(128) NOT NULL DEFAULT '{noop}123'
);

--changeset andrey:2
CREATE TABLE IF NOT EXISTS books
(
    id              BIGSERIAL PRIMARY KEY,
    name            VARCHAR(64) NOT NULL,
    image           VARCHAR(64),
    author          VARCHAR(64) NOT NULL,
    genre           VARCHAR(32) NOT NULL,
    description     VARCHAR(255),
    year_of_publish INT         NOT NULL,
    pages           INT         NOT NULL,
    price_paper     INT         NOT NULL,
    price_digital   INT         NOT NULL,
    in_stock        INT         NOT NULL,
    UNIQUE (name, author)
);
