package com.hashedin.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class LoginOrRegisterViaOtpDto {
    @NotBlank(message="UserName not received")
    private String contactNumber;

    @NotBlank(message="EmailId not received")
    @Email
    private String emailId;

}
