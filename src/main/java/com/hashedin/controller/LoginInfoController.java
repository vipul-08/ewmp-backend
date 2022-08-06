package com.hashedin.controller;

import com.hashedin.config.JwtVerifier;
import com.hashedin.constants.Constants;
import com.hashedin.constants.HttpMethodConstants;
import com.hashedin.dto.*;

import com.hashedin.service.LoginInfoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.slf4j.MDC;
import java.util.UUID;


import javax.validation.Valid;

@RestController
@Log4j2
public class LoginInfoController {

    @Autowired
    LoginInfoService loginInfoService;

    @Autowired
    private JwtVerifier jwtverifier;

    @PostMapping(value = "/{type}/login", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = Constants.LOGIN_INFO,
            notes = Constants.LOGIN_INFO)
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = HttpMethodConstants.STATUS_OK),
                    @ApiResponse(code = 400, message = HttpMethodConstants.STATUS_BAD_REQUEST),
                    @ApiResponse(code = 401, message = HttpMethodConstants.STATUS_UNAUTHORIZED),
                    @ApiResponse(code = 403, message = HttpMethodConstants.STATUS_FORBIDDEN),
                    @ApiResponse(code = 500, message = HttpMethodConstants.STATUS_SERVER_ERROR)
            })
    public ResponseDto<LoginResponseDto> login(@PathVariable String type,@Valid @RequestBody LoginDto loginDto) {
        log.info(Constants.LOGIN_INFO);
        return ResponseDto.success(Constants.LOGGED_IN, loginInfoService.login(type,loginDto));
    }

    @PatchMapping(value = "/{type}/login-otp", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseDto loginViaOtp(@PathVariable String type,@Valid @RequestBody LoginOrRegisterViaOtpDto loginOrRegisterViaOtpDto) {
        log.info(Constants.LOGIN_INFO);
        loginInfoService.loginViaOtp(type, loginOrRegisterViaOtpDto);
        return ResponseDto.success(Constants.OTP_SENT);
    }

    @PostMapping(value = "/{type}/login-otp-validate", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseDto<LoginResponseDto> loginViaOtpValidation(@PathVariable String type,@Valid @RequestBody LoginViaOtpValidationDto loginViaOtpValidationDto) {
        log.info(Constants.LOGIN_INFO);
        return ResponseDto.success(Constants.LOGGED_IN, loginInfoService.loginViaOtpValidation(type, loginViaOtpValidationDto));
    }

    /**
     * Forgot Password
     *
     * @param loginInfoDto - Email Id to generate a password and send it to the respective user's mobile number.
     * @return - Return to the LoginInfo Service Interface
     */
    @PatchMapping(path = "/forgot-password", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseDto forgotPassword(@Valid @RequestBody LoginInfoForgotPasswordDto loginInfoDto) {
        MDC.put(Constants.TRACE_ID, UUID.randomUUID().toString());
        log.info(Constants.FORGOT);
        loginInfoService.forgotPassword(loginInfoDto);
        return ResponseDto.success(Constants.OTP_SENT);
    }

    /**
     * Forgot password verifier to check the OTP and redirect to the change password page
     *
     * @param forgotPasswordChecker - Email Id and OTP to check whether the OTP matches to the respective user.
     * @return - Return to the LoginInfo Service Interface
     */
    @PostMapping(path = "/forgot-password-checker", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseDto forgotPasswordChecker(@Valid @RequestBody LoginInfoForgotPasswordCheckerDto forgotPasswordChecker) {
        MDC.put(Constants.TRACE_ID, UUID.randomUUID().toString());
        log.info(Constants.FORGOT_CHECKER);
        loginInfoService.forgotPasswordChecker(forgotPasswordChecker);
        return ResponseDto.success(Constants.OTP_VALIDATED);
    }

    /**
     * Forgot Password to update the new password
     *
     * @param loginInfoChangePassword - Email Id, OTP, New Password and Confirm New Password to update the user's credentials.
     * @return - Return to the LoginInfo Service Interface
     */
    @PatchMapping(path = "/forgot-password-update", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseDto forgotPasswordUpdate(@Valid @RequestBody LoginInfoForgotPasswordChangeDto loginInfoChangePassword) {
        MDC.put(Constants.TRACE_ID, UUID.randomUUID().toString());
        log.info(Constants.FORGOT_UPDATE);
        loginInfoService.forgotPasswordUpdate(loginInfoChangePassword);
        return ResponseDto.success(Constants.PASSWORD_RESET);
    }


    /**
     * Change Password
     * @param authorization - Authorization Token to check if it is a valid user
     * @param loginInfoDto - Email Id, Password, New Password and Confirm New Password to change the password of the user.
     * @return - Return to the LoginInfo Service Interface
     */
    @PatchMapping(path= "/change-password",produces = {MediaType.APPLICATION_JSON_VALUE},consumes= {MediaType.APPLICATION_JSON_VALUE})
    public ResponseDto<String> updatePassword(@RequestHeader ("Authorization") String authorization, @Valid @RequestBody LoginInfoChangePasswordDto loginInfoDto)
    {
        String id = jwtverifier.extractDataFromToken(authorization).getSubject();
        Long userId = Long.parseLong(id);
        log.info(Constants.CHANGE_PASSWORD);
        loginInfoService.updatePassword(userId, loginInfoDto);
        return ResponseDto.success(Constants.PASSWORD_UPDATED);
    }

}
