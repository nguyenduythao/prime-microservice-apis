package com.prime.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthorizedGrantType implements Code<String> {

    PASSWORD("password"),
    REFRESH_TOKEN("refresh_token"),
    IMPLICIT("implicit"),
    TRUST("trust"),
    CLIENT_CREDENTIALS("client_credentials");

    private final String code;

    public static AuthorizedGrantType fromCode(Integer code) {
        return Code.fromCode(code, AuthorizedGrantType.values());
    }
}
