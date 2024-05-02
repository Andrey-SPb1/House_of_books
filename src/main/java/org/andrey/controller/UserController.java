package org.andrey.controller;

import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import org.andrey.dto.create.UserCreateEditDto;
import org.andrey.dto.read.BasketReadDto;
import org.andrey.dto.read.FavoritesReadDto;
import org.andrey.dto.read.PurchaseHistoryReadDto;
import org.andrey.dto.read.UserReadDto;
import org.andrey.service.PurchaseHistoryService;
import org.andrey.service.UserService;
import org.andrey.validation.group.Marker;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final PurchaseHistoryService purchaseHistoryService;

    @GetMapping
    public UserReadDto findByEmail(@AuthenticationPrincipal UserDetails userDetails) {
        return userService.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/password")
    public ResponseEntity<?> changePassword(@AuthenticationPrincipal UserDetails userDetails,
                                            String oldPassword,
                                            String newPassword) {
        return userService.changePassword(userDetails.getUsername(), oldPassword, newPassword);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserReadDto update(@AuthenticationPrincipal UserDetails userDetails,
                              @Validated({Default.class, Marker.UpdateAction.class})
                              @RequestBody
                              UserCreateEditDto user) {
        return userService.update(userDetails.getUsername(), user)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    // TODO: 01.05.2024 fix queries
    @PutMapping("/basket/buy")
    public ResponseEntity<?> buyBooks(@AuthenticationPrincipal UserDetails userDetails) {
        return purchaseHistoryService.addPurchaseHistoryFromBasket(userDetails.getUsername());
    }

    @GetMapping("/basket")
    public List<BasketReadDto> findUsersBasket(@AuthenticationPrincipal UserDetails userDetails) {
        return userService.getBasketByEmail(userDetails.getUsername());
    }

    @GetMapping("/favorites")
    public List<FavoritesReadDto> findUsersFavorites(@AuthenticationPrincipal UserDetails userDetails) {
        return userService.getFavoritesByEmail(userDetails.getUsername());
    }

    @GetMapping("/purchase-history")
    public List<PurchaseHistoryReadDto> findUsersPurchaseHistory(@AuthenticationPrincipal UserDetails userDetails) {
        return userService.getPurchaseHistoryByEmail(userDetails.getUsername());
    }
}
