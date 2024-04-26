package org.andrey.controller;

import lombok.RequiredArgsConstructor;
import org.andrey.dto.create.BookCreateEditDto;
import org.andrey.dto.read.BookReadDto;
import org.andrey.service.BookReviewService;
import org.andrey.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final BookReviewService bookReviewService;

    @GetMapping("/{id}")
    public BookReadDto findById(@PathVariable Long id) {
        // TODO: 24.04.2024 check favorites and basket
        return bookService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}/favorites")
    public ResponseEntity<String> changeFavorites(@PathVariable Long id,
                                                  @AuthenticationPrincipal UserDetails userDetails) {
        return bookService.changeFavorites(id, userDetails.getUsername()) ?
                new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("/{id}/basket")
    public ResponseEntity<String> changeBasket(@PathVariable Long id,
                                               @AuthenticationPrincipal UserDetails userDetails) {
        return bookService.changeBasket(id, userDetails.getUsername()) ?
                new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/{id}/review")
    @ResponseStatus(HttpStatus.CREATED)
    public void addReview(@PathVariable Long id,
                          @AuthenticationPrincipal UserDetails userDetails,
                          String review) {
        bookReviewService.addReview(id, userDetails.getUsername(), review);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public BookReadDto create(BookCreateEditDto book) {
        return bookService.create(book);
    }
}
