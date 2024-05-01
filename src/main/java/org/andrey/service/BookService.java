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
import org.springframework.util.StringUtils;
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
                    uploadImage(dto.image());
                    return bookCreateEditMapper.map(dto);
                })
                .map(bookRepository::save)
                .map(bookReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<BookReadDto> updateImage(Long id, MultipartFile image) {
        return bookRepository.findById(id)
                .map(book -> {
                    if(deleteImage(book.getImage())) {
                        uploadImage(image);
                        book.setImage(image.getOriginalFilename());
                        bookRepository.saveAndFlush(book);
                    }
                    return book;
                })
                .map(bookReadMapper::map);
    }

    @SneakyThrows
    private boolean deleteImage(String imageName) {
        if(imageName != null && !imageName.isEmpty()) return imageService.delete(imageName);
        else return false;
    }

    @SneakyThrows
    private void uploadImage(MultipartFile image) {
        if (image != null && !image.isEmpty()) imageService.upload(image.getOriginalFilename(), image.getInputStream());
    }

    public Optional<byte[]> getImage(Long id) {
        return bookRepository.findById(id)
                .map(Book::getImage)
                .filter(StringUtils::hasText)
                .flatMap(imageService::get);
    }

    @Transactional
    public Optional<BookReadDto> update(Long id, BookCreateEditDto bookDto) {
        return bookRepository.findById(id)
                .map(book -> bookCreateEditMapper.map(bookDto, book))
                .map(bookRepository::saveAndFlush)
                .map(bookReadMapper::map);
    }

}
