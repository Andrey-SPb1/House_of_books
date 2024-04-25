package org.andrey.mapper.read;

import lombok.RequiredArgsConstructor;
import org.andrey.database.entity.User;
import org.andrey.dto.read.UserReadDto;
import org.andrey.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserReadMapper implements Mapper<User, UserReadDto> {

    private final FavoritesReadMapper favoritesReadMapper;
    private final BasketReadMapper basketReadMapper;

    @Override
    public UserReadDto map(User object) {
        return new UserReadDto(
                object.getFirstname(),
                object.getLastname(),
                object.getEmail(),
                object.getBirthDate(),
                object.getBooksInFavorites().stream()
                        .map(favoritesReadMapper::map)
                        .toList(),
                object.getBooksInBasket().stream()
                        .map(basketReadMapper::map)
                        .toList()
        );
    }
}
