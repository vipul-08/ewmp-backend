package com.hashedin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class AddOderDto {

    private String orderIdString;
    private String transactionIdString;
    private int productId;
    private Long userId;
    private int cartId;
}
