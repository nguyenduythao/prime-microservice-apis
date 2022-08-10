package com.prime.common.vo.user;

import com.prime.common.constant.RegexPattern;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
public class RegisterUserVO implements Serializable {

    private static final long serialVersionUID = 8397420174294872197L;

    @NotBlank(message = "{validation.user.common.value.notBlank}")
    @Pattern(regexp = RegexPattern.USERNAME,
            message = "{validation.user.common.username.pattern}")
    private String username;

    @NotBlank(message = "{validation.user.common.value.notBlank}")
    @Email(message = "{validation.user.common.email.invalid}")
    private String email;

    @NotBlank(message = "{validation.user.common.value.notBlank}")
    @Pattern(regexp = RegexPattern.PASSWORD,
            message = "{validation.user.common.password.pattern}")
    private String password;

    @NotBlank(message = "{validation.user.common.value.notBlank}")
    private String confirmPassword;

}
