package org.andrey.controller;

import lombok.RequiredArgsConstructor;
import org.andrey.dto.create.BookCreateEditDto;
import org.andrey.dto.read.BookReadDto;
import org.andrey.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/{id}")
    public BookReadDto findById(@PathVariable Long id) {
        return bookService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PutMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public BookReadDto create(BookCreateEditDto book) {
        return bookService.create(book);
    }
}
