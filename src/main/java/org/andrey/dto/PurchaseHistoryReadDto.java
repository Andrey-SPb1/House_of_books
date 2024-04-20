package org.andrey.dto;

import lombok.Value;

@Value
public class PurchaseHistoryReadDto {

    Integer amount;
    String bookImage;
    String bookName;

}
