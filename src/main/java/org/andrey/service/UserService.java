package org.andrey.service;

import lombok.RequiredArgsConstructor;
import org.andrey.database.entity.User;
import org.andrey.database.repository.UserRepository;
import org.andrey.dto.BasketReadDto;
import org.andrey.dto.FavoritesReadDto;
import org.andrey.dto.PurchaseHistoryReadDto;
import org.andrey.dto.UserReadDto;
import org.andrey.mapper.BasketReadMapper;
import org.andrey.mapper.FavoritesReadMapper;
import org.andrey.mapper.PurchaseHistoryReadMapper;
import org.andrey.mapper.UserReadMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserReadMapper userReadMapper;
    private final BasketReadMapper basketReadMapper;
    private final FavoritesReadMapper favoritesReadMapper;
    private final PurchaseHistoryReadMapper purchaseHistoryReadMapper;

    public Optional<UserReadDto> findById(Long id) {
        return userRepository.findById(id)
                .map(userReadMapper::map);
    }

    public List<BasketReadDto> getBasketById(Long id) {
        return userRepository.findById(id)
                .map(User::getBooksInBasket)
                .map(basket -> basket.stream()
                        .map(basketReadMapper::map)
                        .toList())
                .orElse(null);
    }

    public List<FavoritesReadDto> getFavoritesById(Long id) {
        return userRepository.findById(id)
                .map(User::getBooksInFavorites)
                .map(favorites -> favorites.stream()
                        .map(favoritesReadMapper::map)
                        .toList())
                .orElse(null);
    }

    public List<PurchaseHistoryReadDto> getPurchaseHistoryById(Long id) {
        return userRepository.findById(id)
                .map(User::getPurchaseHistories)
                .map(purchaseHistory -> purchaseHistory.stream()
                        .map(purchaseHistoryReadMapper::map)
                        .toList())
                .orElse(null);
    }

}
