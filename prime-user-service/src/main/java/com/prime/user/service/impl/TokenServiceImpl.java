package com.prime.user.service.impl;

import com.prime.common.enums.TokenType;
import com.prime.common.exception.ServiceFailedException;
import com.prime.common.util.ErrorMessage;
import com.prime.user.common.constant.UserErrorCode;
import com.prime.user.common.constant.TableName;
import com.prime.user.model.entity.TokenEntity;
import com.prime.user.repository.TokenRepository;
import com.prime.user.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private ErrorMessage errorMessage;

    @Autowired
    private TokenRepository tokenRepository;

    @Override
    public Optional<TokenEntity> findByTokenAndTokenType(String token, TokenType tokenType) {
        return tokenRepository.findByTokenAndTokenType(token, tokenType);
    }

    @Override
    public Optional<TokenEntity> findById(Integer id) {
        return tokenRepository.findById(id);
    }

    @Override
    public TokenEntity getById(Integer id) {
        Optional<TokenEntity> entityOptional = this.findById(id);
        if (entityOptional.isEmpty()) {
            throw new ServiceFailedException(errorMessage.getError(UserErrorCode.RECORD_NOT_FOUND,
                    TokenEntity.Fields.tokenId, new String[]{TableName.TOKEN}));
        }
        return entityOptional.get();
    }

    @Override
    public TokenEntity create(final TokenEntity entity) {
        return tokenRepository.saveAndFlush(entity);
    }

    @Override
    public TokenEntity update(final TokenEntity entity) {
        return tokenRepository.save(entity);
    }

    @Override
    public void delete(final TokenEntity entity) {
        tokenRepository.delete(entity);
    }
}
