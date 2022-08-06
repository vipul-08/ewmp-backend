package com.hashedin.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class LoginDto {
    @NotBlank(message="EmailId not received")
    @Email
    private String emailId;

    @NotBlank(message="Password not received")
    private String password;

}
