package org.andrey.mapper;

import org.andrey.database.entity.BookInBasket;
import org.andrey.database.entity.PurchaseHistory;
import org.springframework.stereotype.Component;

@Component
public class BasketToPurchaseHistoryMapper implements Mapper<BookInBasket, PurchaseHistory> {
    @Override
    public PurchaseHistory map(BookInBasket object) {
        return PurchaseHistory.builder()
                .user(object.getUser())
                .book(object.getBook())
                .amount(object.getBook().getPriceDigital())
                .build();
    }
}
