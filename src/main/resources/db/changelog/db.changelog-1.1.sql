--liquibase formatted sql

--changeset andrey:1
CREATE TABLE IF NOT EXISTS books_reviews
(
    id      BIGSERIAL PRIMARY KEY,
    book_id BIGINT       NOT NULL REFERENCES books (id),
    user_id BIGINT       NOT NULL REFERENCES users (id),
    review  VARCHAR(255) NOT NULL
);

--changeset andrey:2
CREATE TABLE IF NOT EXISTS purchase_history
(
    id      SERIAL PRIMARY KEY,
    amount  INT    NOT NULL,
    user_id BIGINT NOT NULL REFERENCES users (id),
    book_id BIGINT NOT NULL REFERENCES books (id)
);

--changeset andrey:3
CREATE TABLE IF NOT EXISTS books_in_favorites
(
    id      SERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users (id),
    book_id BIGINT NOT NULL REFERENCES books (id),
    UNIQUE (user_id, book_id)
);

--changeset andrey:4
CREATE TABLE IF NOT EXISTS books_in_basket
(
    id      SERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users (id),
    book_id BIGINT NOT NULL REFERENCES books (id),
    UNIQUE (user_id, book_id)
);