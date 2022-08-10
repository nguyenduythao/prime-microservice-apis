package com.prime.common.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MailActiveAccountDTO {
    private String name;
    private String subject;
    private String email;
    private String url;
}
