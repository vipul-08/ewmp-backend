package com.hashedin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hashedin.constants.Constants;
import com.hashedin.constants.LogKeys;
import com.hashedin.dto.RegisterRequestDTO;
import com.hashedin.dto.ResponseDto;
import com.hashedin.dto.SellerVerificationDto;
import com.hashedin.dto.SellerVerificationResponseDto;
import com.hashedin.entity.SellerVerification;
import com.hashedin.service.Impl.SellerVerificationImpl;
//import com.hashedin.service.S3Services;
import lombok.extern.log4j.Log4j2;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.Multipart;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@Log4j2
public class SellerVerificationController {

    @Autowired
    SellerVerificationImpl sellerVerificationImpl;

//    @Autowired
//    S3Services s3Services;


    @PostMapping(value = "/seller/seller-verification", consumes = { MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE } )
    @ResponseStatus(HttpStatus.OK)
    public ResponseDto<SellerVerificationResponseDto> uploadSellerVerififcationInfo(@Valid @RequestBody SellerVerificationDto sellerVerificationDto , @RequestAttribute("id") Long id)
    {
        sellerVerificationImpl.sellerVerification(sellerVerificationDto ,id );
        return ResponseDto.success(Constants.SELLERINFO);

    }


    @GetMapping( value="/seller-verification/verified/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDto<SellerVerificationResponseDto> verifiedSeller(@PathVariable("userId") Long userId)
    {
        sellerVerificationImpl.sellerVerified(userId);
        return ResponseDto.success(Constants.SELLERVERIFIED);

    }

    @GetMapping( value="/seller-verification/not-verified/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public  ResponseDto<SellerVerificationResponseDto> notVerifiedSeller(@PathVariable("userId") Long userId)
    {
        sellerVerificationImpl.sellerNotVerified(userId);
        return ResponseDto.success(Constants.SELLERNOTVERIFIED);

    }


    @GetMapping(value = "seller/verification-status")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDto<SellerVerificationResponseDto> getVerificationStatus(@RequestAttribute("id") Long id)
    {
       log.info(LogKeys.SELLER_VERIFICATION_STATUS);
        return ResponseDto.success(Constants.SELLERVERIFICATIONSTATUS, sellerVerificationImpl.sellerVerificationStatus(id));
    }

    @GetMapping(value = "seller/verification-details")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<JSONObject> getVerificationDetails(@RequestAttribute("id") Long id) {
        return new ResponseEntity<>(sellerVerificationImpl.getSellerVerifiedDetails(id),HttpStatus.OK);
    }

}
