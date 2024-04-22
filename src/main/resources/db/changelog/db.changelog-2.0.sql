--liquibase formatted sql

--changeset andrey:1
ALTER TABLE users
    ADD COLUMN created_at TIMESTAMP;

ALTER TABLE users
    ADD COLUMN modified_at TIMESTAMP;

ALTER TABLE users
    ADD COLUMN created_by VARCHAR(32);

ALTER TABLE users
    ADD COLUMN modified_by VARCHAR(32);

--changeset andrey:2
ALTER TABLE purchase_history
    ADD COLUMN created_at TIMESTAMP;

ALTER TABLE purchase_history
    ADD COLUMN modified_at TIMESTAMP;

ALTER TABLE purchase_history
    ADD COLUMN created_by VARCHAR(32);

ALTER TABLE purchase_history
    ADD COLUMN modified_by VARCHAR(32);

--changeset andrey:3
ALTER TABLE books_reviews
    ADD COLUMN created_at TIMESTAMP;

ALTER TABLE books_reviews
    ADD COLUMN modified_at TIMESTAMP;

ALTER TABLE books_reviews
    ADD COLUMN created_by VARCHAR(32);

ALTER TABLE books_reviews
    ADD COLUMN modified_by VARCHAR(32);

--changeset andrey:4
ALTER TABLE books_in_favorites
    ADD COLUMN created_at TIMESTAMP;

ALTER TABLE books_in_favorites
    ADD COLUMN modified_at TIMESTAMP;

ALTER TABLE books_in_favorites
    ADD COLUMN created_by VARCHAR(32);

ALTER TABLE books_in_favorites
    ADD COLUMN modified_by VARCHAR(32);

--changeset andrey:5
ALTER TABLE books_in_basket
    ADD COLUMN created_at TIMESTAMP;

ALTER TABLE books_in_basket
    ADD COLUMN modified_at TIMESTAMP;

ALTER TABLE books_in_basket
    ADD COLUMN created_by VARCHAR(32);

ALTER TABLE books_in_basket
    ADD COLUMN modified_by VARCHAR(32);