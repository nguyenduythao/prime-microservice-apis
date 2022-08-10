package com.prime.common.vo.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
public class UserDetailVO  implements Serializable {

    private static final long serialVersionUID = -569723500370301008L;

    @NotBlank(message = "{validation.user.common.value.notBlank}")
    private String firstName;

    @NotBlank(message = "{validation.user.common.value.notBlank}")
    private String lastName;

    @NotBlank(message = "{validation.user.common.value.notBlank}")
    private String gender;

    private String dob;

    private String address;

}
