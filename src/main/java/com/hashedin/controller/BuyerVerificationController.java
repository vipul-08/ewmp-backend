package com.hashedin.controller;

import com.hashedin.constants.Constants;
import com.hashedin.constants.LogKeys;
import com.hashedin.dto.*;
import com.hashedin.service.Impl.BuyerVerificationImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Log4j2
public class BuyerVerificationController {

    @Autowired
    BuyerVerificationImpl buyerVerificationImpl;


    @PostMapping(value = "/buyer/buyer-verification/{cartId}" )
    @ResponseStatus(HttpStatus.OK)
    public ResponseDto<BuyerVerificationResponseDto> uploadSellerVerififcationInfo(@Valid @RequestBody BuyerVerificationDto buyerVerificationDto, @RequestAttribute("id") Long id, @PathVariable("cartId") int cartId)
    {

        buyerVerificationImpl.buyerVerification(buyerVerificationDto,id , cartId);
        return ResponseDto.success(Constants.BUYERINFO);

    }
    @PostMapping( value="/buyer-verification/verified/{serialNo}/{cartId}")
    @ResponseStatus(HttpStatus.OK)
    public  ResponseDto<BuyerVerificationResponseDto> verifiedBuyer(@PathVariable("serialNo") String serialNo , @PathVariable("cartId") int cartId)
    {
        buyerVerificationImpl.buyerVerified(serialNo, cartId);
        return ResponseDto.success(Constants.BUYERVERIFIED);

    }
    @PostMapping( value="/buyer-verification/not-verified/{serialNo}/{cartId}")
    @ResponseStatus(HttpStatus.OK)
    public  ResponseDto<BuyerVerificationResponseDto> notVerifiedBuyer(@PathVariable("serialNo") String serialNo, @PathVariable("cartId") int cartId)
    {
        buyerVerificationImpl.buyerNotVerified(serialNo, cartId);
        return ResponseDto.success(Constants.BUYERNOTVERIFIED);

    }


    @GetMapping(value = "buyer/verification-status/{serialNo}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDto<BuyerVerificationResponseDto> getVerificationStatus(@PathVariable("serialNo") String serialNo ,@RequestAttribute("id") Long id)
    {
        log.info(LogKeys.BUYER_VERIFICATION_STATUS);
        return ResponseDto.success(Constants.BUYERVERIFICATIONSTATUS, buyerVerificationImpl.buyerVerificationStatus(serialNo));
    }



}
