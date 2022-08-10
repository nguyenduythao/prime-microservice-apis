package com.prime.user.service;

import com.prime.common.enums.TokenType;
import com.prime.common.service.BaseService;
import com.prime.user.model.entity.TokenEntity;

import java.util.Optional;

public interface TokenService extends BaseService<TokenEntity, Integer> {
    Optional<TokenEntity> findByTokenAndTokenType(String token, TokenType tokenType);
}
