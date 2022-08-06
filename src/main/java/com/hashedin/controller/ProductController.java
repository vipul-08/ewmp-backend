package com.hashedin.controller;

import com.hashedin.dto.*;
import com.hashedin.entity.OrdersInfo;
import com.hashedin.service.AddToCartService;
import com.hashedin.service.ProductInfoService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import lombok.extern.log4j.Log4j2;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log4j2
public class ProductController {

    @Autowired
    ProductInfoService productInfoService;

    @PostMapping(value = "/seller/addProduct")
    public ResponseEntity<String> saveProduct(@RequestBody AddProductDto productInfo,@RequestAttribute("id") Long id)
    {
        System.out.printf(String.valueOf(productInfo));
        return new ResponseEntity<>(productInfoService.addProduct(productInfo,id), HttpStatus.OK);
    }

    @GetMapping(value = "/seller/getProductsByCategory/{category}")
    public ResponseEntity<List<JSONObject>> getProductsByCategory(@PathVariable("category") String category)
    {
        return new ResponseEntity<>(productInfoService.filterByCategory(category),HttpStatus.OK);
    }

    @GetMapping(value = "/seller/getList/")
    public ResponseEntity<List<JSONObject>> getProductsBySellerID(@RequestAttribute("id") Long id)
    {
        return new ResponseEntity<>(productInfoService.getListSellerById(id),HttpStatus.OK);
    }

    @PostMapping(value = "/seller/filter")
    public ResponseEntity<List<JSONObject>> sellerFilter(@RequestBody ProductsFilterDto filterDto,@RequestAttribute("id") Long id)
    {
        return new ResponseEntity<>(productInfoService.sellerFilter(filterDto,id),HttpStatus.OK);
    }

    @PostMapping(value = "/buyer/filter")
    public ResponseEntity<List<JSONObject>> filterByValues(@RequestBody ProductsFilterDto filterDto)
    {
        return new ResponseEntity<>(productInfoService.filterByValues(filterDto),HttpStatus.OK);
    }

    @GetMapping(value = "/buyer/getProduct/{productId}")
    public ResponseEntity<JSONObject> getProductViewDetails(@PathVariable("productId") int productId)
    {
        return new ResponseEntity<>(productInfoService.getProductByPID(productId),HttpStatus.OK);
    }

    @GetMapping(value = "/seller/deleteProduct/{productId}")
    public ResponseEntity<String> deleteProduct(@RequestAttribute("id") Long id,@PathVariable("productId") int productId)
    {
        return new ResponseEntity<>(productInfoService.deleteProduct(id,productId),HttpStatus.OK);
    }

    @PostMapping(value = "/seller/updateSellerDetails")
    public ResponseEntity<String> updateProduct(@RequestBody UpdateProductDetails productInfo, @RequestAttribute("id") Long id)
    {
        return new ResponseEntity<>(productInfoService.updateSellerDetails(productInfo,id),HttpStatus.OK);
    }

    @GetMapping(value = "/buyer/searchFilter/{keyword}")
    public ResponseEntity<List<SearchFilterDto>> searchFilter(@PathVariable("keyword") String keyword)
    {
        return new ResponseEntity<>(productInfoService.searchFilter(keyword),HttpStatus.OK);
    }


}
