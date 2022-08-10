package com.prime.common.dto.user;

import com.prime.common.enums.user.UserStatus;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements Serializable {

    private static final long serialVersionUID = -569723500370301008L;

    private Integer userId;

    private String username;

    private String email;

    private String password;

    private String lastPassword;

    private Date passwordChangedTime;

    private Integer failedAttempt;

    private Date lockedTime;

    private String roleName;

    private UserStatus userStatus;
}
