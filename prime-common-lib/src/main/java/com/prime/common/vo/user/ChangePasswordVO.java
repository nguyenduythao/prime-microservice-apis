package com.prime.common.vo.user;

import com.prime.common.constant.RegexPattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
public class ChangePasswordVO implements Serializable {

    private static final long serialVersionUID = 8397420174294872197L;

    private String username;

    @NotBlank(message = "{validation.user.common.value.notBlank}")
    private String currentPassword;

    @NotBlank(message = "{validation.user.common.value.notBlank}")
    @Pattern(regexp = RegexPattern.PASSWORD,
            message = "{validation.user.common.password.pattern}")
    private String newPassword;

    @NotBlank(message = "{validation.user.common.value.notBlank}")
    private String confirmPassword;

}
