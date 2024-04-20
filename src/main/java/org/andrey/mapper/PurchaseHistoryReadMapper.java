package org.andrey.mapper;

import org.andrey.database.entity.PurchaseHistory;
import org.andrey.dto.PurchaseHistoryReadDto;
import org.springframework.stereotype.Component;

@Component
public class PurchaseHistoryReadMapper implements Mapper<PurchaseHistory, PurchaseHistoryReadDto> {
    @Override
    public PurchaseHistoryReadDto map(PurchaseHistory object) {
        return new PurchaseHistoryReadDto(
                object.getAmount(),
                object.getBook().getImage(),
                object.getBook().getName()
        );
    }
}
