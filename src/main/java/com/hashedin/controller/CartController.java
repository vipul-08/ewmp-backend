package com.hashedin.controller;

import com.hashedin.dto.AddToCartDto;
import com.hashedin.dto.AddToCartFromProductListDto;
import com.hashedin.dto.DeleteCartItemDto;
import com.hashedin.service.AddToCartService;
import lombok.extern.log4j.Log4j2;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log4j2
public class CartController {

    @Autowired
    AddToCartService addToCartService;

    @PostMapping(value = "/buyer/addToCart")
    public ResponseEntity<String> addToCart(@RequestBody AddToCartDto addToCartDto, @RequestAttribute("id") Long id)
    {
        return new ResponseEntity<>(addToCartService.addToCart(addToCartDto,id), HttpStatus.OK);
    }

    @PostMapping(value = "/buyer/addToCartFromProductList")
    public ResponseEntity<String> addToCartFromProductList(@RequestBody AddToCartFromProductListDto addToCartFromProductListDto, @RequestAttribute("id") Long id)
    {
        return new ResponseEntity<>(addToCartService.addToCartFromProductList(addToCartFromProductListDto,id),HttpStatus.OK);
    }

    @GetMapping(value = "/buyer/getCartItems")
    public ResponseEntity<List<JSONObject>> getCartItems(@RequestAttribute("id") Long id)
    {
        return new ResponseEntity<>(addToCartService.getCartItems(id),HttpStatus.OK);
    }

    @PostMapping(value = "/buyer/deleteProductFromCart")
    public ResponseEntity<String> deleteProductFromCart(@RequestBody DeleteCartItemDto addToCartDto, @RequestAttribute("id") Long id)
    {
        return new ResponseEntity<>(addToCartService.deleteCartItem(addToCartDto.getCartId()),HttpStatus.OK);
    }
}
