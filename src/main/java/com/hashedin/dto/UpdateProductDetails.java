package com.hashedin.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UpdateProductDetails {
    private int productId;
    private String planName;
    private String category;
    private String appliance;
    private int priceFrom;
    private int priceTo;
    private float mrp;
    private float discount;
    private String description;
    private float rating;
    private String covered;
    private String notCovered;
    private int planDuration;
}
