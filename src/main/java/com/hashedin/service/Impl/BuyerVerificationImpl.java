package com.hashedin.service.Impl;

import com.hashedin.constants.Constants;
import com.hashedin.constants.LogKeys;
import com.hashedin.dto.BuyerVerificationDto;
import com.hashedin.dto.BuyerVerificationResponseDto;
import com.hashedin.dto.SellerVerificationResponseDto;
import com.hashedin.entity.BuyerVerification;
import com.hashedin.entity.EmailTemplate;
import com.hashedin.repository.AddToCartRepository;
import com.hashedin.repository.BuyerVerificationRepository;
import com.hashedin.service.BuyerVerificationService;
import freemarker.template.TemplateException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@Log4j2
public class BuyerVerificationImpl implements BuyerVerificationService {
    @Autowired
    EmailServiceImpl emailServiceImpl;


    @Autowired
    BuyerVerificationRepository buyerVerificationRepository;

    @Autowired
    AddToCartRepository addToCartRepository;

    @Override
    public BuyerVerificationResponseDto buyerVerification(BuyerVerificationDto buyerVerificationDto, Long id, int cartId) {

        String message = Constants.BUYERINFO;

        // array of url to comma seperated
        String allHardwareImages= String.join(",", buyerVerificationDto.getHardwareImage());


        //serial no present
        if(buyerVerificationRepository.checkIfSerialNoExists(buyerVerificationDto.getSerialNo())!=0)
       {
           buyerVerificationRepository.updatePending(buyerVerificationDto.getSerialNo());
       }else  // serial no not present !
       {
           buyerVerificationRepository.savingData( buyerVerificationDto.getSerialNo(),buyerVerificationDto.getInvoiceImage(),allHardwareImages,id, BuyerVerification.Vstatus.PENDING );
       }
        addToCartRepository.updateStatusToVerified(cartId,"PENDING");

        // email
        EmailTemplate email = new EmailTemplate();
        email.setTo(Constants.SENTTOADMIN);
        email.setFrom(Constants.SENTFROM);
        email.setSubject(Constants.MAILSUBJECTBUYER);
        email.setContent(Constants.MAILCONTENT);
        Map<String, Object> model = new HashMap<>();
        model.put("hardwareImage", buyerVerificationDto.getHardwareImage());
        model.put("invoiceImage", buyerVerificationDto.getInvoiceImage());
        model.put("serialNo", buyerVerificationDto.getSerialNo());
        model.put("pathToBeCalled", Constants.PATH);
        model.put("cartId", cartId);

        model.put("userId", id);
        email.setModel((HashMap<String, Object>) model);
        try {
            System.out.println("email sent to admin");
            emailServiceImpl.sendEmailTemplateToBuyer(email);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        log.info(LogKeys.BUYER_INFO);
        return BuyerVerificationResponseDto.builder()
                .message(message)
                .build();
    }

    @Override
    public BuyerVerificationResponseDto buyerVerified(String serialNo, int cartId) {
       String message=Constants.BUYERVERIFIED;
       log.info(LogKeys.BUYER_VERIFIED);
        addToCartRepository.updateStatusToVerified(cartId,"VERIFIED");
       buyerVerificationRepository.updateVerified(serialNo);
       return BuyerVerificationResponseDto.builder().message(message).build();
    }

    @Override
    public BuyerVerificationResponseDto buyerNotVerified(String serialNo, int cartId) {
        String message=Constants.BUYERNOTVERIFIED;
        log.info(LogKeys.BUYER_NOTVERIFIED);
        addToCartRepository.updateStatusToVerified(cartId,"NOTVERIFIED");
        buyerVerificationRepository.updateNotVerified(serialNo);
        return BuyerVerificationResponseDto.builder().message(message).build();
    }

    @Override
    public BuyerVerificationResponseDto buyerVerificationStatus(String serialNo) {
        log.info(LogKeys.BUYER_VERIFICATION_STATUS);
       String message =buyerVerificationRepository.getStatusOfVerification(serialNo);
        return BuyerVerificationResponseDto.builder()
                .message(message)
                .build();
    }
}
