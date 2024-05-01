package org.andrey.dto.read;

import java.time.Instant;

public record PurchaseHistoryReadDto(
        Integer amount,
        String bookImage,
        String bookName,
        String createdBy,
        Instant createdAt,
        String modifiedBy,
        Instant modifiedAt) {

}
