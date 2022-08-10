package com.prime.common.constant;

public final class BaseConstant {
    /* Code values default */
    public static final Integer ADMINISTRATOR_CODE = -1000;

    /* Counter */
    public static final int MAX_FAILED_ATTEMPTS = 5;

    /* Units in minutes */
    public static final int LOCKED_TIME = 30; // 30 minutes
    public static final int RESET_PASSWORD_TOKEN_EXPIRE_TIME = 30; // 30 minutes
    public static final int ACTIVE_ACCOUNT_TOKEN_EXPIRE_TIME = 30;// 30 minutes

    /* Units in days */
    public static final int PASSWORD_EXPIRE_TIME = 90; // 90 days
    public static final int CUSTOMER_INVITE_EXPIRE_TIME = 1; // 1 day
}
