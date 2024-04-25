INSERT INTO users(firstname, lastname, email, birth_date, role)
VALUES ('Ivan', 'Ivanov', 'ivan@gmail.com', '2000-01-01', 'USER'),
       ('Maria', 'Morozova', 'maria@gmail.com', '1998-11-18', 'ADMIN');

INSERT INTO books (name, author, genre, year_of_publish, pages, price_paper, price_digital, in_stock)
VALUES ('Капитанская дочка', 'А.С.Пушкин', 'Роман', 1836, 320, 500, 300, 18),
       ('Мертвые души', 'Н.В.Гоголь', 'Роман', 1841, 352, 400, 250, 7),
       ('Ромео и Джульетта', 'У.Шекспир', 'Трагедия', 1595, 192, 700, 500, 18),
       ('Хоббит', 'Джон Рональд Руэл Толкин', 'Повесть', 1937, 320, 500, 300, 18),
       ('Гарри Поттер и философский камень', 'Джоан Роулинг', 'Роман', 1997, 352, 400, 250, 7),
       ('Властелин колец', 'Джон Рональд Руэл Толкин', 'Роман-эпопея', 1954, 352, 400, 250, 7),
       ('И никого не стало', 'Агата Кристи', 'Детектив', 1939, 352, 400, 250, 7),
       ('Дон Кихот', 'Мигель де Сервантес', 'Роман', 1615, 352, 400, 250, 7),
       ('Маленький принц', 'Антуан де Сент-Экзюпери', 'Повесть-сказка', 1943, 192, 700, 500, 18);

INSERT INTO books_in_basket(user_id, book_id)
VALUES (1, 1),
       (2, 3);

INSERT INTO books_in_favorites(user_id, book_id)
VALUES (1, 1),
       (2, 1),
       (2, 3);

INSERT INTO books_reviews(book_id, user_id, review)
VALUES (1, 1, 'Крутая книга!'),
       (2, 1, 'Рекомендую!'),
       (2, 2, 'Cool book'),
       (3, 2, 'I liked it');

INSERT INTO purchase_history(amount, user_id, book_id)
VALUES (250, 1, 2),
       (300, 1, 1),
       (500, 2, 3);