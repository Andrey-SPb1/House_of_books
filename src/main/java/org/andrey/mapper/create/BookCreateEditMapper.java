package org.andrey.mapper.create;

import org.andrey.database.entity.Book;
import org.andrey.dto.create.BookCreateEditDto;
import org.andrey.mapper.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static java.util.function.Predicate.*;

@Component
public class BookCreateEditMapper implements Mapper<BookCreateEditDto, Book> {
    @Override
    public Book map(BookCreateEditDto object) {
        return Book.builder()
                .name(object.getName())
                .author(object.getAuthor())
                .genre(object.getGenre())
                .description(object.getDescription())
                .yearOfPublish(object.getYearOfPublish())
                .pages(object.getPages())
                .pricePaper(object.getPricePaper())
                .priceDigital(object.getPriceDigital())
                .inStock(object.getInStock())
                .image(Optional.ofNullable(object.getImage())
                        .filter(not(MultipartFile::isEmpty))
                        .map(MultipartFile::getOriginalFilename)
                        .orElse(null))
                .build();
    }
}
