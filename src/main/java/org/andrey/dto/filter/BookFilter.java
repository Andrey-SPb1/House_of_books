package org.andrey.dto.filter;

public record BookFilter (
        String name,
        String author,
        String genre,
        Integer yearOfPublishGe,
        Integer pagesLe,
        Integer pricePaperFrom,
        Integer pricePaperTo,
        Integer priceDigitalFrom,
        Integer priceDigitalTo
){
}
