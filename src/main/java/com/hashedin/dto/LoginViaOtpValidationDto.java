package com.hashedin.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class LoginViaOtpValidationDto {
    @NotBlank(message="Contact Number not received")
    private String contactNumber;

    @NotBlank(message="Please Enter the OTP")
    @Size(min=4,message="OTP cannot be less than 4 characters")
    private String otp;
}
