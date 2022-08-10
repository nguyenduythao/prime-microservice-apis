package com.prime.core.service.impl;

import com.prime.common.dto.MailMessageDTO;
import com.prime.common.exception.ServiceFailedException;
import com.prime.common.util.ErrorMessage;
import com.prime.core.common.constant.CoreErrorCode;
import com.prime.core.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.thymeleaf.TemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Map;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

    private static final String TEMPLATE_INVITE_CUSTOMER = "invite_customer";
    private static final String LOGO_IMAGE = "mail-templates/images/school-logo.jpeg";
    private static final String PNG_MIME = "image/png";

    @Value("${spring.mail.properties.mail.no-reply}")
    private String noReply;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private ErrorMessage errorMessage;

    @Autowired
    private TemplateEngine htmlTemplateEngine;

    @Override
    public void sendMail(MailMessageDTO message) throws ServiceFailedException {
        try {
            if (message.isHtmlMail() || message.getIsAttached()) {
                sendMessage(message);
            } else {
                sendSimpleMessage(message);
            }
        } catch (Exception e) {
            log.error("Send mail occur an exception: {}", e.getMessage());
            throw new ServiceFailedException(errorMessage.getError(CoreErrorCode.EMAIL_SERVICE_FAILED));
        }
    }

    private void sendSimpleMessage(MailMessageDTO mailMessageDTO) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(getFrom(mailMessageDTO));
        message.setTo(mailMessageDTO.getTo());
        message.setSubject(mailMessageDTO.getSubject());
        message.setText(mailMessageDTO.getBody());

        if (mailMessageDTO.getCc() != null && mailMessageDTO.getCc().length > 0) {
            message.setCc(mailMessageDTO.getCc());
        }
        if (mailMessageDTO.getBcc() != null && mailMessageDTO.getBcc().length > 0) {
            message.setBcc(mailMessageDTO.getCc());
        }
        mailSender.send(message);
    }

    private void sendMessage(MailMessageDTO mailMessageDTO) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        helper.setFrom(getFrom(mailMessageDTO));
        helper.setTo(mailMessageDTO.getTo());
        helper.setSubject(mailMessageDTO.getSubject());
        helper.setText(mailMessageDTO.getBody(), true);

        if (mailMessageDTO.getCc() != null && mailMessageDTO.getCc().length > 0) {
            helper.setCc(mailMessageDTO.getCc());
        }

        if (mailMessageDTO.getBcc() != null && mailMessageDTO.getBcc().length > 0) {
            helper.setBcc(mailMessageDTO.getCc());
        }
        // Set file attached from disk
        if (mailMessageDTO.getIsAttached() && !CollectionUtils.isEmpty(mailMessageDTO.getPaths())) {
            for (Map.Entry<String, String> entry : mailMessageDTO.getPaths().entrySet()) {
                if (entry.getValue() != null) {
                    FileSystemResource file = new FileSystemResource(new File(entry.getValue()));
                    helper.addAttachment(entry.getKey(), file);
                }
            }
        }
        // Set image files attached from resource
        if (!CollectionUtils.isEmpty(mailMessageDTO.getImages())) {
            for (Map.Entry<String, String> entry : mailMessageDTO.getImages().entrySet()) {
                if (entry.getValue() != null) {
                    ClassPathResource clr = new ClassPathResource(entry.getValue());
                    helper.addInline(entry.getKey(), clr, PNG_MIME);
                }
            }
        }
        mailSender.send(mimeMessage);
    }

    private String getFrom(MailMessageDTO mailMessageDTO) {
        if (StringUtils.hasText(mailMessageDTO.getFrom())) {
            return mailMessageDTO.getFrom();
        }
        return noReply;
    }
}
