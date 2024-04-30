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
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public UserReadDto create(@Validated @RequestBody UserCreateEditDto user) {
        return userService.create(user);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserReadDto update(@PathVariable Long id,
                              @Validated({Default.class, Marker.UpdateAction.class})
                              @RequestBody
                              UserCreateEditDto user) {
        return userService.update(id, user)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/basket/buy")
    public ResponseEntity<String> buyBooks(@AuthenticationPrincipal UserDetails userDetails) {
        return purchaseHistoryService.addPurchaseHistoryFromBasket(userDetails.getUsername());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public UserReadDto findById(@PathVariable Long id) {
        return userService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
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
