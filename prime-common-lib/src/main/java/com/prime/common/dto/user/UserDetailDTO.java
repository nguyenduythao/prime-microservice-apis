package com.prime.common.dto.user;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailDTO implements Serializable {

    private static final long serialVersionUID = -569723500370301008L;

    private String firstName;

    private String lastName;

    private String gender;

    private String dob;

    private String address;
}
