package com.hashedin.service;

import com.hashedin.entity.EmailTemplate;
import freemarker.template.TemplateException;

import javax.mail.MessagingException;
import java.io.IOException;

public interface EmailService {

    void sendEmailTemplateToSeller(EmailTemplate emailTemplate) throws IOException, TemplateException, MessagingException;
    void sendEmailTemplateToBuyer(EmailTemplate emailTemplate) throws IOException, TemplateException, MessagingException;
    void sendEmailTemplateToUserOrderConfirmation(EmailTemplate emailTemplate) throws IOException, TemplateException, MessagingException;

}
