package com.prime.auth.exception;

import com.prime.common.exception.ErrorCode;
import lombok.Getter;
import org.springframework.security.authentication.LockedException;

import java.io.Serializable;

@Getter
public class LockedTemporaryException extends LockedException implements Serializable {

    private static final long serialVersionUID = -8300449900515220958L;

    private ErrorCode errorCode;

    public LockedTemporaryException(String msg) {
        super(msg);
    }

    public LockedTemporaryException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public LockedTemporaryException(String msg, Object[] arguments) {
        super(msg);
    }

    public LockedTemporaryException(String msg, Throwable cause) {
        super(msg, cause);
    }


}
