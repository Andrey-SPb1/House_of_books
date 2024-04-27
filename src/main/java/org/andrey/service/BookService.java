package org.andrey.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.andrey.database.entity.Book;
import org.andrey.database.entity.BookInBasket;
import org.andrey.database.entity.BookInFavorites;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
    private final ImageService imageService;

    @Transactional
    public boolean changeFavorites(Long bookId, String email) {
        Long userId = userRepository.getIdByEmail(email)
                .orElseThrow();
        int result = bookInFavoritesRepository.findByUserIdAndBookId(userId, bookId).isPresent() ?
                bookInFavoritesRepository.deleteByUserIdAndBookId(userId, bookId) :
                bookInFavoritesRepository.addByUserIdAndBookId(userId, bookId);

        return result == 1;
    }

    @Transactional
    public boolean changeBasket(Long bookId, String email) {
        Long userId = userRepository.getIdByEmail(email)
                .orElseThrow();
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

    public Optional<BookReadDto> findById(Long bookId, String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        Book book = bookRepository.findById(bookId).orElseThrow();
        Optional<BookInFavorites> inFavorites = bookInFavoritesRepository.findByUserIdAndBookId(user.getId(), bookId);
        Optional<BookInBasket> inBasket = bookInBasketRepository.findByUserIdAndBookId(user.getId(), bookId);

        return Optional.of(bookReadMapper.map(book, inFavorites.isPresent(), inBasket.isPresent()));
    }

    @Transactional
    public BookReadDto create(BookCreateEditDto book) {
        return Optional.of(book)
                .map(dto -> {
                    uploadImage(dto.getImage());
                    return bookCreateEditMapper.map(dto);
                })
                .map(bookRepository::save)
                .map(bookReadMapper::map)
                .orElseThrow();
    }

    @SneakyThrows
    private void uploadImage(MultipartFile image) {
        if (!image.isEmpty()) imageService.upload(image.getOriginalFilename(), image.getInputStream());
    }

}
