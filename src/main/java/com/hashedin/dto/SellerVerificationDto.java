package com.hashedin.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class SellerVerificationDto {

    @NotBlank(message = "GST NO. not received")
    private String gstNo;

    @NotBlank(message = "IFSC Code not received")
    private  String ifscCode;

    @NotBlank(message = "Bank Account no. Not received")
    private String bankAccountNo;

    @NotBlank(message = "Image url not received")
    private String imageUrl;

//    @NotBlank(message = "cheque image not received")
//    private byte[] imageData;

    // user info


}
