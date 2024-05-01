package org.andrey.controller;

import lombok.RequiredArgsConstructor;
import org.andrey.dto.read.BookReadDto;
import org.andrey.service.BookReviewService;
import org.andrey.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final BookReviewService bookReviewService;

    @GetMapping("/{id}")
    public BookReadDto findById(@PathVariable Long id,
                                @AuthenticationPrincipal UserDetails userDetails) {
        return bookService.findById(id, userDetails.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}/favorites")
    public ResponseEntity<?> changeFavorites(@PathVariable Long id,
                                                  @AuthenticationPrincipal UserDetails userDetails) {
        return bookService.changeFavorites(id, userDetails.getUsername()) ?
                ok().build() :
                internalServerError().build();
    }

    @PutMapping("/{id}/basket")
    public ResponseEntity<?> changeBasket(@PathVariable Long id,
                                               @AuthenticationPrincipal UserDetails userDetails) {
        return bookService.changeBasket(id, userDetails.getUsername()) ?
                ok().build() :
                internalServerError().build();
    }

    @PostMapping("/{id}/review")
    @ResponseStatus(HttpStatus.CREATED)
    public void addReview(@PathVariable Long id,
                          @AuthenticationPrincipal UserDetails userDetails,
                          String review) {
        bookReviewService.addReview(id, userDetails.getUsername(), review);
    }
}
