package org.andrey.controller;

import lombok.RequiredArgsConstructor;
import org.andrey.dto.create.UserCreateEditDto;
import org.andrey.dto.read.BasketReadDto;
import org.andrey.dto.read.FavoritesReadDto;
import org.andrey.dto.read.PurchaseHistoryReadDto;
import org.andrey.dto.read.UserReadDto;
import org.andrey.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public UserReadDto findByEmail(@AuthenticationPrincipal UserDetails userDetails) {
        return userService.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PutMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public UserReadDto create(UserCreateEditDto user) {
        return userService.create(user);
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
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
