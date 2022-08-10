package com.prime.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum TokenType implements Code<Integer> {
    CUSTOMER_ISSUANCE(0), ACCOUNT(1), RESET_PASSWORD(2);

    @Getter
    private final Integer code;

    public static TokenType fromCode(Integer code) {
        return Code.fromCode(code, TokenType.values());
    }

    public static TokenType fromCode(Integer code, TokenType defaultValue) {
        return Code.fromCode(code, TokenType.values(), defaultValue);
    }
}
