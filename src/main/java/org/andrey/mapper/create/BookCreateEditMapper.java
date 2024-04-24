package org.andrey.mapper.create;

import org.andrey.database.entity.Book;
import org.andrey.dto.create.BookCreateEditDto;
import org.andrey.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
public class BookCreateEditMapper implements Mapper<BookCreateEditDto, Book> {
    @Override
    public Book map(BookCreateEditDto object) {
        return Book.builder()
                .name(object.getName())
                // TODO: 24.04.2024
//                .image(object.getImage())
                .author(object.getAuthor())
                .genre(object.getGenre())
                .description(object.getDescription())
                .yearOfPublish(object.getYearOfPublish())
                .pages(object.getPages())
                .pricePaper(object.getPricePaper())
                .priceDigital(object.getPriceDigital())
                .inStock(object.getInStock())
                .build();
    }
}
