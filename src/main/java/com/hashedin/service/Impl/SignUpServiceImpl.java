package com.hashedin.service.Impl;

import com.hashedin.config.JwtVerifier;
import com.hashedin.constants.Constants;
import com.hashedin.constants.ErrorKeys;
import com.hashedin.constants.LogKeys;
import com.hashedin.dto.*;
import com.hashedin.entity.LoginInfo;
import com.hashedin.entity.SignUpInfo;
import com.hashedin.entity.UserInfo;
import com.hashedin.exceptions.BadRequest;
import com.hashedin.exceptions.DataExistsException;
import com.hashedin.repository.LoginInfoRepository;
import com.hashedin.repository.SignUpInfoRepository;
import com.hashedin.repository.UserInfoRepository;
import com.hashedin.service.SignUpService;
import com.hashedin.service.SmsService;
import com.hashedin.utils.UserType;
import com.hashedin.validations.ValidateUser;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.log4j.Log4j2;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.sql.SQLDataException;
import java.util.Date;
import java.util.Random;

@Service
@Log4j2
public class SignUpServiceImpl implements SignUpService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private LoginInfoRepository loginInfoRepository;

    @Autowired
    private SignUpInfoRepository signUpInfoRepository;

    @Autowired
    private ValidateUser validateUser;

    @Autowired
    private JwtVerifier jwtverifier;

    @Autowired
    private SmsService smsservice;

    @Autowired
    private JavaMailSender javaMailSender;

    PasswordEncoder bcryptEncoder=new BCryptPasswordEncoder();


    @Override
    public RegisterResponseDTO register(RegisterRequestDTO registerRequest) throws DataExistsException {

        String message= LogKeys.USER_REGISTERED;

        //validate if user already exists
        validateUser.validateUser(registerRequest.getEmailId(), registerRequest.getContactNumber());

      PasswordEncoder bcryptEncoder=new BCryptPasswordEncoder();
        UserInfo userInfo = new UserInfo();
        LoginInfo loginInfo = new LoginInfo();
        RegisterResponseDTO registerResponseDTO = new RegisterResponseDTO();

        if(registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) {
            String encodedNewPassword = bcryptEncoder.encode(registerRequest.getPassword());
            if(registerRequest.getType().equals(UserType.BUYER)) {
                userInfo.setFname(registerRequest.getFirstName());
                userInfo.setLname(registerRequest.getLastName());
                userInfo.setType(UserInfo.Type.BUYER);
                registerResponseDTO.setName(registerRequest.getFirstName());
            }
            if(registerRequest.getType().equals(UserType.SELLER)){
                userInfo.setBname(registerRequest.getBusinessName());
                userInfo.setType(UserInfo.Type.SELLER);
                registerResponseDTO.setName(registerRequest.getBusinessName());
            }
            userInfo.setContactNumber(registerRequest.getContactNumber());
            userInfo.setEmailId(registerRequest.getEmailId());

            try{
                userInfo = userInfoRepository.save(userInfo);
                if(userInfo == null){
                    throw new SQLDataException();
                }
                long lastId = userInfo.getUserId();
                System.out.println(lastId);
                loginInfo.setUserInfo(userInfo);
                loginInfoRepository.savingData(lastId, encodedNewPassword);
                if(loginInfo == null){
                    throw new SQLDataException();
                }
                long lastLoginId = loginInfo.getUserId();
                userInfo.setUserId(lastLoginId);
                loginInfo.setUserInfo(userInfo);
                log.info(LogKeys.USER_REGISTERED);
                String token = jwtverifier.generateToken(lastId);
                registerResponseDTO.setToken(token);
            }
            catch (SQLDataException exception) {
                log.error(ErrorKeys.USER_NOT_SAVED);
                message = ErrorKeys.USER_NOT_SAVED;
            }
        }
        else{
            log.error(ErrorKeys.PASSWORD_DONT_MATCH);
            message = ErrorKeys.PASSWORD_DONT_MATCH;
        }
        registerResponseDTO.setMessage(message);
        return registerResponseDTO;
    }

    public void registerViaOtp(LoginOrRegisterViaOtpDto registerViaOtpDTO) {

        SignUpInfo signUpInfo = new SignUpInfo();
        Date date = new Date();

        String email = registerViaOtpDTO.getEmailId();
        String contactNumber = registerViaOtpDTO.getContactNumber();
        // validate if user already exists
        validateUser.validateUser(email, contactNumber);
        String newpassword = generateOtp();
        String body = "\nYour ONE TIME PASSWORD is \n" + newpassword;

        //send sms
        smsservice.sendMessage(contactNumber, body);

        //send mail
        sendMail(email, body);

        String encryptedOTP=bcryptEncoder.encode(newpassword);
        signUpInfo.setEmail(email);
        signUpInfo.setContactNumber(contactNumber);
        signUpInfo.setOtp(encryptedOTP);
        signUpInfo.setTimestamp(date.getTime());
        signUpInfoRepository.save(signUpInfo);
        log.info("OTP sent to registered mobile number");
    }

    private void sendMail(String email, String body) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(email);
        mail.setSubject(Constants.LOGIN_MESSAGE);
        mail.setText(body);
        javaMailSender.send(mail);
    }

    private String generateOtp() {
        String numbers = "123456789";
        String otp = numbers;
        Random r = new SecureRandom();
        char[] newPassword = new char[4];
        for(int i = 0; i < 4; ++i) {
            newPassword[i] = otp.charAt(r.nextInt(otp.length()));
        }
        String newpassword = new String(newPassword);
        return newpassword;
    }

    public RegisterResponseDTO registerViaOtpValidation(RegisterViaOtpValidationDto registerViaOtpValidationDto) {

        String contactNumber = registerViaOtpValidationDto.getContactNumber();
        String otp = registerViaOtpValidationDto.getOtp();
        log.debug("Validate OTP for contact number {}", contactNumber);

        String otpCheck = signUpInfoRepository.validateOTPForRegister(contactNumber);
        if(bcryptEncoder.matches(otp,otpCheck)) {
            int otpExpired = signUpInfoRepository.checkIfOtpExpiredForLogin(contactNumber);
            if (otpExpired == 0) {
                log.info("OTP validated");
                return RegisterResponseDTO.builder().message("OTP validated").build();
            } else {
                throw new BadRequest("OTP Expired");
            }
        } else {
            throw new BadRequest("Incorrect OTP -" + otp);
        }

    }
}
