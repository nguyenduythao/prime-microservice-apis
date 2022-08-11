package com.prime.auth.exception;

import com.prime.auth.common.constant.AuthErrorCode;
import com.prime.common.constant.ErrorCategory;
import com.prime.common.exception.ErrorCode;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.oauth2.common.exceptions.*;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.stereotype.Component;

@Component
public class CustomWebResponseExceptionTranslator implements WebResponseExceptionTranslator<OAuth2Exception> {

    @Override
    public ResponseEntity<OAuth2Exception> translate(Exception exception) {
        if (exception instanceof InvalidClientException) {
            return createResponse(HttpStatus.BAD_REQUEST.value(), AuthErrorCode.INVALID_CLIENT);
        }

        if (exception instanceof UnauthorizedClientException) {
            return createResponse(HttpStatus.UNAUTHORIZED.value(), AuthErrorCode.UNAUTHORIZED_CLIENT);
        }

        if (exception instanceof InvalidGrantException) {
            return createResponse(HttpStatus.BAD_REQUEST.value(), AuthErrorCode.INVALID_GRANT);
        }

        if (exception instanceof InvalidScopeException) {
            return createResponse(HttpStatus.BAD_REQUEST.value(), AuthErrorCode.INVALID_SCOPE);
        }

        if (exception instanceof InvalidTokenException) {
            return createResponse(HttpStatus.BAD_REQUEST.value(), AuthErrorCode.INVALID_TOKEN);
        }

        if (exception instanceof InvalidRequestException) {
            return createResponse(HttpStatus.BAD_REQUEST.value(), AuthErrorCode.INVALID_REQUEST);
        }

        if (exception instanceof RedirectMismatchException) {
            return createResponse(HttpStatus.BAD_REQUEST.value(), AuthErrorCode.REDIRECT_URI_MISMATCH);
        }

        if (exception instanceof UnsupportedGrantTypeException) {
            return createResponse(HttpStatus.BAD_REQUEST.value(), AuthErrorCode.UNSUPPORTED_GRANT_TYPE);
        }

        if (exception instanceof UnsupportedResponseTypeException) {
            return createResponse(HttpStatus.BAD_REQUEST.value(), AuthErrorCode.UNSUPPORTED_RESPONSE_TYPE);
        }

        if (exception instanceof UserDeniedAuthorizationException) {
            return createResponse(HttpStatus.FORBIDDEN.value(), AuthErrorCode.ACCESS_DENIED);
        }

        if (exception instanceof BadCredentialsException) {
            return createResponse(HttpStatus.BAD_REQUEST.value(), AuthErrorCode.BAD_CREDENTIALS);
        }

        if (exception instanceof OAuth2Exception) {
            return createResponse(HttpStatus.BAD_REQUEST.value(), new ErrorCode("ERR4012", exception.getMessage(), ErrorCategory.BUSINESS));
        }

        return createResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), AuthErrorCode.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<OAuth2Exception> createResponse(int httpStatusCode, ErrorCode error) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        CustomOAuth2Exception customOAuth2Exception = new CustomOAuth2Exception(error);
        return new ResponseEntity<>(customOAuth2Exception, headers, HttpStatus.valueOf(httpStatusCode));
    }
}