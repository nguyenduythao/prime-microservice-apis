package com.prime.common.enums.user;

import com.prime.common.enums.Code;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum UserStatus implements Code<Integer> {

    ACTIVATED(1),
    DISABLED(2),
    USER_LOCKED(3),
    SYSTEMS_LOCKED(4);

    @Getter
    private final Integer code;

    public static UserStatus fromCode(Integer code) {
        return Code.fromCode(code, UserStatus.values());
    }

    public static UserStatus fromCode(Integer code, UserStatus defaultValue) {
        return Code.fromCode(code, UserStatus.values(), defaultValue);
    }
}
