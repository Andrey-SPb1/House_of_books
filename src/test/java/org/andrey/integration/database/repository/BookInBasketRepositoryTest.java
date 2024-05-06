package org.andrey.integration.database.repository;

import lombok.RequiredArgsConstructor;
import org.andrey.database.repository.BookInBasketRepository;
import org.andrey.integration.service.IntegrationTestBase;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
class BookInBasketRepositoryTest extends IntegrationTestBase {

    private final BookInBasketRepository bookInBasketRepository;

    @Test
    void deleteByUserIdAndBookId() {
        int deleted = bookInBasketRepository.deleteByUserIdAndBookId(2L, 1L);

        assertEquals(1, deleted);
    }

    @Test
    void deleteByUserId() {
        int deleted = bookInBasketRepository.deleteByUserId(2L);

        assertEquals(2, deleted);
    }
}