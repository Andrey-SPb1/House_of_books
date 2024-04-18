package org.andrey.integration.database.repository;

import lombok.RequiredArgsConstructor;
import org.andrey.database.entity.Book;
import org.andrey.database.repository.BookRepository;
import org.andrey.integration.service.IntegrationTestBase;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
class BookRepositoryTest extends IntegrationTestBase{

    private final BookRepository bookRepository;

    @Test
    void checkTest() {
        Book expectedBook = Book.builder()
                .id(1L)
//                .name("Капитанская дочка")
//                .author("А.С.Пушкин")
//                .genre("Роман")
                .name("name1")
                .author("author1")
                .genre("genre")
                .yearOfPublish(1836)
                .pages(320)
                .pricePaper(500)
                .priceDigital(300)
                .inStock(18)
                .build();

        Optional<Book> optionalBook = bookRepository.findById(1L);
        assertTrue(optionalBook.isPresent());
        System.out.println(optionalBook.get().getAuthor());
        optionalBook.ifPresent(book -> assertEquals(expectedBook, book));
    }
}