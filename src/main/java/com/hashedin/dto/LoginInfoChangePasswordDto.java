package com.hashedin.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class LoginInfoChangePasswordDto {

    private String emailId;

    @NotBlank(message="Password not received")
    private String password;

    @NotBlank(message="New Password not received")
    private String newPassword;

    @NotBlank(message="Confirm New Password not received")
    private String confirmNewPassword;
}
