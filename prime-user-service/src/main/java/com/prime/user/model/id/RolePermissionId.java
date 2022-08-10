package com.prime.user.model.id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RolePermissionId implements Serializable {

    private static final long serialVersionUID = 7864636883056807810L;

    private Integer roleId;
    private Integer permissionId;
}
