package org.andrey.service;

import lombok.RequiredArgsConstructor;
import org.andrey.database.repository.BookRepository;
import org.andrey.dto.BookReadDto;
import org.andrey.mapper.BookReadMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BookReadMapper bookReadMapper;

    public List<BookReadDto> findAll() {
        return bookRepository.findAll().stream()
                .map(bookReadMapper::map)
                .toList();
    }

}
