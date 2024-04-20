package org.andrey.dto;

import lombok.Value;

@Value
public class BookReviewReadDto {

    String userFirstname;
    String userLastname;
    String review;

}
