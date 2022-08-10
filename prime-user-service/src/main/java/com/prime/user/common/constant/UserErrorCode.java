package com.prime.user.common.constant;

public class UserErrorCode {
    /* Business error code */
    public static final String ID_NOT_FOUND = "validate.user.id.notFound";
    public static final String CONFIRM_PASSWORD_NOT_MATCH = "validate.user.confirm.notMatch";
    public static final String EMAIL_EXISTED = "validate.user.email.existed";

    public static final String URL_TOKEN_HAS_EXPIRED_TIME = "validate.user.token.expiredTime";

    /* Service error code */
    public static final String EMAIL_SEND_FAILED = "service.user.email.sendFailed";
    public static final String RECORD_NOT_FOUND = "service.user.record.notFound";
}
