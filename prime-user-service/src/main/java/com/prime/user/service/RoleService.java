package com.prime.user.service;

import com.prime.common.service.BaseService;
import com.prime.user.model.entity.RoleEntity;

import java.util.Optional;

public interface RoleService extends BaseService<RoleEntity, Integer> {
    Optional<RoleEntity> findByRoleName(String roleName);
}
