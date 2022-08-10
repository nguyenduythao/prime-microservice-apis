package com.prime.user.model.entity;

import com.prime.common.enums.user.UserStatus;
import com.prime.common.model.entity.AuditField;
import com.prime.user.model.converter.UserStatusConverter;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants
@Entity
@Table(name = "prime_user")
public class UserEntity extends AuditField implements Serializable {

    private static final long serialVersionUID = 101059097991712324L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "last_password", columnDefinition = "TEXT")
    private String lastPassword;

    @Column(name = "password_changed_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date passwordChangedTime;

    @Column(name = "failed_attempt")
    private Integer failedAttempt;

    @Column(name = "locked_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lockedTime;

    @Column(name = "role_id")
    private Integer roleId;

    @Column(name = "user_status")
    @Convert(converter = UserStatusConverter.class)
    private UserStatus userStatus;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private UserDetailEntity userDetail;

    @OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", referencedColumnName = "role_id", insertable = false, updatable = false)
    private RoleEntity role;
}
