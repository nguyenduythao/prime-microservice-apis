package com.prime.core.exception;

import com.prime.common.constant.ErrorCategory;
import com.prime.common.dto.ErrorResponse;
import com.prime.common.exception.*;
import feign.FeignException;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Global Exception Handler.
 *
 * @author ThaoND
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * @return Method to handle UnsupportedMethodException
     */
    @ExceptionHandler(UnsupportedMethodException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody
    ErrorResponse handleUnsupportedMethodException(UnsupportedMethodException e) {
        ErrorCode error = e.getError();
        error.setCategory(ErrorCategory.VALIDATE);
        LOGGER.warn(error.getMessage());
        return new ErrorResponse(error);
    }

    /**
     * @return Method to handle ServiceFailedException
     */
    @ExceptionHandler(ServiceFailedException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody
    ErrorResponse handleServiceFailedException(ServiceFailedException e) {
        ErrorCode error = e.getError();
        error.setCategory(ErrorCategory.SERVICE);
        if (error.getArguments() instanceof String[]) {
            error.setMessage(((String[]) error.getArguments())[0]);
        }
        LOGGER.error("ServiceFailedException -> ", e);
        return new ErrorResponse(error);
    }

    @ExceptionHandler(ConnectException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody
    ErrorResponse handleServiceFailedException(
            ConnectException e) {
        ErrorCode error = e.getError();
        error.setCategory(ErrorCategory.SERVICE);
        if (error.getArguments() instanceof String[]) {
            error.setMessage(((String[]) error.getArguments())[0]);
        }
        LOGGER.error("ConnectException -> ", e);
        return new ErrorResponse(error);
    }

    /**
     * @return Method to handle ValidationException
     */
    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody
    ErrorResponse handleValidationException(ValidationException e) {
        List<ErrorCode> errors = new ArrayList<>();
        Set<String> errorSet = new HashSet<>();
        for (ErrorCode error : e.getAllErrors()) {
            if (errorSet.add(error.getMessage())) {
                errors.add(error);
                error.setCategory(ErrorCategory.BUSINESS);
                LOGGER.warn(error.getCode(), error.getMessage());
            }
        }

        return new ErrorResponse(errors);
    }

    /**
     * @return Method to handle MethodArgumentNotValidException
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody
    ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<ObjectError> objectErrors = e.getBindingResult().getAllErrors();
        List<ErrorCode> errors = new ArrayList<>(objectErrors.size());
        for (ObjectError objectError : objectErrors) {
            FieldError fieldError = (FieldError) objectError;
            String[] splitMsg = fieldError.getDefaultMessage().split("~");
            if (splitMsg != null && splitMsg.length == 2) {
                StringBuilder msg = new StringBuilder(splitMsg[1]);
                String field = ((FieldError) objectError).getField();
                errors.add(new ErrorCode(splitMsg[0], msg.toString(), ErrorCategory.VALIDATE, field));
            } else {
                errors.add(new ErrorCode("ERR???", fieldError.getDefaultMessage(), ErrorCategory.SERVICE));
            }
        }
        return new ErrorResponse(errors);
    }

    /**
     * @return Method to handle HttpMessageNotReadableException
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody
    ErrorResponse handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        ErrorCode error = new ErrorCode("ERR251", "Request JSON Format is Invalid", ErrorCategory.VALIDATE);
        LOGGER.warn("{} {} {}", error.getCode(), "Validation Failed: Request JSON Format is Invalid ",
                e.getMessage());
        return new ErrorResponse(error);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody
    ErrorResponse handleMethodArgumentTypeMismatchException(MissingServletRequestParameterException e) {
        StringBuilder msg = new StringBuilder("Parameter [")
                .append(e.getParameterName())
                .append("] is required");
        ErrorCode error = new ErrorCode("ERR498", msg.toString(), ErrorCategory.VALIDATE, e.getParameterName());
        return new ErrorResponse(error);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody
    ErrorResponse handleMissingServletRequestParameterException(MethodArgumentTypeMismatchException e) {
        StringBuilder msg = new StringBuilder(e.getName()).append("が無効です。");
        ErrorCode error = new ErrorCode("ERR1000", msg.toString(), ErrorCategory.VALIDATE, e.getName());
        return new ErrorResponse(error);
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public @ResponseBody
    ErrorResponse handleUnauthorizedException(UnauthorizedException e) {
        ErrorCode error = e.getError();
        if (error.getArguments() instanceof String[]) {
            error.setMessage(((String[]) error.getArguments())[0]);
        }
        LOGGER.error("UnauthorizedException -> ", e);
        return new ErrorResponse(error);
    }

    @SuppressWarnings("rawtypes")
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody
    ErrorResponse handleConstraintViolationException(ConstraintViolationException e) {
        List<Object> objectErrors = new ArrayList<>(e.getConstraintViolations());
        List<ErrorCode> errors = new ArrayList<>(objectErrors.size());
        for (Object object : objectErrors) {
            ConstraintViolationImpl ex = (ConstraintViolationImpl) object;
            var pathImpl = ((PathImpl) ex.getPropertyPath());
            String[] splitMsg = ex.getMessage().split("~");
            if (splitMsg != null && splitMsg.length == 2) {
                StringBuilder msg = new StringBuilder(splitMsg[1]);
                errors.add(new ErrorCode(splitMsg[0], msg.toString(), ErrorCategory.VALIDATE, pathImpl.getLeafNode().getName()));
            } else {
                errors.add(new ErrorCode("ERR???", ex.getMessage(), ErrorCategory.SERVICE, pathImpl.getLeafNode().getName()));
            }
        }
        return new ErrorResponse(errors);
    }

    @ExceptionHandler(UndeclaredThrowableException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody
    ErrorResponse handleValidationThrowable(UndeclaredThrowableException e) {
        if (e.getUndeclaredThrowable() instanceof ValidationException) {
            return handleValidationException((ValidationException) e.getUndeclaredThrowable());
        }
        return handleAnotherException(e);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody
    ErrorResponse handleRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        LOGGER.error("ERROR:", e);
        return new ErrorResponse(new ErrorCode("ERR400", "Method not supported.", ErrorCategory.VALIDATE));
    }

    @ExceptionHandler(FeignException.class)
    public @ResponseBody ResponseEntity<?> handleFeignStatusException(FeignException e) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (e.status() >= 400 && e.status() < 500) {
            return ResponseEntity.status(e.status())
                    .headers(headers)
                    .body(e.contentUTF8());
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .headers(headers)
                    .body(new ErrorResponse(new ErrorCode("ERR500", "Server call internal API failed.", ErrorCategory.SERVICE)));
        }
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody
    ErrorResponse handleAnotherException(Exception e) {
        LOGGER.error("ERROR:", e);
        return new ErrorResponse(new ErrorCode("ERR500", "Internal server error.", ErrorCategory.SERVICE));
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public @ResponseBody
    ErrorResponse handleAccessDeniedException(AccessDeniedException e) {
        LOGGER.error("AccessDeniedException -> ", e);
        return new ErrorResponse(new ErrorCode("ERR403", e.getMessage(), ErrorCategory.SERVICE));
    }

}
