package com.prime.auth.exception;

import com.prime.auth.common.constant.AuthErrorCode;
import com.prime.auth.common.constant.ClientFieldName;
import com.prime.common.constant.ErrorCategory;
import com.prime.common.exception.ErrorCode;
import com.prime.common.util.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.oauth2.common.DefaultThrowableAnalyzer;
import org.springframework.security.oauth2.common.exceptions.*;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.web.util.ThrowableAnalyzer;
import org.springframework.stereotype.Component;

@Component
public class CustomWebResponseExceptionTranslator implements WebResponseExceptionTranslator<OAuth2Exception> {

    @Autowired
    private ErrorMessage errorMessage;

    private ThrowableAnalyzer throwableAnalyzer = new DefaultThrowableAnalyzer();

    @Override
    public ResponseEntity<OAuth2Exception> translate(Exception e) {
        // Try to extract a SpringSecurityException from the stacktrace
        Throwable[] causeChain = throwableAnalyzer.determineCauseChain(e);

        Throwable exception = throwableAnalyzer.getFirstThrowableOfType(InvalidClientException.class, causeChain);
        ErrorCode errorCode;

        if (exception instanceof InvalidClientException) {
            errorCode = errorMessage.getError(AuthErrorCode.INVALID_CLIENT);
            errorCode.setFieldName(ClientFieldName.CLIENT_ID);
            return createResponse(HttpStatus.BAD_REQUEST.value(), errorCode);
        }

        exception = throwableAnalyzer.getFirstThrowableOfType(UnauthorizedClientException.class, causeChain);
        if (exception instanceof UnauthorizedClientException) {
            errorCode = errorMessage.getError(AuthErrorCode.UNAUTHORIZED_CLIENT);
            errorCode.setFieldName(ClientFieldName.CLIENT_ID);
            return createResponse(HttpStatus.UNAUTHORIZED.value(), errorCode);
        }

        exception = throwableAnalyzer.getFirstThrowableOfType(InvalidGrantException.class, causeChain);
        if (exception instanceof InvalidGrantException) {
            errorCode = errorMessage.getError(AuthErrorCode.INVALID_GRANT);
            errorCode.setFieldName(ClientFieldName.GRANT_TYPE);
            return createResponse(HttpStatus.BAD_REQUEST.value(), errorCode);
        }

        exception = throwableAnalyzer.getFirstThrowableOfType(InvalidScopeException.class, causeChain);
        if (exception instanceof InvalidScopeException) {
            errorCode = errorMessage.getError(AuthErrorCode.INVALID_SCOPE);
            errorCode.setFieldName(ClientFieldName.SCOPE);
            return createResponse(HttpStatus.BAD_REQUEST.value(), errorCode);
        }

        exception = throwableAnalyzer.getFirstThrowableOfType(InvalidTokenException.class, causeChain);
        if (exception instanceof InvalidTokenException) {
            errorCode = errorMessage.getError(AuthErrorCode.INVALID_TOKEN);
            errorCode.setFieldName(ClientFieldName.TOKEN);
            return createResponse(HttpStatus.BAD_REQUEST.value(), errorCode);
        }

        exception = throwableAnalyzer.getFirstThrowableOfType(InvalidRequestException.class, causeChain);
        if (exception instanceof InvalidRequestException) {
            return createResponse(HttpStatus.BAD_REQUEST.value(), errorMessage.getError(AuthErrorCode.INVALID_REQUEST));
        }

        exception = throwableAnalyzer.getFirstThrowableOfType(RedirectMismatchException.class, causeChain);
        if (exception instanceof RedirectMismatchException) {
            return createResponse(HttpStatus.BAD_REQUEST.value(), errorMessage.getError(AuthErrorCode.REDIRECT_URI_MISMATCH));
        }

        exception = throwableAnalyzer.getFirstThrowableOfType(UnsupportedGrantTypeException.class, causeChain);
        if (exception instanceof UnsupportedGrantTypeException) {
            errorCode = errorMessage.getError(AuthErrorCode.UNSUPPORTED_GRANT_TYPE);
            errorCode.setFieldName(ClientFieldName.GRANT_TYPE);
            return createResponse(HttpStatus.BAD_REQUEST.value(), errorCode);
        }

        exception = throwableAnalyzer.getFirstThrowableOfType(UnsupportedResponseTypeException.class, causeChain);
        if (exception instanceof UnsupportedResponseTypeException) {
            return createResponse(HttpStatus.BAD_REQUEST.value(), errorMessage.getError(AuthErrorCode.UNSUPPORTED_RESPONSE_TYPE));
        }

        exception = throwableAnalyzer.getFirstThrowableOfType(UserDeniedAuthorizationException.class, causeChain);
        if (exception instanceof UserDeniedAuthorizationException) {
            return createResponse(HttpStatus.FORBIDDEN.value(), errorMessage.getError(AuthErrorCode.ACCESS_DENIED));
        }

        exception = throwableAnalyzer.getFirstThrowableOfType(BadCredentialsException.class, causeChain);
        if (exception instanceof BadCredentialsException) {
            return createResponse(HttpStatus.BAD_REQUEST.value(), errorMessage.getError(AuthErrorCode.BAD_CREDENTIALS));
        }

        exception = throwableAnalyzer.getFirstThrowableOfType(OAuth2Exception.class, causeChain);
        if (exception instanceof OAuth2Exception) {
            errorCode = errorMessage.getError(AuthErrorCode.UNKNOWN_EXCEPTION);
            errorCode.setCategory(ErrorCategory.SERVICE);
            return createResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), errorCode);
        }

        // Default error code
        errorCode = errorMessage.getError(AuthErrorCode.INTERNAL_SERVER_ERROR);
        errorCode.setCategory(ErrorCategory.SERVICE);
        return createResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), errorCode);
    }

    private ResponseEntity<OAuth2Exception> createResponse(int httpStatusCode, ErrorCode error) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        CustomOAuth2Exception customOAuth2Exception = new CustomOAuth2Exception(error);
        return new ResponseEntity<>(customOAuth2Exception, headers, HttpStatus.valueOf(httpStatusCode));
    }
}