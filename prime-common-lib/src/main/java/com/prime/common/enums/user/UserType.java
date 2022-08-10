package com.prime.common.enums.user;

import com.prime.common.enums.Code;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum UserType implements Code<String> {
    ADMIN("admin"),
    MANAGER("manager"),
    USER("user");

    @Getter
    private final String code;

    public static UserType fromCode(String code) {
        return Code.fromCode(code, UserType.values());
    }

    public static UserType fromCode(String code, UserType defaultValue) {
        return Code.fromCode(code, UserType.values(), defaultValue);
    }
}
