package com.prime.auth.exception;

import com.prime.common.exception.ErrorCode;
import org.springframework.security.oauth2.common.exceptions.*;

public class CustomOAuth2Exception extends OAuth2Exception {

    private ErrorCode errorCode;

    public CustomOAuth2Exception(String msg, Throwable t) {
        super(msg, t);
    }

    public CustomOAuth2Exception(String msg) {
        super(msg);
    }

    public static OAuth2Exception create(String errorCode, String errorMessage) {
        if (errorMessage == null) {
            errorMessage = errorCode == null ? "OAuth Error" : errorCode;
        }
        if (INVALID_CLIENT.equals(errorCode)) {
            return new InvalidClientException(errorMessage);
        } else if (UNAUTHORIZED_CLIENT.equals(errorCode)) {
            return new UnauthorizedClientException(errorMessage);
        } else if (INVALID_GRANT.equals(errorCode)) {
            return new InvalidGrantException(errorMessage);
        } else if (INVALID_SCOPE.equals(errorCode)) {
            return new InvalidScopeException(errorMessage);
        } else if (INVALID_TOKEN.equals(errorCode)) {
            return new InvalidTokenException(errorMessage);
        } else if (INVALID_REQUEST.equals(errorCode)) {
            return new InvalidRequestException(errorMessage);
        } else if (REDIRECT_URI_MISMATCH.equals(errorCode)) {
            return new RedirectMismatchException(errorMessage);
        } else if (UNSUPPORTED_GRANT_TYPE.equals(errorCode)) {
            return new UnsupportedGrantTypeException(errorMessage);
        } else if (UNSUPPORTED_RESPONSE_TYPE.equals(errorCode)) {
            return new UnsupportedResponseTypeException(errorMessage);
        } else if (ACCESS_DENIED.equals(errorCode)) {
            return new UserDeniedAuthorizationException(errorMessage);
        } else {
            return new OAuth2Exception(errorMessage);
        }
    }
}
