package org.andrey.controller;

import lombok.RequiredArgsConstructor;
import org.andrey.dto.filter.BookFilter;
import org.andrey.dto.BookInMainReadDto;
import org.andrey.service.BookService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/main")
@RequiredArgsConstructor
public class MainController {

    private final BookService bookService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BookInMainReadDto> findAllBooks(BookFilter bookFilter, Pageable pageable) {
        return bookService.findAllByFilter(bookFilter, pageable);
    }

}
