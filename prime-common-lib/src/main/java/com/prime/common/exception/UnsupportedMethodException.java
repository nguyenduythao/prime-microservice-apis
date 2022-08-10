package com.prime.common.exception;

/**
 * Class UnsupportedMethodException thrown when a call occurs to unsupported method.
 *
 * @author ThaoND
 */
public class UnsupportedMethodException extends BusinessException {

    private static final long serialVersionUID = 1L;

    /**
     * @param code
     * @param message
     * @param args
     */
    public UnsupportedMethodException(String code, String message, Object[] args) {
        super(code, message, args);
    }

    /**
     * @param code
     * @param message
     * @param cause
     * @param args
     */
    public UnsupportedMethodException(String code, String message, Throwable cause, Object[] args) {
        super(code, message, cause, args);
    }

    /**
     * @param code
     * @param message
     * @param cause
     */
    public UnsupportedMethodException(String code, String message, Throwable cause) {
        super(code, message, cause);
    }

    /**
     * @param code
     * @param message
     */
    public UnsupportedMethodException(String code, String message) {
        super(code, message);
    }
}
