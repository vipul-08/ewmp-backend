package com.hashedin.service;

import com.hashedin.dto.*;

public interface LoginInfoService {

    LoginResponseDto login(String type,LoginDto loginDto);

    void forgotPassword(LoginInfoForgotPasswordDto loginInfoDto);

    void forgotPasswordChecker(LoginInfoForgotPasswordCheckerDto forgotPasswordChecker);

    void forgotPasswordUpdate(LoginInfoForgotPasswordChangeDto loginInfoChangePassword);

    void loginViaOtp(String type, LoginOrRegisterViaOtpDto loginOrRegisterViaOtpDto);

    LoginResponseDto loginViaOtpValidation(String type, LoginViaOtpValidationDto loginViaOtpValidationDto);

    String updatePassword(Long userId, LoginInfoChangePasswordDto loginInfoDto);
}
