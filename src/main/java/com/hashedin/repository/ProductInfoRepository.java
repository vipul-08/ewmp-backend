package com.hashedin.repository;

import com.hashedin.dto.SearchFilterDto;
import com.hashedin.entity.ProductInfo;
import org.json.simple.JSONObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductInfoRepository extends JpaRepository<ProductInfo,Integer> {
    @Modifying
    @Transactional
    @Query(value = "insert into productinfo(appliance,category,covered,description,discount,mrp,notCovered,planName,priceFrom,priceTo,rating,userId,planDuration,isDeleted) values(?1,?2,?3,?4,?5,?6,?7,?8,?9,?10,?11,?12,?13,false)",nativeQuery = true)
    public void addProduct(String appliance,String category,String covered,String description,Float discount,Float mrp,String notCovered,String planName,int priceFrom,int priceTo,Float rating,Long userId,int planDuration);

    @Query(value = "select max(productId) from productinfo",nativeQuery = true)
    public Long getMaxProductId();

    @Query(value = "select productId,category,appliance from productinfo and isDeleted=false",nativeQuery = true)
    public List<JSONObject> getProducts();

    @Query(value = "select productId,appliance,category,covered,description,discount,mrp,notCovered,planName,priceFrom,priceTo,rating,userId,planDuration from productinfo where userId=?1 and isDeleted=false order by productId desc",nativeQuery = true)
    public List<JSONObject> getListSellerById(Long sellerId);

    @Query(value = "select p.productId,p.appliance,p.category,p.covered,p.description,p.discount,p.mrp,p.notCovered,p.planName,p.priceFrom,p.priceTo,p.rating,p.userId,p.planDuration,u.bname as sellerName from productinfo p inner join userinfo u on p.userId=u.userId where p.pricefrom>=?1 and p.priceTo<=?2 and p.category=?3 and p.appliance=?4 and p.planDuration=?5 and p.isDeleted=false order by productId desc",nativeQuery = true)
    public List<JSONObject> filterByParam(int priceFrom,int priceTo, String category, String appliance, int planDuration);

    @Query(value = "select p.productId,p.appliance,p.category,p.covered,p.description,p.discount,p.mrp,p.notCovered,p.planName,p.priceFrom,p.priceTo,p.rating,p.userId,p.planDuration,u.bname as sellerName from productinfo p inner join userinfo u on p.userId=u.userId where p.pricefrom>=?1 and p.priceTo<=?2 and p.category=?3 and p.appliance=?4 and p.isDeleted=false order by productId desc",nativeQuery = true)
    public List<JSONObject> filterByPriceRange(int priceFrom,int priceTo,String category, String appliance);

    @Query(value = "select p.productId,p.appliance,p.category,p.covered,p.description,p.discount,p.mrp,p.notCovered,p.planName,p.priceFrom,p.priceTo,p.rating,p.userId,p.planDuration,u.bname as sellerName from productinfo p inner join userinfo u on p.userId=u.userId where p.category=?1 and p.appliance=?2 and p.isDeleted=false order by productId desc",nativeQuery = true)
    public List<JSONObject> filterByAppliance(String category, String appliance);

    @Query(value = "select p.productId,p.appliance,p.category,p.covered,p.description,p.discount,p.mrp,p.notCovered,p.planName,p.priceFrom,p.priceTo,p.rating,p.userId,p.planDuration,u.bname as sellerName from productinfo p inner join userinfo u on p.userId=u.userId where p.isdeleted=false and p.category=?1 order by productId desc",nativeQuery = true)
    public List<JSONObject> filterByCategory(String category);

    @Query(value = "select p.productId,p.appliance,p.category,p.covered,p.description,p.discount,p.mrp,p.notCovered,p.planName,p.priceFrom,p.priceTo,p.rating,p.userId,p.planDuration,u.bname as sellerName from productinfo p inner join userinfo u on p.userId=u.userId where p.isdeleted=false and p.category=?1 and p.planduration=?2 order by productId desc",nativeQuery = true)
    public List<JSONObject> filterByCategoryAndPlanDuration(String category,int planDuration);

    @Query(value = "select p.productId,p.appliance,p.category,p.covered,p.description,p.discount,p.mrp,p.notCovered,p.planName,p.priceFrom,p.priceTo,p.rating,p.userId,p.planDuration,u.bname as sellerName from productinfo p inner join userinfo u on p.userId=u.userId where p.isdeleted=false and p.category=?1 and p.planduration=?2 and p.appliance=?3 order by productId desc",nativeQuery = true)
    public List<JSONObject> filterByApplianceAndPlanDuration(String category,int planDuration,String appliance);

    @Modifying
    @Transactional
    @Query(value = "update productinfo set isDeleted=true where userId=?1 and productId=?2",nativeQuery = true)
    public void  deleteProduct(Long id, int productId);

    @Query(value = "select * from productinfo where productId=?1 and isDeleted=false",nativeQuery = true)
    public Optional<ProductInfo> checkIfProductExists(int productId);

    @Query(value = "select p.productId, p.appliance,p.category,p.covered,p.description,p.discount,p.mrp,p.notCovered,p.planName,p.priceFrom,p.priceTo,p.rating,p.userId,p.planDuration,u.bname as sellerName from productinfo p inner join userinfo u on p.userId = u.userId where productId=?1 and p.isDeleted=false",nativeQuery = true)
    public JSONObject getProductByPID(int productId);

    @Query(value = "select p.productId,p.appliance,p.category,p.covered,p.description,p.discount,p.mrp,p.notCovered,p.planName,p.priceFrom,p.priceTo,p.rating,p.userId,p.planDuration,u.bname as sellerName from productinfo p inner join userinfo u on p.userId=u.userId where p.pricefrom>=?1 and p.priceTo<=?2 and p.category=?3 and p.appliance=?4 and p.planDuration=?5 and p.isDeleted=false and p.userid=?6 order by productId desc",nativeQuery = true)
    public List<JSONObject> sellerFilterByALLValues(int priceFrom,int priceTo, String category, String appliance, int planDuration,Long sellerId);

    @Query(value = "select p.productId,p.appliance,p.category,p.covered,p.description,p.discount,p.mrp,p.notCovered,p.planName,p.priceFrom,p.priceTo,p.rating,p.userId,p.planDuration,u.bname as sellerName from productinfo p inner join userinfo u on p.userId=u.userId where p.pricefrom>=?1 and p.priceTo<=?2 and p.category=?3 and p.appliance=?4 and p.isDeleted=false and p.userid=?5 order by productId desc",nativeQuery = true)
    public List<JSONObject> sellerFilterByPriceRange(int priceFrom,int priceTo,String category, String appliance,Long sellerId);

    @Query(value = "select p.productId,p.appliance,p.category,p.covered,p.description,p.discount,p.mrp,p.notCovered,p.planName,p.priceFrom,p.priceTo,p.rating,p.userId,p.planDuration,u.bname as sellerName from productinfo p inner join userinfo u on p.userId=u.userId where p.category=?1 and p.appliance=?2 and p.userid=?3 and p.isDeleted=false order by productId desc",nativeQuery = true)
    public List<JSONObject> sellerFilterByAppliance(String category, String appliance,Long sellerId);

    @Query(value = "select p.productId,p.appliance,p.category,p.covered,p.description,p.discount,p.mrp,p.notCovered,p.planName,p.priceFrom,p.priceTo,p.rating,p.userId,p.planDuration,u.bname as sellerName from productinfo p inner join userinfo u on p.userId=u.userId where p.isdeleted=false and p.category=?1 and p.userid=?2 order by productId desc",nativeQuery = true)
    public List<JSONObject> sellerFilterByCategory(String category,Long sellerId);

    @Query(value = "select p.productId,p.appliance,p.category,p.covered,p.description,p.discount,p.mrp,p.notCovered,p.planName,p.priceFrom,p.priceTo,p.rating,p.userId,p.planDuration,u.bname as sellerName from productinfo p inner join userinfo u on p.userId=u.userId where p.isdeleted=false and p.category=?1 and p.userid=?2 and p.planduration=?3 order by productId desc",nativeQuery = true)
    public List<JSONObject> sellerFilterByCategoryAndPlanDuration(String category,Long sellerId,int planDuration);

    @Query(value = "select p.productId,p.appliance,p.category,p.covered,p.description,p.discount,p.mrp,p.notCovered,p.planName,p.priceFrom,p.priceTo,p.rating,p.userId,p.planDuration,u.bname as sellerName from productinfo p inner join userinfo u on p.userId=u.userId where p.isdeleted=false and p.category=?1 and p.userid=?2 and p.planduration=?3 and p.appliance=?4 order by productId desc",nativeQuery = true)
    public List<JSONObject> sellerFilterByApplianceAndPlanDuration(String category,Long sellerId,int planDuration,String appliance);

    @Query(value = "select p.productId,p.appliance,p.category,p.covered,p.description,p.discount,p.mrp,p.notCovered,p.planName,p.priceFrom,p.priceTo,p.userId,p.planDuration,u.bname as sellerName from productinfo p inner join userinfo u on p.userId=u.userId where p.isdeleted=false and (p.category LIKE %?1%) order by productId desc",nativeQuery = true)
    public List<SearchFilterDto> searchFilter(String keyword);

    @Query(value = "select p.productId,p.appliance,p.category,p.covered,p.description,p.discount,p.mrp,p.notCovered,p.planName,p.priceFrom,p.priceTo,p.userId,p.planDuration,u.bname as sellerName from productinfo p inner join userinfo u on p.userId=u.userId where p.isdeleted=false and (p.category LIKE %?1% or p.appliance LIKE %?1%) order by productId desc",nativeQuery = true)
    public List<Tuple> searchFilter1(String keyword);

}
