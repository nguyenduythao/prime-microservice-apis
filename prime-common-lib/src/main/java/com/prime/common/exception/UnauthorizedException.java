package com.prime.common.exception;

public class UnauthorizedException extends UncheckedException {

    private static final long serialVersionUID = -2805800424942228529L;

    public UnauthorizedException(String code, String message) {
        super(code, message);
    }

    public UnauthorizedException(String code, String message, Object[] args) {
        super(code, message, args);
    }

    public UnauthorizedException(String code, String message, Throwable cause) {
        super(code, message, cause);
    }

    public UnauthorizedException(String code, String message, Throwable cause, Object[] args) {
        super(code, message, cause, args);
    }
}