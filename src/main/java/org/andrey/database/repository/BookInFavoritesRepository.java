package org.andrey.database.repository;

import org.andrey.database.entity.BookInFavorites;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookInFavoritesRepository extends JpaRepository<BookInFavorites, Integer> {

    @Query("select bf from BookInFavorites bf " +
            "where bf.user.id = :userId and bf.book.id = :bookId")
    Optional<BookInFavorites> findByUserIdAndBookId(Long userId, Long bookId);

    @Query("select bf from BookInFavorites bf " +
            "join fetch bf.book " +
            "where bf.user.email = :email ")
    List<BookInFavorites> findByUserEmail(String email);

    @Modifying
    @Query(value = "insert into books_in_favorites(user_id, book_id) " +
            "values (:userId, :bookId) ", nativeQuery = true)
    int addByUserIdAndBookId(Long userId, Long bookId);

    @Modifying
    @Query(value = "delete from books_in_favorites " +
            "where user_id = :userId and book_id = :bookId ", nativeQuery = true)
    int deleteByUserIdAndBookId(Long userId, Long bookId);

}
