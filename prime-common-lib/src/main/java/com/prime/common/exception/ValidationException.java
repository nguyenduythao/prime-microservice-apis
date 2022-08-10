package com.prime.common.exception;

import java.util.List;

/**
 * Class to throw an exception when validation error occurs in request
 *
 * @author ThaoND
 */
public class ValidationException extends BusinessException {

    private static final long serialVersionUID = 1L;

    /**
     * @param code
     * @param message
     * @param args
     */
    public ValidationException(String code, String message, Object[] args) {
        super(code, message, args);
    }

    /**
     * @param code
     * @param message
     * @param cause
     * @param args
     */
    public ValidationException(String code, String message, Throwable cause, Object[] args) {
        super(code, message, cause, args);
    }

    /**
     * @param code
     * @param message
     * @param cause
     */
    public ValidationException(String code, String message, Throwable cause) {
        super(code, message, cause);
    }

    /**
     * @param code
     * @param message
     */
    public ValidationException(String code, String message) {
        super(code, message);
    }

    public ValidationException(ErrorCode error) {
        super(error);
    }

    /**
     * @param errors
     */
    public ValidationException(List<ErrorCode> errors) {
        super(errors);
    }
}
