package com.prime.common.exception;

/**
 * Class to throw an exception when the service call failure occurs during the process flow.
 *
 * @author ThaoND
 */
public class ServiceFailedException extends UncheckedException {

    private static final long serialVersionUID = 1L;

    /**
     * @param code
     * @param message
     * @param args
     */
    public ServiceFailedException(String code, String message, Object[] args) {
        super(code, message, args);
    }

    /**
     * @param code
     * @param message
     * @param cause
     * @param args
     */
    public ServiceFailedException(String code, String message, Throwable cause, Object[] args) {
        super(code, message, cause, args);
    }

    /**
     * @param code
     * @param message
     * @param cause
     */
    public ServiceFailedException(String code, String message, Throwable cause) {
        super(code, message, cause);
    }

    /**
     * @param code
     * @param message
     */
    public ServiceFailedException(String code, String message) {
        super(code, message);
    }

    public ServiceFailedException(ErrorCode error) {
        super(error.getCode(), error.getMessage());
    }
}
