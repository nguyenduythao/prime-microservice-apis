package com.prime.user.task;

import com.prime.common.dto.user.MailActiveAccountDTO;
import com.prime.user.service.EmailService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MailActiveAccountTaskExecutor implements Runnable {

    private EmailService emailService;
    private MailActiveAccountDTO mailActiveDTO;

    public MailActiveAccountTaskExecutor(EmailService emailService, MailActiveAccountDTO mailActiveDTO) {
        this.emailService = emailService;
        this.mailActiveDTO = mailActiveDTO;
    }

    @Override
    public void run() {
        try {
            log.debug("+++ Mail active account task executor start... ++++");
            emailService.sendMailActiveAccount(mailActiveDTO);
            log.debug("+++ Mail active account task executor end. ++++");
        } catch (Exception e) {
            log.error("Exception MailActiveAccountTaskExecutor: {}", e.getMessage());
        }
    }
}
