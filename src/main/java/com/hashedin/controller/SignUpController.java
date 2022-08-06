package com.hashedin.controller;

import com.hashedin.constants.Constants;
import com.hashedin.constants.HttpMethodConstants;
import com.hashedin.constants.LogKeys;
import com.hashedin.dto.*;
import com.hashedin.service.SignUpService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Log4j2
public class SignUpController {

    @Autowired
    SignUpService signUpService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = Constants.REGISTERING_USER,
            notes = Constants.REGISTERING_USER)
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = HttpMethodConstants.STATUS_OK),
                    @ApiResponse(code = 400, message = HttpMethodConstants.STATUS_BAD_REQUEST),
                    @ApiResponse(code = 401, message = HttpMethodConstants.STATUS_UNAUTHORIZED),
                    @ApiResponse(code = 403, message = HttpMethodConstants.STATUS_FORBIDDEN),
                    @ApiResponse(code = 500, message = HttpMethodConstants.STATUS_SERVER_ERROR)
            })
    public ResponseDto<RegisterResponseDTO> registerBuyer(@Valid @RequestBody RegisterRequestDTO registerRequest){
        log.info(Constants.REGISTERING_USER);
        return ResponseDto.success(LogKeys.USER_REGISTERED, signUpService.register(registerRequest));
    }

    @PatchMapping({"/register-otp"})
    public ResponseDto registerViaOtp(@Valid @RequestBody LoginOrRegisterViaOtpDto registerViaOtpDTO) {
        log.info(LogKeys.GENERATING_OTP);
        signUpService.registerViaOtp(registerViaOtpDTO);
        return ResponseDto.success(LogKeys.OTP_GENERATED);
    }

    @PostMapping({"/register-otp-validate"})
    public ResponseDto<RegisterResponseDTO> registerViaOtpValidation(@Valid @RequestBody RegisterViaOtpValidationDto registerViaOtpValidationDto) {
        log.info(LogKeys.VERIFYING_OTP);
        return ResponseDto.success(LogKeys.OTP_VERIFIED, signUpService.registerViaOtpValidation(registerViaOtpValidationDto));
    }
}
