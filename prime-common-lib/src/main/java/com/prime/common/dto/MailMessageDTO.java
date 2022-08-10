package com.prime.common.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MailMessageDTO {

    public static final String TEXT_MAIL = "text";
    public static final String HTML_MAIL = "html";

    private String from;
    private String to;
    private String[] cc;
    private String[] bcc;

    private String subject;
    private String body;
    @Builder.Default
    private String type = TEXT_MAIL;

    @Builder.Default
    private Boolean isAttached = Boolean.FALSE;
    private Map<String, String> paths;
    private Map<String, String> images;

    @JsonIgnore
    public boolean isHtmlMail() {
        return HTML_MAIL.equals(this.type);
    }
}
