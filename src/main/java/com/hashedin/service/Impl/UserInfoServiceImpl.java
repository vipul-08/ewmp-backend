package com.hashedin.service.Impl;

import com.hashedin.dto.UpdateBusinessNameDto;
import com.hashedin.dto.UpdateUserDetailsDto;
import com.hashedin.repository.AddressInfoRepository;
import com.hashedin.repository.SellerVerificationRepository;
import com.hashedin.repository.UserInfoRepository;
import com.hashedin.service.UserInfoService;
import lombok.extern.log4j.Log4j2;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Log4j2
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    UserInfoRepository userInfoRepository;

    @Autowired
    AddressInfoRepository addressInfoRepository;

    @Autowired
    SellerVerificationRepository sellerVerificationRepository;

    @Override
    public JSONObject getUserDetails(Long id) {
        JSONObject userDetails = userDetails(id);
        return userDetails;
    }

    private JSONObject userDetails(Long id) {
        JSONObject userInfo = userInfoRepository.getUserDetails(id);
        return userInfo;
    }

    @Override
    public JSONObject updateUserDetails(Long id, UpdateUserDetailsDto updateUserDetailsDto) throws ParseException {
        String date= updateUserDetailsDto.getDateOfBirth();
        Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(date);
        userInfoRepository.updateUserDetails(id,updateUserDetailsDto.getFname(),updateUserDetailsDto.getLname(),updateUserDetailsDto.getGender(),date1);
        addressInfoRepository.updateUserAddressDetails(id,updateUserDetailsDto.getAddressLine1(),updateUserDetailsDto.getAddressLine2(),updateUserDetailsDto.getCity(),updateUserDetailsDto.getPincode(),updateUserDetailsDto.getState());
        JSONObject updateUserDetails = userDetails(id);
        return updateUserDetails;
    }

    @Override
    public JSONObject getUserBankDetails(Long id) {
        JSONObject userBankDetails = userInfoRepository.getUserBankdetails(id);
        return userBankDetails;
    }

    @Override
    public JSONObject updateBusinessName(Long id, UpdateBusinessNameDto userBankDetailsDto) {
        userInfoRepository.updateBusinessName(id,userBankDetailsDto.getBname());
        JSONObject userBankDetails = userInfoRepository.getUserBankdetails(id);
        return userBankDetails;
    }
}
