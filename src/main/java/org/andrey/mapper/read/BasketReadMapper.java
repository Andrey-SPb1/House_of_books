package org.andrey.mapper.read;

import org.andrey.database.entity.BookInBasket;
import org.andrey.dto.read.BasketReadDto;
import org.andrey.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
public class BasketReadMapper implements Mapper<BookInBasket, BasketReadDto> {

    @Override
    public BasketReadDto map(BookInBasket object) {
        return new BasketReadDto(
                object.getBook().getImage(),
                object.getBook().getName(),
                object.getBook().getAuthor()
        );
    }

}
