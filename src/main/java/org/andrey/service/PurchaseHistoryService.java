package org.andrey.service;

import lombok.RequiredArgsConstructor;
import org.andrey.database.entity.PurchaseHistory;
import org.andrey.database.entity.User;
import org.andrey.database.repository.BookInBasketRepository;
import org.andrey.database.repository.PurchaseHistoryRepository;
import org.andrey.database.repository.UserRepository;
import org.andrey.mapper.BasketToPurchaseHistoryMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseHistoryService {

    private final PurchaseHistoryRepository purchaseHistoryRepository;
    private final UserRepository userRepository;
    private final BookInBasketRepository bookInBasketRepository;
    private final BasketToPurchaseHistoryMapper basketToPurchaseHistoryMapper;

    public boolean addPurchase(String email) {
        // TODO: 26.04.2024 return ResponseEntity
        User user = userRepository.findByEmail(email)
                .orElseThrow();
        List<PurchaseHistory> purchaseHistoryList = user.getBooksInBasket().stream()
                .map(basketToPurchaseHistoryMapper::map)
                .toList();

        if(purchaseHistoryList.size() != 0) {
            if (bookInBasketRepository.deleteByUser(user) == purchaseHistoryList.size()) {
                List<PurchaseHistory> savedAll = purchaseHistoryRepository.saveAll(purchaseHistoryList);
                return purchaseHistoryList.size() == savedAll.size();
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

}
