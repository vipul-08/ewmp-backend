package com.hashedin.service.Impl;


import com.hashedin.config.JwtVerifier;
import com.hashedin.constants.Constants;
import com.hashedin.dto.*;
import com.hashedin.exceptions.BadRequest;
import com.hashedin.exceptions.Forbidden;
import com.hashedin.exceptions.NoDataExists;
import com.hashedin.exceptions.RecordNotFoundException;
import com.hashedin.repository.LoginInfoRepository;
import com.hashedin.repository.UserInfoRepository;
import com.hashedin.service.LoginInfoService;
import com.hashedin.service.SmsService;
import org.json.simple.JSONObject;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Random;

@Service
@Log4j2
public class LoginInfoServiceImpl implements LoginInfoService {

    @Autowired
    private LoginInfoRepository loginInfoRepository;

    @Autowired
    private JwtVerifier jwtverifier;

    @Autowired
    private UserInfoRepository userinforepository;

    @Autowired
    private SmsService smsservice;

    private PasswordEncoder bcryptEncoder=new BCryptPasswordEncoder();

    @Autowired
    private JavaMailSender javaMailSender;

    //Normal Login
    @Override
    public LoginResponseDto login(String type,LoginDto loginDto) {
        String emailId = loginDto.getEmailId();
        String password = loginDto.getPassword();
        if(userinforepository.checkIfUserExists(emailId,type) == 0){
            throw new NoDataExists("EmailId does not exist");
        }
        Long userId = userinforepository.findByEmailId(emailId);
        String pwd = loginInfoRepository.getPassword(userId);
        String encode = bcryptEncoder.encode(password);
        System.out.println(encode);
        boolean passwordVerifier = bcryptEncoder.matches(password,pwd);
        if (passwordVerifier)
        {
            return getLoginResponseDto(type, userId);
        }
        else
        {
            throw new Forbidden("Unregistered Id "+ userId);
        }
    }


    @Override
    public void loginViaOtp(String type, LoginOrRegisterViaOtpDto loginOrRegisterViaOtpDto) {
        String contactNumber = loginOrRegisterViaOtpDto.getContactNumber();
        String emailId = loginOrRegisterViaOtpDto.getEmailId();
        if(userinforepository.checkIfUserExistsFromContactNumber(contactNumber,type) == 0) {
            throw new NoDataExists("User does not exist");
        }
        Long userId=userinforepository.findByContactNumber(contactNumber);
        //Generating an OTP and storing in the database for verification purpose
        String newpassword = generateOtp();
        String body = Constants.OTP_MESSAGE_1 + newpassword;
        //sending sms to the respective user
        smsservice.sendMessage(contactNumber, body);
        //sending mail
        sendMail(userId, body);
        String encryptedOTP=bcryptEncoder.encode(newpassword);
        loginInfoRepository.updateOTP(userId,encryptedOTP);
        log.info("OTP sent to registered mobile number and mail");
    }

    private void sendMail(Long userId, String body) {
        SimpleMailMessage mail = new SimpleMailMessage();
        String emailId = userinforepository.getEmailByUserId(userId);
        mail.setTo(emailId);
        mail.setSubject(Constants.LOGIN_MESSAGE);
        mail.setText(body);
        javaMailSender.send(mail);
    }


    @Override
    public LoginResponseDto loginViaOtpValidation(String type, LoginViaOtpValidationDto loginViaOtpValidationDto) {
        String contactNumber = loginViaOtpValidationDto.getContactNumber();
        String otp = loginViaOtpValidationDto.getOtp();
        log.debug("Validate OTP for contact number {}", contactNumber);
        if(userinforepository.checkIfUserExistsFromContactNumber(contactNumber,type) == 0) {
            throw new RecordNotFoundException("Invalid Contact Number: "+contactNumber);
        }
        else
        {
            String otpCheck=loginInfoRepository.validateOTPForLogin(contactNumber);
            if(bcryptEncoder.matches(otp,otpCheck))
            {
                int otpExpired = loginInfoRepository.checkIfOtpExpiredForLogin(contactNumber);
                if(otpExpired == 0)
                {
                    log.info("OTP validated");
                    Long userId = userinforepository.findByContactNumber(contactNumber);
                    return getLoginResponseDto(type, userId);
                }
                else
                    throw new BadRequest("OTP Expired");
            }
            else
            {
                throw new BadRequest("Incorrect OTP -" +""+ otp);
            }
        }
    }

    //On Login the returning of token and role
    private LoginResponseDto getLoginResponseDto(String type, Long userId) {
        String token = jwtverifier.generateToken(userId);
        JSONObject jsonObject = userinforepository.getUserDetailsByUserId(userId);
        if(type.equals("BUYER")){
            String name = (String) jsonObject.get(Constants.FIRST_NAME);
            return LoginResponseDto.builder()
                    .role(type)
                    .name(name)
                    .token(token)
                    .build(); }
        else{
            String businessName = (String) jsonObject.get(Constants.BUSINESS_NAME);
            return LoginResponseDto.builder()
                    .role(type)
                    .name(businessName)
                    .token(token)
                    .build();
        }
    }


    @Override
    public void forgotPassword(LoginInfoForgotPasswordDto loginInfoDto) {
        String email=loginInfoDto.getEmailId();
        int value = userinforepository.checkIfEmailExists(email);
        log.debug("Forgot Password for mail-Id {}",email);
        if (value == 1)
        {
            Long userId=userinforepository.idFromEmail(email);
            //Getting the phone number of the user
            String contactno=userinforepository.getPhoneNumber(userId);
            //Generating an OTP and storing in the database for verification purpose
            String newpassword = generateOtp();
            String body = Constants.OTP_MESSAGE_1 + newpassword + Constants.OTP_MESSAGE_2;
            //sending sms to the respective user
            smsservice.sendMessage(contactno, body);
            //sending mail
            sendMail(userId, body);
            String encryptedOTP=bcryptEncoder.encode(newpassword);
            loginInfoRepository.updateOTP(userId,encryptedOTP);
            log.info("OTP sent to registered mobile number and emailId");
        }
        else
        {
            throw new RecordNotFoundException("Invalid EmailId - " +""+ email);
        }
    }

    @Override
    public void forgotPasswordChecker(LoginInfoForgotPasswordCheckerDto forgotPasswordChecker) {
        String email=forgotPasswordChecker.getEmailId();
        String otp=forgotPasswordChecker.getOtp();
        log.debug("Validate OTP for email-Id {}", email);

        if(userinforepository.checkIfEmailExists(email) == 0) {
            throw new RecordNotFoundException("Invalid Email-Id: "+email);
        } else {
            String otpCheck=loginInfoRepository.validateOTP(email);
            if(bcryptEncoder.matches(otp,otpCheck)) {
                int otpExpired = loginInfoRepository.checkIfOtpExpired(email);
                if(otpExpired == 0)
                    log.info("OTP validated");
                else
                    throw new BadRequest("OTP Expired");
            } else {
                throw new BadRequest("Incorrect OTP -" +""+ otp);
            }
        }
    }

    @Override
    public void forgotPasswordUpdate(LoginInfoForgotPasswordChangeDto loginInfoChangePassword) {
        String email=loginInfoChangePassword.getEmailId();
        String newPassword=loginInfoChangePassword.getNewPassword();
        String newPasswordConfirm=loginInfoChangePassword.getConfirmNewPassword();
        String otp = loginInfoChangePassword.getOtp();
        String otpCheck=loginInfoRepository.validateOTP(email);
        log.debug("Change Password for account attached to email-Id {}", email);
        if(userinforepository.checkIfEmailExists(email) == 0) {
            throw new RecordNotFoundException("Invalid Email-Id: "+email);
        } else if(!bcryptEncoder.matches(otp,otpCheck)) {
            throw new BadRequest("Incorrect OTP -" +""+ otp);
        } else {
            Long id=userinforepository.idFromEmail(email);
            if(newPassword.equals(newPasswordConfirm)) {
                String encodedNewPassword = bcryptEncoder.encode(newPassword);
                loginInfoRepository.updateNewPassword(id, encodedNewPassword);
                log.info("Password Sucessfully Set");
            } else {
                throw new BadRequest("Passwords did not match");
            }
        }
    }


    //Generating OTP
    private String generateOtp() {
        String numbers = Constants.NUMBERS_2;
        String otp = numbers;
        Random r = new SecureRandom();
        char[] newPassword = new char[4];
        for (int i = 0; i < 4; i++) {
            newPassword[i] = otp.charAt(r.nextInt(otp.length()));
        }
        String newpassword = new String(newPassword);
        System.out.println(newPassword);
        return newpassword;
    }


    /**
     * Change Password
     * @param loginInfoDto - Email Id, Password, New Password and Confirm New Password to change the password of the user.
     * @return - Password Changed Successfully / Passwords did not match Correctly message
     */
    @Override
    public String updatePassword(Long userId, LoginInfoChangePasswordDto loginInfoDto)
    {
        String password = loginInfoDto.getPassword();
        String newPassword = loginInfoDto.getNewPassword();
        String newPasswordConfirm = loginInfoDto.getConfirmNewPassword();

        String encodedPassword = loginInfoRepository.getPassword(userId);
        boolean bool = bcryptEncoder.matches(password, encodedPassword);
        if(bool) {
            if(newPassword.equals(newPasswordConfirm)) {
                String encodedNewPassword = bcryptEncoder.encode(newPassword);
                loginInfoRepository.changePassword(userId, encodedNewPassword);
                log.info("Password changed successfully for id {}", userId);
                return "Password updated successfully";
            } else {
                throw new  BadRequest("Passwords did not match Correctly");
            }
        } else {
            throw new BadRequest("Enter Current Password Correctly");
        }
    }
}
