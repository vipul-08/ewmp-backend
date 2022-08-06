package com.hashedin.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductsFilterDto {
    private String category;
    private String appliance;
    private int priceFrom;
    private int priceTo;
    private int planDuration;
}
