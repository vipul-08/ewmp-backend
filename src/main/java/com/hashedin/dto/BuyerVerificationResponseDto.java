package com.hashedin.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Setter;

@Setter
@Data
@Builder
public class BuyerVerificationResponseDto {
    private String message;
}
