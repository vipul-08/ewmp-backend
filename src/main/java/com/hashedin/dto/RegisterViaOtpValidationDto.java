package com.hashedin.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class RegisterViaOtpValidationDto {

    String email;
    String contactNumber;
    String otp;

}
