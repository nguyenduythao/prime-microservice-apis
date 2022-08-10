package com.prime.common.exception;

import org.springframework.core.NestedCheckedException;

import java.util.ArrayList;
import java.util.List;

/**
 * Base class for all business exceptions.
 *
 * @author ThaoND
 */
public abstract class BusinessException extends NestedCheckedException {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 2223663039483348747L;

    /**
     * errors
     */
    private transient List<ErrorCode> errors = new ArrayList<ErrorCode>();

    /**
     * @param code
     * @param message
     */
    public BusinessException(String code, String message) {
        super(message);
        addError(new ErrorCode(code, message));
    }

    /**
     * @param code
     * @param message
     * @param args
     */
    public BusinessException(String code, String message, Object[] args) {
        super(message);
        addError(new ErrorCode(code, message, args));
    }

    /**
     * @param code
     * @param message
     * @param cause
     */
    public BusinessException(String code, String message, Throwable cause) {
        super(message, cause);
        addError(new ErrorCode(code, message));
    }

    /**
     * @param code
     * @param message
     * @param cause
     * @param args
     */
    public BusinessException(String code, String message, Throwable cause, Object[] args) {
        super(message, cause);
        addError(new ErrorCode(code, message, args));
    }

    /**
     * @param error
     */
    public BusinessException(ErrorCode error) {
        super(error.getMessage());
        addError(error);
    }

    /**
     * @param errors
     */
    public BusinessException(List<ErrorCode> errors) {
        super(errors.get(0).getMessage());
        addAllErrors(errors);
    }

    /**
     * @return the error
     */
    public ErrorCode getError() {
        return errors.get(0);
    }

    /**
     * @return the list of errors
     */
    public List<ErrorCode> getAllErrors() {
        return errors;
    }

    /**
     * @param code
     * @param message
     */
    public void addError(String code, String message) {
        this.errors.add(new ErrorCode(code, message));
    }

    /**
     * @param code
     * @param message
     * @param arguments
     */
    public void addError(String code, String message, Object[] arguments) {
        this.errors.add(new ErrorCode(code, message, arguments));
    }

    /**
     * @param error
     */
    public void addError(ErrorCode error) {
        this.errors.add(error);
    }

    /**
     * @param errors
     */
    public void addAllErrors(List<ErrorCode> errors) {
        this.errors.addAll(errors);
    }

}
