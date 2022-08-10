package com.prime.user.repository;

import com.prime.user.model.entity.RolePermissionEntity;
import com.prime.user.model.id.RolePermissionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermissionEntity, RolePermissionId> {
}
