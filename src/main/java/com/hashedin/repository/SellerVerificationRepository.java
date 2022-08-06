package com.hashedin.repository;

import com.hashedin.entity.SellerVerification;
import org.json.simple.JSONObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface SellerVerificationRepository extends JpaRepository<SellerVerification, Long  > {
    @Transactional
    @Modifying
    @Query(value = "insert into sellerverification (gstNo,bankAccountNo,ifscCode,imageurl, userId,verification) values ( ?1,?2,?3,?4,?5,'PENDING')",nativeQuery = true)
    void savingData( String gstNo, String bankAccountNo, String ifscCode, String imageUrl, Long userId, SellerVerification.Vstatus vstatus);

    @Transactional
    @Modifying
    @Query(value = "delete from sellerverification where userId=?1",nativeQuery = true)
    void deleteData(Long userId);

    @Modifying
    @Transactional
    @Query(value = "update sellerverification s set s.verification= 'VERIFIED' where s.userId=?1",nativeQuery = true)
    public void updateVerified(Long userId);

    @Modifying
    @Transactional
    @Query(value = "update sellerverification s set s.verification = 'NOTVERIFIED' where s.userId=?1",nativeQuery = true)
    public void updateNOTVerified(Long userId);

    @Query(value =" select verification from sellerverification where userId=?1", nativeQuery = true)
    public String getStatusOfVerification(Long userId);

    @Query(value = "select sv.gstNo,sv.bankAccountNo,sv.ifscCode,sv.imageurl, sv.userId,sv.verification,ui.bname, ui.emailId, ui.contactNumber from sellerverification sv inner join userinfo ui on ui.userId=sv.userId where ui.userId=?1", nativeQuery = true)
    public JSONObject getVerifiedSellerDetails(Long userId);

    @Query(value = "select userId, bname, emailId, contactNumber from userinfo where userId=?1", nativeQuery = true)
    public JSONObject getNonVerifiedSellerDetails(Long userId);

}
