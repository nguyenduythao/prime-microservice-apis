package com.prime.user.service;

import com.prime.common.dto.MailMessageDTO;
import com.prime.common.dto.user.MailActiveAccountDTO;

public interface EmailService {

    void sendMail(MailMessageDTO message);

    void sendMailActiveAccount(MailActiveAccountDTO mailActiveAccountDTO);

}