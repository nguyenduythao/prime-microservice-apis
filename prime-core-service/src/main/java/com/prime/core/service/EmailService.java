package com.prime.core.service;

import com.prime.common.dto.MailMessageDTO;

public interface EmailService {

    void sendMail(MailMessageDTO message);

}