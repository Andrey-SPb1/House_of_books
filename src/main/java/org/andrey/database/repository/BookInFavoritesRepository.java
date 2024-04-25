package org.andrey.database.repository;

import org.andrey.database.entity.BookInFavorites;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
public interface BookInFavoritesRepository extends JpaRepository<BookInFavorites, Integer> {

    @Query("select bf from BookInFavorites bf " +
            "where bf.user.id = :userId and bf.book.id = :bookId")
    Optional<BookInFavorites> findByUserIdAndBookId(Long userId, Long bookId);

    @Modifying
    @Query(value = "insert into books_in_favorites(user_id, book_id) " +
            "values (:userId, :bookId) ", nativeQuery = true)
    @Transactional
    int addByUserIdAndBookId(Long userId, Long bookId);

    @Modifying
    @Query(value = "delete from books_in_favorites " +
            "where user_id = :userId and book_id = :bookId ", nativeQuery = true)
    @Transactional
    int deleteByUserIdAndBookId(Long userId, Long bookId);

}
