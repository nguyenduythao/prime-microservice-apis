package com.prime.common.config;

import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.TimeZoneAwareLocaleContext;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.TimeZone;

public class CustomSessionLocaleResolver extends SessionLocaleResolver {

    private String timeZoneAttributeName = TIME_ZONE_SESSION_ATTRIBUTE_NAME;

    @Override
    public LocaleContext resolveLocaleContext(final HttpServletRequest request) {
        return new TimeZoneAwareLocaleContext() {
            @Override
            public Locale getLocale() {
                Locale locale = determineDefaultLocale(request);
                String acceptLanguage = request.getHeader(HttpHeaders.ACCEPT_LANGUAGE);
                if (acceptLanguage != null && !acceptLanguage.trim().isEmpty() && request.getLocale() != null) {
                    locale = request.getLocale();
                }
                return locale;
            }

            @Override
            public TimeZone getTimeZone() {
                TimeZone timeZone = (TimeZone) WebUtils.getSessionAttribute(request, timeZoneAttributeName);
                if (timeZone == null) {
                    timeZone = determineDefaultTimeZone(request);
                }
                return timeZone;
            }
        };
    }
}
