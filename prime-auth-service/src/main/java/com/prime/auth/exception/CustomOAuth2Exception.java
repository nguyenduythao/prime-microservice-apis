package com.prime.auth.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.prime.common.exception.ErrorCode;
import lombok.Getter;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

@Getter
@JsonSerialize(using = CustomOAuth2ExceptionSerializer.class)
public class CustomOAuth2Exception extends OAuth2Exception {

    private ErrorCode error;

    public CustomOAuth2Exception(String msg, Throwable t) {
        super(msg, t);
    }

    public CustomOAuth2Exception(String msg) {
        super(msg);
    }

    public CustomOAuth2Exception(ErrorCode error) {
        super(error.getMessage());
        this.error = error;
    }
}
