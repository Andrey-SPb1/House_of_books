package org.andrey.service;

import lombok.RequiredArgsConstructor;
import org.andrey.database.repository.BookRepository;
import org.andrey.dto.BookInMainReadDto;
import org.andrey.dto.BookReadDto;
import org.andrey.dto.filter.BookFilter;
import org.andrey.mapper.BookInMainReadMapper;
import org.andrey.mapper.BookReadMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BookReadMapper bookReadMapper;
    private final BookInMainReadMapper bookInMainReadMapper;

    public List<BookInMainReadDto> findAllByFilter(BookFilter bookFilter, Pageable pageable) {
        return bookRepository.findAllByFilter(bookFilter, pageable).stream()
                .map(bookInMainReadMapper::map)
                .toList();
    }

    public Optional<BookReadDto> findById(Long id) {
        return bookRepository.findById(id)
                .map(bookReadMapper::map);
    }

}
