package com.hashedin.service;

import com.hashedin.dto.BuyerVerificationDto;
import com.hashedin.dto.BuyerVerificationResponseDto;
import com.hashedin.dto.SellerVerificationDto;
import com.hashedin.dto.SellerVerificationResponseDto;

public interface BuyerVerificationService {
    BuyerVerificationResponseDto buyerVerification(BuyerVerificationDto buyerVerificationDto, Long id, int cartId);

    BuyerVerificationResponseDto buyerVerified(String serialNo, int cardId);
    BuyerVerificationResponseDto buyerNotVerified(String serialNo, int cartId);

    BuyerVerificationResponseDto buyerVerificationStatus(String serialNo);

}
