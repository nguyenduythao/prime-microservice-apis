package com.prime.user.controller;

import com.prime.common.dto.Response;
import com.prime.common.dto.user.OAuthClientDetailDTO;
import com.prime.common.exception.ValidationException;
import com.prime.common.vo.user.OAuthClientDetailVO;
import com.prime.user.component.OAuthClientComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class OAuthClientController {

    @Autowired
    private OAuthClientComponent oAuthClientComponent;

    @GetMapping("/client/{clientId}")
    public Response<OAuthClientDetailDTO> getClientInfo(@PathVariable @Valid String clientId) throws ValidationException {
        OAuthClientDetailDTO oAuthClientDetailDTO = oAuthClientComponent.getClientInfo(clientId);
        return new Response<>(oAuthClientDetailDTO);
    }

    @PostMapping("/client")
    public Response<OAuthClientDetailDTO> createClient(@RequestBody @Valid OAuthClientDetailVO oAuthClientDetailVO) throws ValidationException {
        OAuthClientDetailDTO oAuthClientDetailDTO = oAuthClientComponent.createClient(oAuthClientDetailVO);
        return new Response<>(oAuthClientDetailDTO);
    }

    @DeleteMapping("/client/{clientId}")
    public Response<?> createClient(@PathVariable @Valid String clientId) throws ValidationException {
        oAuthClientComponent.deleteClient(clientId);
        return new Response<>();
    }
}
