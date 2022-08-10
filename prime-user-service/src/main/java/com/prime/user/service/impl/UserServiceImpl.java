package com.prime.user.service.impl;

import com.prime.common.constant.RedisCacheConstants;
import com.prime.common.exception.ServiceFailedException;
import com.prime.common.util.ErrorMessage;
import com.prime.user.common.constant.UserErrorCode;
import com.prime.user.common.constant.TableName;
import com.prime.user.model.entity.UserEntity;
import com.prime.user.repository.UserRepository;
import com.prime.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private ErrorMessage errorMessage;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<UserEntity> findByUsername(String email) {
        return userRepository.findByUsername(email);
    }

    @Override
    public UserEntity getByEmail(String email) {
        Optional<UserEntity> entityOptional = this.findByEmail(email);
        if (entityOptional.isEmpty()) {
            throw new ServiceFailedException(errorMessage.getError(UserErrorCode.RECORD_NOT_FOUND,
                    UserEntity.Fields.email, new String[]{TableName.USER}));
        }
        return entityOptional.get();
    }

    @Override
    public UserEntity getByUsername(String username) {
        Optional<UserEntity> entityOptional = this.findByUsername(username);
        if (entityOptional.isEmpty()) {
            throw new ServiceFailedException(errorMessage.getError(UserErrorCode.RECORD_NOT_FOUND,
                    UserEntity.Fields.username, new String[]{TableName.USER}));
        }
        return entityOptional.get();
    }

    @Override
    public Optional<UserEntity> findById(Integer id) {
        return userRepository.findById(id);
    }

    @Override
    public UserEntity getById(Integer id) {
        Optional<UserEntity> entityOptional = userRepository.findById(id);
        if (entityOptional.isEmpty()) {
            throw new ServiceFailedException(errorMessage.getError(UserErrorCode.RECORD_NOT_FOUND,
                    UserEntity.Fields.userId, new String[]{TableName.USER}));
        }
        return entityOptional.get();
    }

    @Override
    public UserEntity create(final UserEntity userEntity) {
        return userRepository.saveAndFlush(userEntity);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = RedisCacheConstants.LOAD_USER_BY_USERNAME, key = "#userEntity.username"),
            @CacheEvict(cacheNames = RedisCacheConstants.LOAD_USER_BY_USERNAME, key = "#userEntity.email"),
    })
    public UserEntity update(final UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = RedisCacheConstants.LOAD_USER_BY_USERNAME, key = "#userEntity.username"),
            @CacheEvict(cacheNames = RedisCacheConstants.LOAD_USER_BY_USERNAME, key = "#userEntity.email"),
    })
    public void delete(final UserEntity userEntity) {
        userRepository.delete(userEntity);
    }
}
