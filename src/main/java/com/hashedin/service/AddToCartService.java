package com.hashedin.service;

import com.hashedin.dto.AddOderDto;
import com.hashedin.dto.AddToCartDto;
import com.hashedin.dto.AddToCartFromProductListDto;
import com.hashedin.entity.OrdersInfo;
import org.json.simple.JSONObject;

import java.util.List;

public interface AddToCartService {
    public String addToCart(AddToCartDto addToCartDto, Long id);

    List<JSONObject> getCartItems(Long userId);

    String deleteCartItem(int cartId);



    String addToCartFromProductList(AddToCartFromProductListDto addToCartFromProductListDto, Long id);
}
