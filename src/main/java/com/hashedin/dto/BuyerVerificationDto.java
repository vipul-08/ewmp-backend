package com.hashedin.dto;

import com.hashedin.entity.BuyerVerification;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
public class BuyerVerificationDto {

    @NotBlank
    private String serialNo;

    @NotBlank
    private String invoiceImage;

    @NotBlank
    private List<String> hardwareImage;


}
