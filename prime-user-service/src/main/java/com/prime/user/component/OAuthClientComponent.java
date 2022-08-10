package com.prime.user.component;

import com.prime.common.dto.user.OAuthClientDetailDTO;
import com.prime.common.exception.ValidationException;
import com.prime.common.vo.user.OAuthClientDetailVO;

public interface OAuthClientComponent {

    OAuthClientDetailDTO getClientInfo(String clientId) throws ValidationException;

    OAuthClientDetailDTO createClient(OAuthClientDetailVO vo);

    void deleteClient(String clientId) throws ValidationException;
}
