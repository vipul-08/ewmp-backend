package com.hashedin.controller;

import com.hashedin.constants.LogKeys;
import com.hashedin.dto.ResponseDto;
import com.hashedin.dto.UpdateBusinessNameDto;
import com.hashedin.dto.UpdateUserDetailsDto;
import com.hashedin.service.UserInfoService;
import lombok.extern.log4j.Log4j2;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@Log4j2
@RestController
public class UserInfoController {

    @Autowired
    public UserInfoService userInfoService;

    @GetMapping(value = "/buyer/details")
    public ResponseDto<JSONObject> getUserDetails(@RequestAttribute("id") Long id) {
        log.info(LogKeys.GETTING_USER_DETAILS);
        return ResponseDto.success(LogKeys.GETTING_USER_DETAILS,userInfoService.getUserDetails(id));
    }

    @PutMapping(value = "/buyer/update-details")
    public ResponseDto<JSONObject> updateUserDetails(@RequestAttribute("id") Long id,@RequestBody UpdateUserDetailsDto updateUserDetailsDto) throws ParseException {
        log.info(LogKeys.UPDATING_USER_DETAILS);
        return ResponseDto.success(LogKeys.UPDATING_USER_DETAILS,userInfoService.updateUserDetails(id,updateUserDetailsDto));
    }

    @GetMapping(value = "/seller/bank-details")
    public ResponseDto<JSONObject> getUserBankDetails(@RequestAttribute("id") Long id) {
        log.info(LogKeys.GETTING_BANK_DETAILS);
        return ResponseDto.success(LogKeys.GETTING_BANK_DETAILS,userInfoService.getUserBankDetails(id));
    }

    @PutMapping(value = "/seller/update-business-name")
    public ResponseDto<JSONObject> updateBusinessName(@RequestAttribute("id") Long id,@RequestBody UpdateBusinessNameDto updateUserBankDetails) {
        log.info("Updating the user's business name");
        return ResponseDto.success("Updating the user's Business name",userInfoService.updateBusinessName(id,updateUserBankDetails));
    }


}
