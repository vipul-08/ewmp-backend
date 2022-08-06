package com.hashedin.service.Impl;

import com.hashedin.constants.LogKeys;
import com.hashedin.dto.SellerVerificationDto;
import com.hashedin.dto.SellerVerificationResponseDto;
import com.hashedin.entity.EmailTemplate;
import com.hashedin.entity.SellerVerification;
import com.hashedin.repository.AddToCartRepository;
import com.hashedin.repository.SellerVerificationRepository;
import com.hashedin.repository.UserInfoRepository;
import com.hashedin.service.SellerVerificationService;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.log4j.Log4j2;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import com.hashedin.constants.Constants;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import freemarker.template.Configuration;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@Log4j2
public class SellerVerificationImpl implements SellerVerificationService {



    @Autowired
    EmailServiceImpl emailServiceImpl;

    @Autowired
    SellerVerificationRepository sellerVerificationRepository;

    @Autowired
    AddToCartRepository addToCartRepository;

    @Override
    public SellerVerificationResponseDto sellerVerification(SellerVerificationDto sellerVerificationDto , Long id ) {

        String message =Constants.SELLERINFO;

        // email
        EmailTemplate email = new EmailTemplate();
        email.setTo(Constants.SENTTOADMIN);
        email.setFrom(Constants.SENTFROM);
        email.setSubject(Constants.MAILSUBJECTSELLER);
        email.setContent(Constants.MAILCONTENT);
        Map<String, Object> model = new HashMap<>();
        model.put("gstNo", sellerVerificationDto.getGstNo());
        model.put("accountNo", sellerVerificationDto.getBankAccountNo());
        model.put("ifscCode", sellerVerificationDto.getIfscCode());
        model.put("imageUrl", sellerVerificationDto.getImageUrl());
        model.put("pathToBeCalled", Constants.PATH);

       // model.put("imageData", sellerVerificationDto.getImageData());
        model.put("userId", id);
        email.setModel((HashMap<String, Object>) model);
        try {
            emailServiceImpl.sendEmailTemplateToSeller(email);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        sellerVerificationRepository.deleteData(id);
        sellerVerificationRepository.savingData(sellerVerificationDto.getGstNo(),sellerVerificationDto.getBankAccountNo(),sellerVerificationDto.getIfscCode(),sellerVerificationDto.getImageUrl(), id, SellerVerification.Vstatus.PENDING );

        log.info(LogKeys.SELLER_INFO);
        return SellerVerificationResponseDto.builder()
                .message(message)
                .build();

    }

    @Override
    public SellerVerificationResponseDto sellerVerified(Long id) {
        String message=Constants.SELLERVERIFIED;
        sellerVerificationRepository.updateVerified(id);
        log.info(LogKeys.SELLER_VERIFIED);
        //addToCartRepository.updateStatusToVerified(cartId,'VERIFIED');

        return SellerVerificationResponseDto.builder()
                .message(message)
                .build();
    }

    @Override
    public SellerVerificationResponseDto sellerNotVerified(Long id) {
        String message=Constants.SELLERNOTVERIFIED;
        sellerVerificationRepository.updateNOTVerified(id);
        log.info(LogKeys.SELLER_NOTVERIFIED);
        //addToCartRepository.updateStatusToVerified(cartId,'VERIFIED');
        return SellerVerificationResponseDto.builder()
                .message(message)
                .build();

    }

    @Override
    public SellerVerificationResponseDto sellerVerificationStatus(Long id) {
        log.info(LogKeys.SELLER_VERIFICATION_STATUS);
        String message=    sellerVerificationRepository.getStatusOfVerification(id);
        return SellerVerificationResponseDto.builder()
                .message(message)
                .build();
    }

    @Override
    public JSONObject getSellerVerifiedDetails(Long id) {
        JSONObject obj = sellerVerificationRepository.getVerifiedSellerDetails(id);
        if (obj == null) {
            return sellerVerificationRepository.getNonVerifiedSellerDetails(id);
        }
        return sellerVerificationRepository.getVerifiedSellerDetails(id);
    }


}







