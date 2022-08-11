package com.prime.auth.common.constant;

import com.prime.common.constant.ErrorCategory;
import com.prime.common.exception.ErrorCode;

public class AuthErrorCode {

    public static ErrorCode INVALID_CLIENT = new ErrorCode("ERR4001", "Invalid client", ErrorCategory.VALIDATE, "clientId");
    public static ErrorCode UNAUTHORIZED_CLIENT = new ErrorCode("ERR4002", "Unauthorized client", ErrorCategory.VALIDATE, "clientId");
    public static ErrorCode INVALID_GRANT = new ErrorCode("ERR4003", "Invalid grant", ErrorCategory.VALIDATE, "grant_type");
    public static ErrorCode INVALID_SCOPE = new ErrorCode("ERR4004", "Invalid scope", ErrorCategory.VALIDATE, "scope");
    public static ErrorCode INVALID_TOKEN = new ErrorCode("ERR4005", "Invalid token", ErrorCategory.VALIDATE, "token");
    public static ErrorCode INVALID_REQUEST = new ErrorCode("ERR4006", "Invalid request", ErrorCategory.VALIDATE);
    public static ErrorCode REDIRECT_URI_MISMATCH = new ErrorCode("ERR4007", "Redirect uri mismatch", ErrorCategory.VALIDATE);
    public static ErrorCode UNSUPPORTED_GRANT_TYPE = new ErrorCode("ERR4008", "Unsupported grant type", ErrorCategory.VALIDATE, "grant_type");
    public static ErrorCode UNSUPPORTED_RESPONSE_TYPE = new ErrorCode("ERR4009", "Unsupported response type", ErrorCategory.VALIDATE);
    public static ErrorCode ACCESS_DENIED = new ErrorCode("ERR4010", "Access denied", ErrorCategory.VALIDATE);
    public static ErrorCode BAD_CREDENTIALS = new ErrorCode("ERR4011", "Username or password incorrect", ErrorCategory.VALIDATE);
    public static ErrorCode INTERNAL_SERVER_ERROR = new ErrorCode("ERR5000", "Internal server error", ErrorCategory.VALIDATE);

}
