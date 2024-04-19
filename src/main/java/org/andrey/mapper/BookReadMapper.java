package org.andrey.mapper;

import org.andrey.database.entity.Book;
import org.andrey.dto.BookReadDto;
import org.springframework.stereotype.Component;

@Component
public class BookReadMapper implements Mapper <Book, BookReadDto> {

    @Override
    public BookReadDto map(Book fromObject) {
        return new BookReadDto(
                fromObject.getName(),
                fromObject.getAuthor(),
                fromObject.getGenre()
        );
    }
}
