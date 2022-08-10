package com.prime.common.vo.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ForgotPasswordVO implements Serializable {

    private static final long serialVersionUID = 3860540376209251362L;

    @NotBlank(message = "{validation.user.common.value.notBlank}")
    @Email(message = "{validation.user.common.email.invalid}")
    private String email;

}
