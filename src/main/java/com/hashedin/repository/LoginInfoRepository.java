package com.hashedin.repository;

import com.hashedin.entity.LoginInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface LoginInfoRepository extends JpaRepository<LoginInfo,Long> {

    //Get user Password
    @Query(value = "select password from logininfo where loginId = ?1 and isDeleted = false",nativeQuery = true)
    public String getPassword(Long userId);

    //OTP being updated in the login_info
    @Modifying
    @Transactional
    @Query(value = "update logininfo  set otp = ?2, otpTimestamp = now() where loginId = ?1",nativeQuery = true)
    public void updateOTP(Long loginId,String encryptedOTP2);


    //OTP being validated
    @Query(value = "select e.otp from logininfo e left join userinfo ui on e.loginId = ui.userId where ui.emailId = ?1 ",nativeQuery = true)
    public String validateOTP(String emailId);

    //Check If OTP expired
    @Query(value = "select if ((select abs(timestampdiff(second, (select li.otpTimestamp from logininfo li left join userinfo ui on ui.userId = li.loginId where ui.emailId = ?1), now())) > 300) , 1, 0)", nativeQuery = true)
    public int checkIfOtpExpired(String email);

    //OTP being validated for login
    @Query(value = "select e.otp from logininfo e left join userinfo ui on e.loginId = ui.userId where ui.contactNumber = ?1 ",nativeQuery = true)
    public String validateOTPForLogin(String contactNumber);

    //Check If OTP expired for login
    @Query(value = "select if ((select abs(timestampdiff(second, (select li.otpTimestamp from logininfo li left join userinfo ui on ui.userId = li.loginId where ui.contactNumber = ?1), now())) > 300) , 1, 0)", nativeQuery = true)
    public int checkIfOtpExpiredForLogin(String contactNumber);

    //Forgot password to update the new password
    @Modifying
    @Transactional
    @Query(value = "update logininfo e set e.password = ?2 where e.loginId=?1",nativeQuery = true)
    public void updateNewPassword(Long id,String encodedNewPassword);

    @Transactional
    @Modifying
    @Query(value = "insert into logininfo (loginId,otp,otpTimestamp,isDeleted,password) values (?1,NULL,NULL,0,?2)",nativeQuery = true)
    void savingData(Long id, String pswd);

    //Update New Password
    @Modifying
    @Transactional
    @Query(value = "update logininfo set password = ?2 where loginId = ?1",nativeQuery = true)
    public void changePassword(Long id, String encodedNewPassword);
}