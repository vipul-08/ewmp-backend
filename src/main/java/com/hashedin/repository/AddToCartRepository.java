package com.hashedin.repository;

import com.hashedin.entity.CartItems;
import org.json.simple.JSONObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface AddToCartRepository extends JpaRepository<CartItems,Integer> {
    @Modifying
    @Transactional
    @Query(value = "insert into cartitems(userid,productid,verification) values(?1,?2,'VERIFICATION_NEEDED')",nativeQuery = true)
    public void addToCart(Long userId,int productId);

    @Query(value = "select * from cartitems where userid=?1 and productid=?2",nativeQuery = true)
    public Optional<CartItems> getCartByProductIdAndUserId(Long userId, int productId);

    @Query(value = "select c.cartId,c.productId,c.verification,c.userId,p.description,p.planName,p.mrp,p.discount from cartitems c inner join productinfo p on c.productId=p.productId where c.userId=?1",nativeQuery = true)
    public List<JSONObject> getCartByUserId(Long userId);

    @Modifying
    @Transactional
    @Query(value = "update cartitems set verification=?2 where cartId=?1 ",nativeQuery = true)
    public void updateStatusToVerified(int cartId,String status);

}
