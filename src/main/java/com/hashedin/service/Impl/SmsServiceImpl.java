package com.hashedin.service.Impl;

import com.hashedin.constants.Constants;
import com.hashedin.exceptions.InvalidException;
import com.hashedin.service.SmsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;


@Log4j2
@Service
public class SmsServiceImpl implements SmsService {
    private static final String ACCOUNT_SID = "ACc3a08d7ec2a40182c465ac76664db143";
    private static final String AUTH_TOKEN = "7f3766053f8ee1d714cfaa07fa1aa604";
    private static final String NUMBER = "+15103151332";

    static
    {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    public void sendMessage(String contactNumber, String body)
    {
        System.out.printf(contactNumber+"-----");
        try
        {
            Message.creator(new PhoneNumber("+91"+contactNumber) , new PhoneNumber(NUMBER), body).create();
        }
        catch(Exception e)
        {
            log.error("Exception : {} ", e.getStackTrace());
            throw new InvalidException(Constants.MOBILE_NUMBER_NOT_REGISTERED);
        }
    }

}
