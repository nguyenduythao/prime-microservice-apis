package com.prime.user.model.entity;

import com.prime.common.model.entity.AuditField;
import com.prime.user.model.id.RolePermissionId;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants
@Entity
@Table(name = "prime_authority")
@IdClass(RolePermissionId.class)
public class RolePermissionEntity extends AuditField implements Serializable {

    private static final long serialVersionUID = 101059097991712324L;

    @Id
    @Column(name = "role_id")
    private Integer roleId;

    @Id
    @Column(name = "permission_id")
    private Integer permissionId;

    @Column(name = "read_flag", nullable = false, columnDefinition = "boolean default true")
    private Boolean readFlag;

    @Column(name = "insert_flag", nullable = false, columnDefinition = "boolean default true")
    private Boolean insertFlag;

    @Column(name = "update_flag", nullable = false, columnDefinition = "boolean default true")
    private Boolean updateFlag;

    @Column(name = "delete_flag", nullable = false, columnDefinition = "boolean default true")
    private Boolean deleteFlag;

    @Column(name = "is_active", nullable = false, columnDefinition = "boolean default true")
    private Boolean is_active;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", referencedColumnName = "role_id", insertable = false, updatable = false)
    private RoleEntity role;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "permission_id", referencedColumnName = "permission_id", insertable = false, updatable = false)
    private PermissionEntity permission;
}
