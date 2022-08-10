package com.prime.common.exception;

/**
 * Class to throw an exception when request timeout occurs from X
 *
 * @author ThaoND
 */
public class TimeoutException extends UncheckedException {

    private static final long serialVersionUID = 1L;

    /**
     * @param code
     * @param message
     * @param args
     */
    public TimeoutException(String code, String message, Object[] args) {
        super(code, message, args);
    }

    /**
     * @param code
     * @param message
     * @param cause
     * @param args
     */
    public TimeoutException(String code, String message, Throwable cause, Object[] args) {
        super(code, message, cause, args);
    }

    /**
     * @param code
     * @param message
     * @param cause
     */
    public TimeoutException(String code, String message, Throwable cause) {
        super(code, message, cause);
    }

    /**
     * @param code
     * @param message
     */
    public TimeoutException(String code, String message) {
        super(code, message);
    }
}
