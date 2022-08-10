package com.prime.user.service.impl;

import com.prime.common.exception.ServiceFailedException;
import com.prime.common.util.ErrorMessage;
import com.prime.user.common.constant.UserErrorCode;
import com.prime.user.common.constant.TableName;
import com.prime.user.model.entity.UserDetailEntity;
import com.prime.user.repository.UserDetailRepository;
import com.prime.user.service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailServiceImpl implements UserDetailService {

    @Autowired
    private ErrorMessage errorMessage;

    @Autowired
    private UserDetailRepository userDetailRepository;

    @Override
    public Optional<UserDetailEntity> findById(Integer id) {
        return userDetailRepository.findById(id);
    }

    @Override
    public UserDetailEntity getById(Integer id) {
        Optional<UserDetailEntity> entityOptional = this.findById(id);
        if (entityOptional.isEmpty()) {
            throw new ServiceFailedException(errorMessage.getError(UserErrorCode.RECORD_NOT_FOUND,
                    UserDetailEntity.Fields.userId, new String[]{TableName.USER_DETAIL}));
        }
        return entityOptional.get();
    }

    @Override
    public UserDetailEntity create(final UserDetailEntity entity) {
        return userDetailRepository.saveAndFlush(entity);
    }

    @Override
    public UserDetailEntity update(final UserDetailEntity entity) {
        return userDetailRepository.save(entity);
    }

    @Override
    public void delete(final UserDetailEntity entity) {
        userDetailRepository.delete(entity);
    }
}
