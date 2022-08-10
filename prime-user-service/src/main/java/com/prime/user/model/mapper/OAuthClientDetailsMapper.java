package com.prime.user.model.mapper;

import com.prime.common.dto.user.OAuthClientDetailDTO;
import com.prime.common.vo.user.OAuthClientDetailVO;
import com.prime.user.model.entity.OAuthClientDetailsEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class OAuthClientDetailsMapper {

    @Autowired
    private PasswordEncoder passwordEncoder;


    public OAuthClientDetailDTO convertToDTO(final OAuthClientDetailsEntity entity) {
        return OAuthClientDetailDTO.builder()
                .clientId(entity.getClientId())
                .resourceIds(entity.getResourceIds())
                .scope(entity.getScope())
                .authorities(entity.getAuthorities())
                .webServerRedirectUri(entity.getWebServerRedirectUri())
                .accessTokenValidity(entity.getAccessTokenValidity())
                .refreshTokenValidity(entity.getRefreshTokenValidity())
                .authorizedGrantTypes(entity.getAuthorizedGrantTypes())
                .additionalInformation(entity.getAdditionalInformation())
                .autoapprove(entity.getAutoapprove())
                .build();
    }

    public void mapToEntity(final OAuthClientDetailVO vo, final OAuthClientDetailsEntity entity) {
        entity.setClientId(vo.getClientId());
        entity.setClientSecret(passwordEncoder.encode(vo.getClientSecret()));
        entity.setResourceIds(vo.getResourceIds());
        entity.setScope(vo.getScope());
        entity.setAuthorities(vo.getAuthorities());
        entity.setAuthorizedGrantTypes(vo.getAuthorizedGrantTypes());
        entity.setWebServerRedirectUri(vo.getWebServerRedirectUri());
        entity.setAccessTokenValidity(vo.getAccessTokenValidity());
        entity.setRefreshTokenValidity(vo.getRefreshTokenValidity());
        entity.setAdditionalInformation(entity.getAdditionalInformation());
        entity.setAutoapprove(vo.getAutoapprove());
    }
}
