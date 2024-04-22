package org.andrey.database.repository;

import org.andrey.database.entity.Book;
import org.andrey.dto.filter.BookFilter;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FilterBookRepository {

    List<Book> findAllByFilter(BookFilter bookFilter, Pageable pageable);
}
