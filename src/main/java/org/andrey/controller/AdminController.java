package org.andrey.controller;

import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import org.andrey.dto.create.BookCreateEditDto;
import org.andrey.dto.create.UserCreateEditDto;
import org.andrey.dto.read.BookReadDto;
import org.andrey.dto.read.UserReadDto;
import org.andrey.service.BookService;
import org.andrey.service.UserService;
import org.andrey.validation.group.Marker;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {

    private final UserService userService;
    private final BookService bookService;

    @GetMapping("/users/{id}")
    public UserReadDto findUser(@PathVariable Long id) {
        return userService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public UserReadDto createUser(@Validated @RequestBody UserCreateEditDto user) {
        return userService.create(user);
    }

    @PutMapping(value = "/users/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserReadDto updateUser(@PathVariable Long id,
                                  @Validated({Default.class, Marker.UpdateAction.class})
                                  @RequestBody
                                  UserCreateEditDto user) {
        return userService.update(id, user)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(value = "/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        return userService.delete(id) ? noContent().build() : notFound().build();
    }

    @PostMapping(value = "/books", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public BookReadDto createBook(@Validated BookCreateEditDto book) {
        return bookService.create(book);
    }

    @PutMapping(value = "/books/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public BookReadDto updateBook(@PathVariable Long id,
                                  @Validated
                                  @RequestBody
                                  BookCreateEditDto book) {
        return bookService.update(id, book)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping(value = "/books/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public BookReadDto updateBookImage(@PathVariable Long id,
                                       @RequestPart(name = "image") MultipartFile image) {
        return bookService.updateImage(id, image)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/books/{id}/image")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        return bookService.getImage(id)
                .map(content -> ok()
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
                        .contentLength(content.length)
                        .body(content))
                .orElseGet(noContent()::build);
    }

}
