package org.andrey.mapper.read;

import org.andrey.database.entity.BookInFavorites;
import org.andrey.dto.read.FavoritesReadDto;
import org.andrey.mapper.Mapper;
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
