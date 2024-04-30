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
                .name(object.name())
                .author(object.author())
                .genre(object.genre())
                .description(object.description())
                .yearOfPublish(object.yearOfPublish())
                .pages(object.pages())
                .pricePaper(object.pricePaper())
                .priceDigital(object.priceDigital())
                .inStock(object.inStock())
                .image(Optional.ofNullable(object.image())
                        .filter(not(MultipartFile::isEmpty))
                        .map(MultipartFile::getOriginalFilename)
                        .orElse(null))
                .build();
    }
}
