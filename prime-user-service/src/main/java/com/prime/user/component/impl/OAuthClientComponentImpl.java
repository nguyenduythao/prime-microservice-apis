package com.prime.user.component.impl;

import com.prime.common.dto.user.OAuthClientDetailDTO;
import com.prime.common.exception.ValidationException;
import com.prime.common.util.ErrorMessage;
import com.prime.common.vo.user.OAuthClientDetailVO;
import com.prime.user.common.constant.UserErrorCode;
import com.prime.user.component.OAuthClientComponent;
import com.prime.user.model.entity.OAuthClientDetailsEntity;
import com.prime.user.model.mapper.OAuthClientDetailsMapper;
import com.prime.user.service.OAuthClientDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OAuthClientComponentImpl implements OAuthClientComponent {
    @Autowired
    private ErrorMessage errorMessage;

    @Autowired
    private OAuthClientDetailsMapper authClientDetailsMapper;

    @Autowired
    private OAuthClientDetailsService oAuthClientDetailsService;

    public OAuthClientDetailDTO getClientInfo(String clientId) throws ValidationException {
        Optional<OAuthClientDetailsEntity> entityOptional = oAuthClientDetailsService.findById(clientId);
        if (entityOptional.isEmpty()) {
            throw new ValidationException(errorMessage.getError(UserErrorCode.ID_NOT_FOUND,
                    OAuthClientDetailsEntity.Fields.clientId));
        }
        return authClientDetailsMapper.convertToDTO(entityOptional.get());
    }

    public OAuthClientDetailDTO createClient(OAuthClientDetailVO vo) {
        OAuthClientDetailsEntity entity = new OAuthClientDetailsEntity();
        authClientDetailsMapper.mapToEntity(vo, entity);
        entity = oAuthClientDetailsService.create(entity);
        return authClientDetailsMapper.convertToDTO(entity);
    }

    @Override
    public void deleteClient(String clientId) throws ValidationException {
        Optional<OAuthClientDetailsEntity> entityOptional = oAuthClientDetailsService.findById(clientId);
        if (entityOptional.isEmpty()) {
            throw new ValidationException(errorMessage.getError(UserErrorCode.ID_NOT_FOUND,
                    OAuthClientDetailsEntity.Fields.clientId));
        }
        oAuthClientDetailsService.delete(entityOptional.get());
    }
}
