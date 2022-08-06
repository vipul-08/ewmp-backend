package com.hashedin.service;

import com.hashedin.dto.*;
import com.hashedin.entity.ProductInfo;
import org.json.simple.JSONObject;

import java.util.List;

public interface ProductInfoService {

    String addProduct(AddProductDto productDto,Long userId);
    List<JSONObject> getProducts();
    public List<JSONObject> filterByCategory(String category);
    public List<JSONObject> getListSellerById(Long sellerId);

    List<JSONObject> filterByValues(ProductsFilterDto filterDto);

    String  deleteProduct(Long id, int productId);

    String updateSellerDetails(UpdateProductDetails productInfo, Long id);

    JSONObject getProductByPID(int productId);

    List<JSONObject> sellerFilter(ProductsFilterDto filterDto,Long sellerId);

    List<SearchFilterDto> searchFilter(String keyword);
}
