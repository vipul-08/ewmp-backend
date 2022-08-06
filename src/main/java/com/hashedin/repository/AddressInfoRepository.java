package com.hashedin.repository;

import com.hashedin.entity.AddressInfo;
import org.json.simple.JSONObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface AddressInfoRepository extends JpaRepository<AddressInfo,Long> {


    @Query(value = "select addressLine1, addressLine2, city, pincode, state from addressinfo where userId = ?1", nativeQuery = true)
    public JSONObject getAddressDetails(Long id);

    @Modifying
    @Transactional
    @Query(value = "update addressinfo set addressLine1 = ?2, addressLine2 = ?3, city = ?4, pincode = ?5, state = ?6 where userId = ?1",nativeQuery = true)
    void updateUserAddressDetails(Long id, String addressLine1, String addressLine2, String city, int pincode, String state);
}
