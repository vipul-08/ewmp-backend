package com.hashedin.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class LoginInfoForgotPasswordChangeDto {

    @NotBlank(message="Email-Id cannot be blank")
    @Email(message="E-mail Id not in valid format")
    private String emailId;

    @NotBlank(message="OTP cannot be blank")
    @Size(min=4,message="OTP cannot be less than 4 characters")
    private String otp;

    @NotBlank(message="New Password not received")
    private String newPassword;

    @NotBlank(message="Confirm New Password not received")
    private String confirmNewPassword;
}
