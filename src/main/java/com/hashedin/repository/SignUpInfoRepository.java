package com.hashedin.repository;

import com.hashedin.entity.LoginInfo;
import com.hashedin.entity.SignUpInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SignUpInfoRepository extends JpaRepository<SignUpInfo,Long> {

    //OTP being validated for login
    @Query(value = "select otp from signupinfo where contactNumber = ?1 order by timestamp desc limit 1",nativeQuery = true)
    public String validateOTPForRegister(String contactNumber);

    //Check If OTP expired for login
    @Query(value = "select if ((select abs(timestampdiff(second, (select timestamp from signupinfo where contactNumber = ?1 order by timestamp desc limit 1), now())) > 300) , 1, 0)", nativeQuery = true)
    public int checkIfOtpExpiredForLogin(String contactNumber);

}
