package org.andrey.service;

import lombok.RequiredArgsConstructor;
import org.andrey.database.entity.BookInBasket;
import org.andrey.database.repository.BookInBasketRepository;
import org.andrey.database.repository.PurchaseHistoryRepository;
import org.andrey.database.repository.UserRepository;
import org.andrey.mapper.BasketToPurchaseHistoryMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.http.ResponseEntity.*;

@Service
@RequiredArgsConstructor
public class PurchaseHistoryService {

    private final PurchaseHistoryRepository purchaseHistoryRepository;
    private final UserRepository userRepository;
    private final BookInBasketRepository bookInBasketRepository;
    private final BasketToPurchaseHistoryMapper basketToPurchaseHistoryMapper;

    @Transactional
    public ResponseEntity<?> addPurchaseHistoryFromBasket(String email) {
        List<BookInBasket> booksInBasket = userRepository.findByEmail(email)
                .orElseThrow()
                .getBooksInBasket();

        if (!booksInBasket.isEmpty()) {
            purchaseHistoryRepository.saveAll(booksInBasket.stream()
                    .map(basketToPurchaseHistoryMapper::map)
                    .toList());
            bookInBasketRepository.deleteAll(booksInBasket);
            return ok().build();
        } else {
            return notFound().build();
        }
    }

}
