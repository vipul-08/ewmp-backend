package com.hashedin.repository;

import com.hashedin.entity.BuyerVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.transaction.Transactional;

@Repository
public interface BuyerVerificationRepository extends JpaRepository< BuyerVerification,Long> {

    @Transactional
    @Modifying
    @Query(value="insert into buyerverification (serialNo,invoiceImage,hardwareImage, userId, verification) values (?1, ?2,?3,?4,'PENDING') ", nativeQuery = true)
    void savingData(String serialNo, String invoiceImage , String hardwareImage ,  Long userInfo , BuyerVerification.Vstatus vstatus);


    @Modifying
    @Transactional
    @Query(value = "update buyerverification s set s.verification ='VERIFIED' where s.serialNo=?1",nativeQuery = true)
    public void updateVerified(String serialNo);

    @Modifying
    @Transactional
    @Query( value = "update buyerverification s set s.verification ='NOTVERIFIED' where s.serialNo=?1", nativeQuery = true)
    public void updateNotVerified( String serialNo);


    //since one to many changing userId to serialNo
    @Query(value =" select verification from buyerverification where serialNo=?1", nativeQuery = true)
    public String getStatusOfVerification(String  serialNo);



//    @Query(value = "select if( exists(select * from buyerverification where serialNo =?1 and (verification='NOTVERIFIED' or verification='PENDING' )  ),1,0);", nativeQuery = true)
//    public int checkIfSerialNoExistsHavingNotVerifiedStatus(String serialNo );

//
//
    @Query(value = "select if( exists(select * from buyerverification where serialNo =?1 ),1,0);", nativeQuery = true)
    public int checkIfSerialNoExists(String serialNo );



    //update the verification field to pending only

    @Modifying
    @Transactional
    @Query( value = "update buyerverification s set s.verification ='PENDING' where s.serialNo=?1", nativeQuery = true)
    public void updatePending( String serialNo);



    //in case of verified--show status.!




}
