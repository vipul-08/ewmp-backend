package com.hashedin.service;

import com.hashedin.dto.SellerVerificationDto;
import com.hashedin.dto.SellerVerificationResponseDto;
import org.json.simple.JSONObject;

public interface SellerVerificationService {
    SellerVerificationResponseDto sellerVerification(SellerVerificationDto sellerVerificationDto, Long id);

    SellerVerificationResponseDto sellerVerified(Long id);
    SellerVerificationResponseDto sellerNotVerified(Long id);
    SellerVerificationResponseDto sellerVerificationStatus(Long id);

    JSONObject getSellerVerifiedDetails(Long id);


}
