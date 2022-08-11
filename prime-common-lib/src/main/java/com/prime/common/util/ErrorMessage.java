package com.prime.common.util;

import com.prime.common.constant.ErrorCategory;
import com.prime.common.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;
import java.util.Locale;

@Slf4j
public class ErrorMessage {
    private static final String ERROR_CODE_NOT_FOUND = "ERR???";
    private static final String MESSAGE_NOT_FOUND = "Message not found key: {0}";
    private static final String SPLITTER = "~";

    private final MessageSource messageSource;

    private final HttpServletRequest request;

    public ErrorMessage(MessageSource messageSource, HttpServletRequest request) {
        this.messageSource = messageSource;
        this.request = request;
    }

    private Locale getLocale() {
        AcceptHeaderLocaleResolver acceptHeaderLocaleResolver = new AcceptHeaderLocaleResolver();
        acceptHeaderLocaleResolver.setDefaultLocale(Locale.ENGLISH);
        return acceptHeaderLocaleResolver.resolveLocale(request);
    }

    public ErrorCode getError(String key) {
        return getError(key, null, null, getLocale());
    }

    public ErrorCode getError(String key, String fieldName) {
        return getError(key, fieldName, null, getLocale());
    }

    public ErrorCode getError(String key, String fieldName, Object[] args) {
        return getError(key, fieldName, args, getLocale());
    }

    public ErrorCode getError(String key, String fieldName, Object[] args, Locale locale) {
        try {
            String errorStr = messageSource.getMessage(key, args, locale);
            return createError(errorStr, key, fieldName);
        } catch (NoSuchMessageException e) {
            log.error("NoSuchMessageException: Message property key {}, field name: {}, details: {}, ", key, fieldName, e.getMessage());
            return new ErrorCode(ERROR_CODE_NOT_FOUND, MessageFormat.format(MESSAGE_NOT_FOUND, key), ErrorCategory.SERVICE);
        }
    }

    private ErrorCode createError(String message, String key, String fieldName) {
        if (StringUtils.hasText(message)) {
            String[] msg = message.split(SPLITTER);
            /* Error message with format is ERROR<Code>~<Message>.
               Split by '~' then length = 2, index 0 is error code, index 1 is error message
            */
            if (msg.length == 2) {
                return new ErrorCode(msg[0], msg[1], ErrorCategory.VALIDATE, fieldName);
            } else {
                return new ErrorCode(ERROR_CODE_NOT_FOUND, message, ErrorCategory.VALIDATE, fieldName);
            }
        }
        return new ErrorCode(ERROR_CODE_NOT_FOUND, MessageFormat.format(MESSAGE_NOT_FOUND, key), ErrorCategory.SERVICE, fieldName);
    }
}
