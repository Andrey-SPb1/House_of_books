package org.andrey.mapper.read;

import org.andrey.database.entity.BookReview;
import org.andrey.dto.read.BookReviewReadDto;
import org.andrey.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
public class BookReviewMapper implements Mapper<BookReview, BookReviewReadDto> {
    @Override
    public BookReviewReadDto map(BookReview object) {
        return new BookReviewReadDto(
                object.getUser().getFirstname(),
                object.getUser().getLastname(),
                object.getReview()
        );
    }
}
