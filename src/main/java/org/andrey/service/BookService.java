package org.andrey.service;

import lombok.RequiredArgsConstructor;
import org.andrey.database.entity.User;
import org.andrey.database.repository.BookInBasketRepository;
import org.andrey.database.repository.BookInFavoritesRepository;
import org.andrey.database.repository.BookRepository;
import org.andrey.database.repository.UserRepository;
import org.andrey.dto.create.BookCreateEditDto;
import org.andrey.dto.read.BookInMainReadDto;
import org.andrey.dto.read.BookReadDto;
import org.andrey.dto.filter.BookFilter;
import org.andrey.mapper.create.BookCreateEditMapper;
import org.andrey.mapper.read.BookInMainReadMapper;
import org.andrey.mapper.read.BookReadMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class BookService {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final BookInFavoritesRepository bookInFavoritesRepository;
    private final BookInBasketRepository bookInBasketRepository;
    private final BookReadMapper bookReadMapper;
    private final BookInMainReadMapper bookInMainReadMapper;
    private final BookCreateEditMapper bookCreateEditMapper;

    public boolean changeFavorites(Long bookId, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow();
        Long userId = user.getId();

        int result = bookInFavoritesRepository.findByUserIdAndBookId(userId, bookId).isPresent() ?
                bookInFavoritesRepository.deleteByUserIdAndBookId(userId, bookId) :
                bookInFavoritesRepository.addByUserIdAndBookId(userId, bookId);

        return result == 1;
    }

    public boolean changeBasket(Long bookId, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow();
        Long userId = user.getId();

        int result = bookInBasketRepository.findByUserIdAndBookId(userId, bookId).isPresent() ?
                bookInBasketRepository.deleteByUserIdAndBookId(userId, bookId) :
                bookInBasketRepository.addByUserIdAndBookId(userId, bookId);

        return result == 1;
    }

    public List<BookInMainReadDto> findAllByFilter(BookFilter bookFilter, Pageable pageable) {
        return bookRepository.findAllByFilter(bookFilter, pageable).stream()
                .map(bookInMainReadMapper::map)
                .toList();
    }

    public Optional<BookReadDto> findById(Long id) {
        return bookRepository.findById(id)
                .map(bookReadMapper::map);
    }

    public BookReadDto create(BookCreateEditDto book) {
        return Optional.of(book)
                .map(bookCreateEditMapper::map)
                .map(bookRepository::save)
                .map(bookReadMapper::map)
                .orElseThrow();
    }

}
