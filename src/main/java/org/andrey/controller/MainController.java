package org.andrey.controller;

import lombok.RequiredArgsConstructor;
import org.andrey.dto.BookReadDto;
import org.andrey.service.BookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/main")
@RequiredArgsConstructor
public class MainController {

    private final BookService bookService;

    @GetMapping
    public List<BookReadDto> findAllBooks() {
        return bookService.findAll();
    }

}
