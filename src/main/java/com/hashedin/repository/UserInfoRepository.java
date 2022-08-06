package com.hashedin.repository;

import com.hashedin.entity.UserInfo;
import org.json.simple.JSONObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo,Long> {

    //Check whether Email is registered or not
    @Query(value = "select if( exists(select * from userinfo where emailId = ?1 and type = ?2 and isDeleted = false), 1, 0)",nativeQuery = true)
    int checkIfUserExists(String email,String type);

    //Check whether Email is registered or not
    @Query(value = "select if( exists(select * from userinfo where emailId = ?1 and isDeleted = false), 1, 0)",nativeQuery = true)
    int checkIfEmailExists(String email);

    //Check whether user is registered or not from contact number
    @Query(value = "select if( exists(select * from userinfo where contactNumber = ?1 and type = ?2 and isDeleted = false), 1, 0)",nativeQuery = true)
    int checkIfUserExistsFromContactNumber(String contactNumber,String type);

    //Get Name and Role of the registered user
    @Query(value = "select bname, fname, lname from userinfo where userId = ?1",nativeQuery = true)
    JSONObject getUserDetailsByUserId(Long userId);

    //getting userId to update in login info
    @Query(value = "select userId from userinfo where contactNumber = ?1", nativeQuery = true)
    Long findByContactNumber(String contactNumber);

    //getting emailid from userId to send email to users
    @Query(value = "select emailId from userinfo where userId = ?1", nativeQuery = true)
    String getEmailByUserId(Long userId);


    @Query(value = "select userId from userinfo where emailId = ?1", nativeQuery = true)
    Long findByEmailId(String emailId);

    //Check the role for the respective email-id
    @Query(value = "select type from userinfo where userId = ?1 and isDeleted = false",nativeQuery = true)
    public String checkIfUserExists(Long userId);

    //Forgot password, if the user enters the email_id
    @Query(value = "select userId from userinfo where emailId = ?1",nativeQuery = true)
    Long idFromEmail(String email);

    //Getting userId phone number to  send messages
    @Query(value = "select e.contactNumber from userinfo e where e.userId = ?1 and e.isDeleted = false", nativeQuery=true)
    public String getPhoneNumber(Long userId);


    @Query(value = "select u.fname, u.lname, u.contactNumber, u.emailId, u.gender, u.dateOfBirth, a.addressLine1, a.addressLine2, a.city, a.pincode, a.state from userinfo u left join addressinfo a on u.userId = a.userId where u.userId = ?1", nativeQuery = true)
    public JSONObject getUserDetails(Long id);

    @Transactional
    @Modifying
    @Query(value = "update userinfo set fname = ?2, lname = ?3, gender = ?4, dateOfBirth = ?5 where userId = ?1 and type = 'BUYER'", nativeQuery = true)
    void updateUserDetails(Long id, String firstName, String lastName, String gender, Date dateOfBirth);

    @Query(value = "select u.bname, u.emailId, u.contactNumber, s.bankAccountNo, s.gstNo, s.ifscCode from userinfo u left join sellerverification s on u.userId = s.userId where u.userId = ?1 and type = 'SELLER'",nativeQuery = true)
    JSONObject getUserBankdetails(Long id);

    @Modifying
    @Transactional
    @Query(value = "update userinfo set bname = ?2 where userId = ?1 and type = 'SELLER'",nativeQuery = true)
    void updateBusinessName(Long id, String bname);
}
