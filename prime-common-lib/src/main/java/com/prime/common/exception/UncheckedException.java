package com.prime.common.exception;

import org.springframework.core.NestedRuntimeException;

/**
 * Base class for all Uncchecked exceptions
 *
 * @author ThaoND
 */
public abstract class UncheckedException extends NestedRuntimeException {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 7544223281433256393L;

    private final ErrorCode error;

    public UncheckedException(ErrorCode error) {
        super(error.getMessage());
        this.error = error;
    }

    public UncheckedException(String code, String message) {
        super(message);
        this.error = new ErrorCode(code, message);
    }

    public UncheckedException(String code, String message, Object[] args) {
        super(message);
        this.error = new ErrorCode(code, message, args);
    }

    public UncheckedException(String code, String message, Throwable cause) {
        super(message, cause);
        this.error = new ErrorCode(code, message);
    }

    public UncheckedException(String code, String message, Throwable cause, Object[] args) {
        super(message, cause);
        this.error = new ErrorCode(code, message, args);
    }

    /**
     * @return the error
     */
    public ErrorCode getError() {
        return error;
    }

}
