package com.prime.user.service.impl;

import com.prime.common.exception.ServiceFailedException;
import com.prime.common.util.ErrorMessage;
import com.prime.user.common.constant.TableName;
import com.prime.user.common.constant.UserErrorCode;
import com.prime.user.model.entity.RoleEntity;
import com.prime.user.repository.RoleRepository;
import com.prime.user.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    ErrorMessage errorMessage;

    @Override
    public Optional<RoleEntity> findById(Integer id) {
        return roleRepository.findById(id);
    }

    @Override
    public RoleEntity getById(Integer id) {
        Optional<RoleEntity> entityOptional = this.findById(id);
        if (entityOptional.isEmpty()) {
            throw new ServiceFailedException(errorMessage.getError(UserErrorCode.RECORD_NOT_FOUND,
                    RoleEntity.Fields.roleId, new String[]{TableName.ROLE}));
        }
        return entityOptional.get();
    }

    @Override
    public RoleEntity create(RoleEntity entity) {
        return roleRepository.saveAndFlush(entity);
    }

    @Override
    public RoleEntity update(RoleEntity entity) {
        return roleRepository.save(entity);
    }

    @Override
    public void delete(RoleEntity entity) {
        roleRepository.delete(entity);
    }
}
