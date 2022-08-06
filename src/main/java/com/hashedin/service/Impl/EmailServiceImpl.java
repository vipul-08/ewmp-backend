package com.hashedin.service.Impl;

import com.hashedin.entity.EmailTemplate;
import com.hashedin.service.EmailService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Map;


@Service
@Log4j2
public class EmailServiceImpl implements EmailService {


    @Autowired
    private JavaMailSender emailSender;

    private void sendHtmlMessage(String to, String subject, String htmlBody) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlBody, true);
        emailSender.send(message);
    }

    @Autowired
    private FreeMarkerConfigurer freemarkerConfigurer;

    @Override
    public void sendEmailTemplateToSeller( EmailTemplate emailTemplate)
            throws IOException, TemplateException, MessagingException {

        Template freemarkerTemplate = freemarkerConfigurer.getConfiguration()
                .getTemplate("email-template.ftl");
        //System.out.println(emailTemplate.getModel());
        String htmlBody = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerTemplate, emailTemplate.getModel());

        sendHtmlMessage(emailTemplate.getTo(), emailTemplate.getSubject(),htmlBody);
    }

    @Override
    public void sendEmailTemplateToBuyer(EmailTemplate emailTemplate) throws IOException, TemplateException, MessagingException {


        Template freemarkerTemplate = freemarkerConfigurer.getConfiguration()
                .getTemplate("email-template-buyer.ftl");
       // System.out.println(emailTemplate.getModel());
        String htmlBody = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerTemplate, emailTemplate.getModel());

        sendHtmlMessage(emailTemplate.getTo(), emailTemplate.getSubject(),htmlBody);
    }

    @Override
    public void sendEmailTemplateToUserOrderConfirmation(EmailTemplate emailTemplate) throws IOException , TemplateException, MessagingException{
        Template freemarkerTemplate = freemarkerConfigurer.getConfiguration()
                .getTemplate("email-template-order-confirmation.ftl");
        String htmlBody = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerTemplate, emailTemplate.getModel());
        sendHtmlMessage(emailTemplate.getTo(), emailTemplate.getSubject(),htmlBody);

    }


}
