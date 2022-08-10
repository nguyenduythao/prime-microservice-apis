package com.prime.user.service.impl;

import com.prime.common.exception.ServiceFailedException;
import com.prime.common.util.ErrorMessage;
import com.prime.user.common.constant.UserErrorCode;
import com.prime.user.common.constant.TableName;
import com.prime.user.model.entity.OAuthClientDetailsEntity;
import com.prime.user.repository.OAuthClientDetailsRepository;
import com.prime.user.service.OAuthClientDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OAuthClientDetailsServiceImpl implements OAuthClientDetailsService {

    @Autowired
    private ErrorMessage errorMessage;

    @Autowired
    private OAuthClientDetailsRepository oAuthClientDetailsRepository;

    @Override
    public Optional<OAuthClientDetailsEntity> findById(String id) {
        return oAuthClientDetailsRepository.findById(id);
    }

    @Override
    public OAuthClientDetailsEntity getById(String id) {
        Optional<OAuthClientDetailsEntity> entityOptional = this.findById(id);
        if (entityOptional.isEmpty()) {
            throw new ServiceFailedException(errorMessage.getError(UserErrorCode.RECORD_NOT_FOUND,
                    OAuthClientDetailsEntity.Fields.clientId, new String[]{TableName.OAUTH_CLIENT_DETAILS}));
        }
        return entityOptional.get();
    }

    @Override
    public OAuthClientDetailsEntity create(final OAuthClientDetailsEntity entity) {
        return oAuthClientDetailsRepository.saveAndFlush(entity);
    }

    @Override
    public OAuthClientDetailsEntity update(OAuthClientDetailsEntity entity) {
        return oAuthClientDetailsRepository.save(entity);
    }

    @Override
    public void delete(OAuthClientDetailsEntity entity) {
        oAuthClientDetailsRepository.delete(entity);
    }
}
