package com.hashedin.service.Impl;

import com.hashedin.constants.Constants;
import com.hashedin.dto.AddToCartDto;
import com.hashedin.dto.AddToCartFromProductListDto;
import com.hashedin.repository.AddToCartRepository;
import com.hashedin.service.AddToCartService;
import lombok.extern.log4j.Log4j2;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class AddToCartServiceImpl implements AddToCartService {

    @Autowired
    AddToCartRepository addToCartRepository;

    @Override
    public String addToCart(AddToCartDto addToCartDto,Long userId) {
        addToCartRepository.addToCart(userId,addToCartDto.getProductId());
        return Constants.ADDEDTOCART;
    }

    @Override
    public List<JSONObject> getCartItems(Long userId) {
        return addToCartRepository.getCartByUserId(userId);
    }

    @Override
    public String deleteCartItem(int cartItem) {
        addToCartRepository.deleteById(cartItem);
        return Constants.DELETEDFROMCART;
    }

    @Override
    public String addToCartFromProductList(AddToCartFromProductListDto addToCartFromProductListDto, Long id) {

        List<Integer> productList = addToCartFromProductListDto.getProductList();
        for(Integer productId:productList)
        {
            addToCartRepository.addToCart(id,productId);
        }

        return Constants.LISTADDEDTOCART;
    }
}
