package org.andrey.mapper;

import org.andrey.database.entity.Book;
import org.andrey.dto.BookInMainReadDto;
import org.springframework.stereotype.Component;

@Component
public class BookInMainReadMapper implements Mapper<Book, BookInMainReadDto> {
    @Override
    public BookInMainReadDto map(Book object) {
        return new BookInMainReadDto(
                object.getName(),
                object.getImage(),
                object.getAuthor()
        );
    }
}
