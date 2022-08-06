package com.hashedin.validations;

import com.hashedin.constants.ErrorKeys;
import com.hashedin.constants.LogKeys;
import com.hashedin.exceptions.DataExistsException;
import com.hashedin.repository.LoginInfoRepository;
import com.hashedin.repository.UserInfoRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.*;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class ValidateUser {

    @Autowired
    UserInfoRepository userInfoRepository;

    public void validateUser(String email, String contactNumber){
        Long userId = userInfoRepository.findByContactNumber(contactNumber);
        int ifEmailExists = userInfoRepository.checkIfEmailExists(email);

        log.info(LogKeys.VERIFYING_USER);
        if(!(userId==null)){
            throw new DataExistsException(ErrorKeys.NUMBER_EXISTS);
        }
        if(ifEmailExists == 1){
            throw new DataExistsException(ErrorKeys.EMAIL_EXISTS);
        }
        else{
            log.debug(LogKeys.UNREGISTERED_USER);
        }
    }
}
