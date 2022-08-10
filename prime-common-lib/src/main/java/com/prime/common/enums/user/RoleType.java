package com.prime.common.enums.user;

import com.prime.common.enums.Code;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
public enum RoleType implements Code<Integer>, GrantedAuthority {

    ROLE_ADMIN(1),
    ROLE_MANAGER(2),
    ROLE_USER(3);

    @Getter
    private final Integer code;

    public String getAuthority() {
        return name();
    }
}
