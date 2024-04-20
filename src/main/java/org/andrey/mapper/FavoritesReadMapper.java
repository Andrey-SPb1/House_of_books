package org.andrey.mapper;

import org.andrey.database.entity.BookInFavorites;
import org.andrey.dto.FavoritesReadDto;
import org.springframework.stereotype.Component;

@Component
public class FavoritesReadMapper implements Mapper<BookInFavorites, FavoritesReadDto> {

    @Override
    public FavoritesReadDto map(BookInFavorites object) {
        return new FavoritesReadDto(
                object.getBook().getImage(),
                object.getBook().getName(),
                object.getBook().getAuthor()
        );
    }

}
