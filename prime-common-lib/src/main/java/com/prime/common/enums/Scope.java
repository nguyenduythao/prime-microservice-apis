package com.prime.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Scope implements Code<String> {

    READ("read"), WRITE("write");

    private final String code;

    public static Scope fromCode(Integer code) {
        return Code.fromCode(code, Scope.values());
    }
}
