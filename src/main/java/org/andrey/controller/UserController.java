package org.andrey.controller;

import lombok.RequiredArgsConstructor;
import org.andrey.dto.BasketReadDto;
import org.andrey.dto.FavoritesReadDto;
import org.andrey.dto.PurchaseHistoryReadDto;
import org.andrey.dto.UserReadDto;
import org.andrey.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public UserReadDto findById(@PathVariable Long id) {
        return userService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{id}/basket")
    public List<BasketReadDto> findUsersBasket(@PathVariable Long id) {
        userService.getBasketById(id);
        return userService.getBasketById(id);
    }

    @GetMapping("/{id}/favorites")
    public List<FavoritesReadDto> findUsersFavorites(@PathVariable Long id) {
        return userService.getFavoritesById(id);
    }

    @GetMapping("/{id}/purchase-history")
    public List<PurchaseHistoryReadDto> findUsersPurchaseHistory(@PathVariable Long id) {
        return userService.getPurchaseHistoryById(id);
    }
}
