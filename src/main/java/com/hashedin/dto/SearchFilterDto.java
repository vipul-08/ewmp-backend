package com.hashedin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigInteger;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class SearchFilterDto {
    private Integer productId;
    private String appliance;
    private String category;
    private String covered;
    private String description;
    private Float discount;
    private Float mrp;
    private String notCovered;
    private String planName;
    private Integer priceFrom;
    private Integer priceTo;
    private BigInteger userId;
    private Integer planDuration;
    private String sellerName;


}
