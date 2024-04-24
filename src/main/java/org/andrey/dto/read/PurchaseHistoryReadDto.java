package org.andrey.dto.read;

import lombok.Value;

@Value
public class PurchaseHistoryReadDto {

    Integer amount;
    String bookImage;
    String bookName;

}
