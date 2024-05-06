package org.andrey.database.repository;

import org.andrey.database.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>, FilterBookRepository {

    @Query("select b from Book b " +
            "join fetch b.reviews r " +
            "join fetch r.user " +
            "where b.id = :id ")
    Optional<Book> findByIdWithReviews(Long id);

}
