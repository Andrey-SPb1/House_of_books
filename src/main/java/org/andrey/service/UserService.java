package org.andrey.service;

import lombok.RequiredArgsConstructor;
import org.andrey.database.entity.User;
import org.andrey.database.repository.UserRepository;
import org.andrey.dto.create.UserCreateEditDto;
import org.andrey.dto.read.BasketReadDto;
import org.andrey.dto.read.FavoritesReadDto;
import org.andrey.dto.read.PurchaseHistoryReadDto;
import org.andrey.dto.read.UserReadDto;
import org.andrey.mapper.create.UserCreateEditMapper;
import org.andrey.mapper.read.BasketReadMapper;
import org.andrey.mapper.read.FavoritesReadMapper;
import org.andrey.mapper.read.PurchaseHistoryReadMapper;
import org.andrey.mapper.read.UserReadMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserReadMapper userReadMapper;
    private final UserCreateEditMapper userCreateEditMapper;
    private final BasketReadMapper basketReadMapper;
    private final FavoritesReadMapper favoritesReadMapper;
    private final PurchaseHistoryReadMapper purchaseHistoryReadMapper;

    public Optional<UserReadDto> findById(Long id) {
        return userRepository.findById(id)
                .map(userReadMapper::map);
    }

    public Optional<UserReadDto> findByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userReadMapper::map);
    }

    @Transactional
    public UserReadDto create(UserCreateEditDto user) {
        return Optional.of(user)
                .map(userCreateEditMapper::map)
                .map(userRepository::save)
                .map(userReadMapper::map)
                .orElseThrow();
    }

    public List<BasketReadDto> getBasketByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(User::getBooksInBasket)
                .map(basket -> basket.stream()
                        .map(basketReadMapper::map)
                        .toList())
                .orElse(null);
    }

    public List<FavoritesReadDto> getFavoritesByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(User::getBooksInFavorites)
                .map(favorites -> favorites.stream()
                        .map(favoritesReadMapper::map)
                        .toList())
                .orElse(null);
    }

    public List<PurchaseHistoryReadDto> getPurchaseHistoryByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(User::getPurchaseHistories)
                .map(purchaseHistory -> purchaseHistory.stream()
                        .map(purchaseHistoryReadMapper::map)
                        .toList())
                .orElse(null);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .map(user -> new org.springframework.security.core.userdetails.User(
                        user.getEmail(),
                        user.getPassword(),
                        Collections.singleton(user.getRole())
                        )
                )
                .orElseThrow(() -> new UsernameNotFoundException("Failed to retrieve user: " + username));
    }
}
