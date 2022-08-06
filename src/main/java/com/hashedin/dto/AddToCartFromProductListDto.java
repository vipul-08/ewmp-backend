package com.hashedin.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AddToCartFromProductListDto {

    private List<Integer> productList;
}
