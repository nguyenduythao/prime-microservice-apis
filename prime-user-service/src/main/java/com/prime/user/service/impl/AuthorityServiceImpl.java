package com.prime.user.service.impl;

import com.prime.common.exception.ServiceFailedException;
import com.prime.common.util.ErrorMessage;
import com.prime.user.common.constant.UserErrorCode;
import com.prime.user.common.constant.TableName;
import com.prime.user.model.entity.RolePermissionEntity;
import com.prime.user.model.id.RolePermissionId;
import com.prime.user.repository.RolePermissionRepository;
import com.prime.user.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthorityServiceImpl implements AuthorityService {

    @Autowired
    private ErrorMessage errorMessage;

    @Autowired
    private RolePermissionRepository rolePermissionRepository;

    @Override
    public Optional<RolePermissionEntity> findById(RolePermissionId id) {
        return rolePermissionRepository.findById(id);
    }

    @Override
    public RolePermissionEntity getById(RolePermissionId id) {
        Optional<RolePermissionEntity> entityOptional = this.findById(id);
        if (entityOptional.isEmpty()) {
            throw new ServiceFailedException(errorMessage.getError(UserErrorCode.RECORD_NOT_FOUND,
                    RolePermissionEntity.Fields.roleId, new String[]{TableName.ROLE_PERMISSION}));
        }
        return entityOptional.get();
    }

    @Override
    public RolePermissionEntity create(final RolePermissionEntity entity) {
        return rolePermissionRepository.saveAndFlush(entity);
    }

    @Override
    public RolePermissionEntity update(final RolePermissionEntity entity) {
        return rolePermissionRepository.save(entity);
    }

    @Override
    public void delete(final RolePermissionEntity entity) {
        rolePermissionRepository.delete(entity);
    }
}
