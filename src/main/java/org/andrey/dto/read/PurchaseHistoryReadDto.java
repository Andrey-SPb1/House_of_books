package org.andrey.dto.read;

import lombok.Value;

import java.time.Instant;

@Value
public class PurchaseHistoryReadDto {

    Integer amount;
    String bookImage;
    String bookName;
    String createdBy;
    Instant createdAt;
    String modifiedBy;
    Instant modifiedAt;

}
