package com.hashedin.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserDetailsDto {

    private String fname;
    private String lname;
    private String gender;
    private String dateOfBirth;
    private String bname;
    private String emailId;
    private String contactNumber;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private int pincode;
    private String state;

}
