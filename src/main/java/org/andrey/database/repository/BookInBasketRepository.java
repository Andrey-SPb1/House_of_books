package org.andrey.database.repository;

import org.andrey.database.entity.BookInBasket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface BookInBasketRepository extends JpaRepository<BookInBasket, Integer> {

    @Query("select bb from BookInBasket bb " +
            "where bb.user.id = :userId and bb.book.id = :bookId")
    Optional<BookInBasket> findByUserIdAndBookId(Long userId, Long bookId);

    @Modifying
    @Query(value = "insert into books_in_basket(user_id, book_id) " +
            "values (:userId, :bookId) ", nativeQuery = true)
    @Transactional
    int addByUserIdAndBookId(Long userId, Long bookId);

    @Modifying
    @Query(value = "delete from books_in_basket " +
            "where user_id = :userId and book_id = :bookId ", nativeQuery = true)
    @Transactional
    int deleteByUserIdAndBookId(Long userId, Long bookId);
}