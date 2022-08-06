package com.hashedin.service;

import com.hashedin.dto.UpdateBusinessNameDto;
import com.hashedin.dto.UpdateUserDetailsDto;
import org.json.simple.JSONObject;

import java.text.ParseException;

public interface UserInfoService {

    JSONObject getUserDetails(Long id);

    JSONObject updateUserDetails(Long id, UpdateUserDetailsDto updateUserDetailsDto) throws ParseException;

    JSONObject getUserBankDetails(Long id);

    JSONObject updateBusinessName(Long id, UpdateBusinessNameDto updateUserBankDetailsDto);
}
