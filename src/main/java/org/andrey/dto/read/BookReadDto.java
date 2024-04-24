package org.andrey.dto.read;

import lombok.Value;

import java.util.List;

@Value
public class BookReadDto {

    String name;
    String image;
    String author;
    String genre;
    String description;
    Integer yearOfPublish;
    Integer pages;
    Integer pricePaper;
    Integer priceDigital;
    Integer inStock;
    List<BookReviewReadDto> reviews;

}
