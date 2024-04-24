package org.andrey.dto.create;

import lombok.Value;
import org.springframework.web.multipart.MultipartFile;

@Value
public class BookCreateEditDto {

    // TODO: 24.04.2024 validation
    String name;
    MultipartFile image;
    String author;
    String genre;
    String description;
    Integer yearOfPublish;
    Integer pages;
    Integer pricePaper;
    Integer priceDigital;
    Integer inStock;

}
