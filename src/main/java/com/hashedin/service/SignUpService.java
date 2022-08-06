package com.hashedin.service;

import com.hashedin.dto.LoginOrRegisterViaOtpDto;
import com.hashedin.dto.RegisterRequestDTO;
import com.hashedin.dto.RegisterResponseDTO;
import com.hashedin.dto.RegisterViaOtpValidationDto;

public interface SignUpService {

    RegisterResponseDTO register(RegisterRequestDTO registerRequestDTO);

    void registerViaOtp(LoginOrRegisterViaOtpDto registerViaOtpDTO);

    RegisterResponseDTO registerViaOtpValidation(RegisterViaOtpValidationDto registerViaOtpValidationDto);
}
