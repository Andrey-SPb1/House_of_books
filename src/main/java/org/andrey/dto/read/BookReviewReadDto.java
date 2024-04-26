package org.andrey.dto.read;

import lombok.Value;

import java.time.Instant;

@Value
public class BookReviewReadDto {

    String userFirstname;
    String userLastname;
    String review;
    String createdBy;
    Instant createdAt;
    String modifiedBy;
    Instant modifiedAt;
}
