INSERT INTO users(id, firstname, lastname, email, birth_date)
VALUES (1, 'Ivan', 'Ivanov', 'ivan@gmail.com', '2000-01-01'),
       (2, 'Maria', 'Morozova', 'maria@gmail.com', '1998-11-18'),
       (3, 'test', 'test', 'test@gmail.com', '1998-11-18');
SELECT SETVAL('users_id_seq', (SELECT max(id) FROM users));

INSERT INTO books (id, name, author, genre, year_of_publish, pages, price_paper, price_digital, in_stock)
VALUES (1, 'Name', 'Author', 'Genre', 1836, 320, 500, 300, 18),
       (2, 'Name2', 'Author2', 'Genre2', 1841, 352, 400, 250, 7),
       (3, 'Name3', 'Author3', 'Genre3', 1595, 192, 700, 500, 18);
SELECT SETVAL('books_id_seq', (SELECT max(id) FROM books));