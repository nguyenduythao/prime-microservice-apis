package com.prime.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum DeleteFlag implements Code<Integer> {
    VALID(0),
    INVALID(1);

    @Getter
    private final Integer code;

    public static DeleteFlag fromCode(Integer code) {
        return Code.fromCode(code, DeleteFlag.values());
    }

    public static DeleteFlag fromCode(Integer code, DeleteFlag defaultValue) {
        return Code.fromCode(code, DeleteFlag.values(), defaultValue);
    }
}
