package com.hashedin.service.Impl;

import com.hashedin.constants.Constants;
import com.hashedin.dto.AddProductDto;
import com.hashedin.dto.ProductsFilterDto;
import com.hashedin.dto.SearchFilterDto;
import com.hashedin.dto.UpdateProductDetails;
import com.hashedin.entity.ProductInfo;
import com.hashedin.exceptions.RecordNotFoundException;
import com.hashedin.repository.ProductInfoRepository;
import com.hashedin.service.ProductInfoService;
import io.swagger.models.auth.In;
import lombok.extern.log4j.Log4j2;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class ProductInfoServiceImpl implements ProductInfoService {
    @Autowired
    ProductInfoRepository productInfoRepository;

    @Override
    public String addProduct(AddProductDto productInfo,Long userID) {
        Long productID = productInfoRepository.getMaxProductId();
        productInfoRepository
                .addProduct(productInfo.getAppliance(),productInfo.getCategory(),productInfo.getCovered(),productInfo.getDescription(),productInfo.getDiscount(),productInfo.getMrp(),productInfo.getNotCovered(),productInfo.getPlanName(),productInfo.getPriceFrom(),productInfo.getPriceTo(),productInfo.getRating(),userID,productInfo.getPlanDuration());
        log.info("Product Added");
        return Constants.PRODUCTADDED;
    }

    @Override
    public List<JSONObject> getProducts() {
        return productInfoRepository.getProducts();
    }

    @Override
    public List<JSONObject> filterByCategory(String category) {
        return productInfoRepository.filterByCategory(category);
    }

    @Override
    public List<JSONObject> getListSellerById(Long sellerId) {
        return productInfoRepository.getListSellerById(sellerId);
    }

    @Override
    public List<JSONObject> filterByValues(ProductsFilterDto filterDto) {

        if(filterDto.getCategory()!=null && filterDto.getAppliance()!=null && filterDto.getPriceTo()>0 && filterDto.getPlanDuration()>0)
        {
            return productInfoRepository.filterByParam(filterDto.getPriceFrom(),filterDto.getPriceTo(),filterDto.getCategory(),filterDto.getAppliance(),filterDto.getPlanDuration());
        }
        else if(filterDto.getCategory()!=null && filterDto.getAppliance()!=null && filterDto.getPriceTo()>0)
        {
            return productInfoRepository.filterByPriceRange(filterDto.getPriceFrom(),filterDto.getPriceTo(),filterDto.getCategory(),filterDto.getAppliance());
        }
        else if(filterDto.getCategory()!=null && filterDto.getAppliance()!=null && filterDto.getPlanDuration()>0)
        {
            return productInfoRepository.filterByApplianceAndPlanDuration(filterDto.getCategory(), filterDto.getPlanDuration(), filterDto.getAppliance());
        }
        else if(filterDto.getCategory()!=null && filterDto.getAppliance()!=null)
        {
            return productInfoRepository.filterByAppliance(filterDto.getCategory(),filterDto.getAppliance());
        }
        else if(filterDto.getCategory()!=null && filterDto.getPlanDuration()>0)
        {
            return productInfoRepository.filterByCategoryAndPlanDuration(filterDto.getCategory(),filterDto.getPlanDuration());
        }
        else if(filterDto.getCategory()!=null)
        {
            return productInfoRepository.filterByCategory(filterDto.getCategory());
        }
        return null;
    }

    @Override
    public String deleteProduct(Long id, int productId) {
        if(productInfoRepository.checkIfProductExists(productId).isPresent())
        {
            productInfoRepository.deleteProduct(id,productId);
        }
        else {
            throw new RecordNotFoundException("Product Not Found");
        }
        return Constants.PRODUCTDELETED;
    }
    @Override
    public String updateSellerDetails(UpdateProductDetails productInfo, Long id) {
        if(productInfoRepository.checkIfProductExists(productInfo.getProductId()).isPresent())
        {
            ProductInfo productInfo1 = productInfoRepository.checkIfProductExists(productInfo.getProductId()).orElse(null);
            productInfo1.setPlanName(productInfo.getPlanName());
            productInfo1.setCategory(productInfo.getCategory());
            productInfo1.setAppliance(productInfo.getAppliance());
            productInfo1.setPriceFrom(productInfo.getPriceFrom());
            productInfo1.setPriceTo(productInfo.getPriceTo());
            productInfo1.setMrp(productInfo.getMrp());
            productInfo1.setDiscount(productInfo.getDiscount());
            productInfo1.setDescription(productInfo.getDescription());
            productInfo1.setRating(productInfo.getRating());
            productInfo1.setCovered(productInfo.getCovered());
            productInfo1.setNotCovered(productInfo.getNotCovered());
            productInfo1.setPlanDuration(productInfo.getPlanDuration());
            productInfoRepository.save(productInfo1);
        }
        else {
            throw new RecordNotFoundException("Product Not Found");
        }
        return Constants.CHANGESUPDATED;
    }

    @Override
    public JSONObject getProductByPID(int productId) {
        return productInfoRepository.getProductByPID(productId);
    }

    @Override
    public List<JSONObject> sellerFilter(ProductsFilterDto filterDto,Long sellerId) {
        if(filterDto.getCategory()!=null && filterDto.getAppliance()!=null && filterDto.getPriceTo()>0 && filterDto.getPlanDuration()>0)
        {
            return productInfoRepository.sellerFilterByALLValues(filterDto.getPriceFrom(),filterDto.getPriceTo(),filterDto.getCategory(),filterDto.getAppliance(),filterDto.getPlanDuration(),sellerId);
        }
        else if(filterDto.getCategory()!=null && filterDto.getAppliance()!=null && filterDto.getPriceTo()>0)
        {
            return productInfoRepository.sellerFilterByPriceRange(filterDto.getPriceFrom(),filterDto.getPriceTo(),filterDto.getCategory(),filterDto.getAppliance(),sellerId);
        }
        else if(filterDto.getCategory()!=null && filterDto.getAppliance()!=null && filterDto.getPlanDuration()>0)
        {
            return productInfoRepository.sellerFilterByApplianceAndPlanDuration(filterDto.getCategory(),sellerId,filterDto.getPlanDuration(),filterDto.getAppliance());
        }
        else if(filterDto.getCategory()!=null && filterDto.getAppliance()!=null)
        {
            return productInfoRepository.sellerFilterByAppliance(filterDto.getCategory(),filterDto.getAppliance(),sellerId);
        }
        else if(filterDto.getCategory()!=null && filterDto.getPlanDuration()>0)
        {
            return productInfoRepository.sellerFilterByCategoryAndPlanDuration(filterDto.getCategory(),sellerId,filterDto.getPlanDuration());
        }
        else if(filterDto.getCategory()!=null)
        {
            return productInfoRepository.sellerFilterByCategory(filterDto.getCategory(),sellerId);
        }

        return null;
    }

    @Override
    public List<SearchFilterDto> searchFilter(String keyword) {
        String[] keywords = keyword.split(" ");
        List<SearchFilterDto> finalList = new ArrayList<>();
        for(String str:keywords)
        {
            List<Tuple> resultSet = productInfoRepository.searchFilter1(str);
            List<SearchFilterDto> searchFilterDtos = resultSet.stream()
                    .map(t -> new SearchFilterDto(t.get(0, Integer.class),t.get(1, String.class),t.get(2, String.class),
                            t.get(3, String.class),t.get(4, String.class),t.get(5, Float.class),t.get(6, Float.class),t.get(7, String.class),t.get(8, String.class),t.get(9, Integer.class),t.get(10, Integer.class),t.get(11, BigInteger.class),t.get(12, Integer.class),t.get(13, String.class))).collect(Collectors.toList());
            finalList.addAll(searchFilterDtos);
        }
        return finalList;
    }

}
