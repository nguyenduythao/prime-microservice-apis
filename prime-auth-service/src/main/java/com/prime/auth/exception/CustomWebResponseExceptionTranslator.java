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

        if (exception instanceof InvalidClientException) {
            return createResponse(HttpStatus.BAD_REQUEST.value(), errorMessage.getError(AuthErrorCode.INVALID_CLIENT, ClientFieldName.CLIENT_ID));
        }

        exception = throwableAnalyzer.getFirstThrowableOfType(UnauthorizedClientException.class, causeChain);
        if (exception instanceof UnauthorizedClientException) {
            return createResponse(HttpStatus.UNAUTHORIZED.value(), errorMessage.getError(AuthErrorCode.UNAUTHORIZED_CLIENT, ClientFieldName.CLIENT_ID));
        }

        exception = throwableAnalyzer.getFirstThrowableOfType(InvalidGrantException.class, causeChain);
        if (exception instanceof InvalidGrantException) {
            return createResponse(HttpStatus.BAD_REQUEST.value(), errorMessage.getError(AuthErrorCode.INVALID_GRANT, ClientFieldName.GRANT_TYPE));
        }

        exception = throwableAnalyzer.getFirstThrowableOfType(InvalidScopeException.class, causeChain);
        if (exception instanceof InvalidScopeException) {
            return createResponse(HttpStatus.BAD_REQUEST.value(), errorMessage.getError(AuthErrorCode.INVALID_SCOPE, ClientFieldName.SCOPE));
        }

        exception = throwableAnalyzer.getFirstThrowableOfType(InvalidTokenException.class, causeChain);
        if (exception instanceof InvalidTokenException) {
            return createResponse(HttpStatus.BAD_REQUEST.value(), errorMessage.getError(AuthErrorCode.INVALID_TOKEN, ClientFieldName.TOKEN));
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
            return createResponse(HttpStatus.BAD_REQUEST.value(), errorMessage.getError(AuthErrorCode.UNSUPPORTED_GRANT_TYPE, ClientFieldName.GRANT_TYPE));
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

        exception = throwableAnalyzer.getFirstThrowableOfType(LockedTemporaryException.class, causeChain);
        if (exception instanceof LockedTemporaryException) {
            return createResponse(HttpStatus.BAD_REQUEST.value(), ((LockedTemporaryException) exception).getErrorCode());
        }

        exception = throwableAnalyzer.getFirstThrowableOfType(OAuth2Exception.class, causeChain);
        if (exception instanceof OAuth2Exception) {
            ErrorCode errorCode = errorMessage.getError(AuthErrorCode.UNKNOWN_EXCEPTION);
            errorCode.setCategory(ErrorCategory.SERVICE);
            return createResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), errorCode);
        }

        // Default error code
        ErrorCode errorCode = errorMessage.getError(AuthErrorCode.INTERNAL_SERVER_ERROR);
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