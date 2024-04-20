package org.andrey.mapper;

import org.andrey.database.entity.BookReview;
import org.andrey.dto.BookReviewReadDto;
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
