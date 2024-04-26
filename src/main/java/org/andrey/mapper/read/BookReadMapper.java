package org.andrey.mapper.read;

import lombok.RequiredArgsConstructor;
import org.andrey.database.entity.Book;
import org.andrey.dto.read.BookReadDto;
import org.andrey.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookReadMapper implements Mapper<Book, BookReadDto> {

    private final BookReviewMapper bookReviewMapper;

    @Override
    public BookReadDto map(Book object) {
        return new BookReadDto(
                object.getName(),
                object.getImage(),
                object.getAuthor(),
                object.getGenre(),
                object.getDescription(),
                object.getYearOfPublish(),
                object.getPages(),
                object.getPricePaper(),
                object.getPriceDigital(),
                object.getInStock(),
                Boolean.FALSE,
                Boolean.FALSE,
                object.getReviews().stream()
                        .map(bookReviewMapper::map)
                        .toList()
        );
    }

    public BookReadDto map(Book object, Boolean isInFavorites, Boolean isInBasket) {
        return new BookReadDto(
                object.getName(),
                object.getImage(),
                object.getAuthor(),
                object.getGenre(),
                object.getDescription(),
                object.getYearOfPublish(),
                object.getPages(),
                object.getPricePaper(),
                object.getPriceDigital(),
                object.getInStock(),
                isInFavorites,
                isInBasket,
                object.getReviews().stream()
                        .map(bookReviewMapper::map)
                        .toList()
        );
    }
}
