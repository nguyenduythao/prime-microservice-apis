package com.prime.user.service.impl;

import com.prime.common.exception.ServiceFailedException;
import com.prime.common.util.ErrorMessage;
import com.prime.user.common.constant.TableName;
import com.prime.user.common.constant.UserErrorCode;
import com.prime.user.model.entity.PermissionEntity;
import com.prime.user.repository.PermissionRepository;
import com.prime.user.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private ErrorMessage errorMessage;

    @Override
    public Optional<PermissionEntity> findById(Integer id) {
        return permissionRepository.findById(id);
    }

    @Override
    public PermissionEntity getById(Integer id) {
        Optional<PermissionEntity> entityOptional = this.findById(id);
        if (entityOptional.isEmpty()) {
            throw new ServiceFailedException(errorMessage.getError(UserErrorCode.RECORD_NOT_FOUND, PermissionEntity.Fields.permissionId, new String[]{TableName.PERMISSION}));
        }
        return entityOptional.get();
    }

    @Override
    public PermissionEntity create(PermissionEntity entity) {
        return permissionRepository.saveAndFlush(entity);
    }

    @Override
    public PermissionEntity update(PermissionEntity entity) {
        return permissionRepository.save(entity);
    }

    @Override
    public void delete(PermissionEntity entity) {
        permissionRepository.delete(entity);
    }
}
