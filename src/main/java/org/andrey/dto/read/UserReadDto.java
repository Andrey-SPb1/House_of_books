package org.andrey.dto.read;

import lombok.Value;

import java.time.LocalDate;
import java.util.List;

@Value
public class UserReadDto {

    String firstname;
    String lastname;
    String email;
    LocalDate birthDate;
    List<FavoritesReadDto> booksInFavorites;
    List<BasketReadDto> booksInBasket;
}