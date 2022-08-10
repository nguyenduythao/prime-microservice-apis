package com.prime.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Gender implements Code<Integer> {
    MALE(0), FEMALE(1);

    @Getter
    private final Integer code;

    public static Gender fromCode(Integer code) {
        return Code.fromCode(code, Gender.values());
    }

    public String getName() {
        return name().toLowerCase();
    }
}
