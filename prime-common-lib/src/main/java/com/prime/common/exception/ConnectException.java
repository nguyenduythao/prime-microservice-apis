package com.prime.common.exception;

/**
 * Class to throw ConnectException when connection to Other System cannot be established
 *
 * @author ThaoND
 */
public class ConnectException extends UncheckedException {

    private static final long serialVersionUID = 1L;

    /**
     * @param code
     * @param message
     * @param args
     */
    public ConnectException(String code, String message, Object[] args) {
        super(code, message, args);
    }

    /**
     * @param code
     * @param message
     * @param cause
     * @param args
     */
    public ConnectException(String code, String message, Throwable cause, Object[] args) {
        super(code, message, cause, args);
    }

    /**
     * @param code
     * @param message
     * @param cause
     */
    public ConnectException(String code, String message, Throwable cause) {
        super(code, message, cause);
    }

    /**
     * @param code
     * @param message
     */
    public ConnectException(String code, String message) {
        super(code, message);
    }
}
