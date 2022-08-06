package com.hashedin.repository;

import com.hashedin.entity.OrdersInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface OrderInfoRepository extends JpaRepository<OrdersInfo,Integer> {
//    @Modifying
//    @Transactional
//    @Query(value = "insert into ordersinfo(userid,productid,transactionId,planDuration,createdAt) values(?1,?2,?3,?4,now())",nativeQuery = true)
//    public void addOrder(Long userId,int productId,String transactionId,int planDuration);

    @Query(value = "select * from ordersinfo where userId=?1",nativeQuery = true)
    public List<OrdersInfo> getOrders(Long userId);
}
