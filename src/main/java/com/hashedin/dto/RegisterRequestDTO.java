package com.hashedin.dto;

import com.hashedin.entity.UserInfo;
import com.hashedin.utils.UserType;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Getter
@Setter
public class RegisterRequestDTO {
    
    @NotBlank(message="UserName not received")
    private String firstName;

    private String lastName;

    private String businessName;

    private String emailId;

    private String contactNumber;

    private String password;

    private String confirmPassword;

    private UserType type;

}
