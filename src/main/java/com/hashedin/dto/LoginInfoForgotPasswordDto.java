package com.hashedin.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class LoginInfoForgotPasswordDto {
    @NotBlank(message="Email Id is necessary")
    @Email
    private String emailId;

}
