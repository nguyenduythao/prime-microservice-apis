package com.prime.user.service;

import com.prime.common.service.BaseService;
import com.prime.user.model.entity.UserEntity;

import java.util.Optional;

public interface UserService extends BaseService<UserEntity, Integer> {
    /* Find user object functions will return type Optional*/
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByUsername(String email);

    /* Get user function will throw an exception */
    UserEntity getByEmail(String email);
    UserEntity getByUsername(String username);

    /* Create/Update/Delete user object function */
    UserEntity create(UserEntity userEntity);
    UserEntity update(UserEntity userEntity);
    void delete(UserEntity userEntity);
}
