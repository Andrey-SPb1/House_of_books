package org.andrey.integration.database.repository;

import lombok.RequiredArgsConstructor;
import org.andrey.database.entity.Book;
import org.andrey.database.repository.BookRepository;
import org.andrey.dto.filter.BookFilter;
import org.andrey.integration.service.IntegrationTestBase;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

@RequiredArgsConstructor
class FilterBookRepositoryTest extends IntegrationTestBase {

    private final BookRepository bookRepository;

    @ParameterizedTest
    @ArgumentsSource(BookFiltersArgumentsProvider.class)
    void findAllByFilter(BookFilter filter, int expectedSize) {

        List<Book> books = bookRepository.findAllByFilter(filter, Pageable.ofSize(10));

        assertThat(books).hasSize(expectedSize);
    }

    static class BookFiltersArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                    Arguments.of(new BookFilter("Name", "Author", "Genre", 1800,
                            500, 50, 600, 50, 500), 1),
                    Arguments.of(new BookFilter(null, "Author", "Genre", 1500,
                            400, 50, 800, 50, 600), 3),
                    Arguments.of(new BookFilter(null, null, null, 1800,
                            400, 50, 600, 50, 600), 2),
                    Arguments.of(new BookFilter("Name", "Author", "Genre", 2024,
                            100, 50, 500, 50, 500), 0)
            );
        }
    }

}