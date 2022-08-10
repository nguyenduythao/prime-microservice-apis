package com.prime.common.vo.user;

import com.prime.common.enums.user.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
public class UserVO implements Serializable {
    private static final long serialVersionUID = -569723500370301008L;

    @NotNull(message = "{validation.user.common.value.notNull}")
    private Integer roleId;

}
