package com.prime.auth.common.config.security;

import com.prime.auth.model.dto.CustomUserDetails;
import com.prime.auth.service.UserDetailsServiceImpl;
import com.prime.common.dto.user.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.DefaultThrowableAnalyzer;
import org.springframework.security.oauth2.common.util.OAuth2Utils;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpointAuthenticationFilter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.util.ThrowableAnalyzer;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
public class CustomAuthenticationFilter extends TokenEndpointAuthenticationFilter {

    private final UserDetailsServiceImpl userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final OAuth2RequestFactory oAuth2RequestFactory;

    private final AuthenticationEntryPoint authenticationEntryPoint;

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager, AuthenticationEntryPoint authenticationEntryPoint,
                                      OAuth2RequestFactory oAuth2RequestFactory, UserDetailsServiceImpl userDetailsService) {
        super(authenticationManager, oAuth2RequestFactory);
        this.userDetailsService = userDetailsService;
        this.oAuth2RequestFactory = oAuth2RequestFactory;
        this.authenticationManager = authenticationManager;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) req;
        final HttpServletResponse response = (HttpServletResponse) res;
        try {
            Authentication credentials = extractCredentials(request);
            if (credentials != null) {
                Authentication authResult = authenticationManager.authenticate(credentials);
                Authentication clientAuth = SecurityContextHolder.getContext().getAuthentication();
                if (clientAuth == null) {
                    throw new BadCredentialsException(
                            "No client authentication found. Remember to put a filter upstream of the TokenEndpointAuthenticationFilter.");
                }
                Map<String, String> map = getSingleValueMap(request);
                map.put(OAuth2Utils.CLIENT_ID, clientAuth.getName());
                AuthorizationRequest authorizationRequest = oAuth2RequestFactory.createAuthorizationRequest(map);
                authorizationRequest.setScope(getScope(request));
                if (clientAuth.isAuthenticated()) {
                    // Ensure the OAuth2Authentication is authenticated
                    authorizationRequest.setApproved(true);
                }
                OAuth2Request storedOAuth2Request = oAuth2RequestFactory.createOAuth2Request(authorizationRequest);
                SecurityContextHolder.getContext().setAuthentication(
                        new OAuth2Authentication(storedOAuth2Request, authResult));
                onSuccessfulAuthentication(request, response, authResult);
            }

        } catch (AuthenticationException failed) {
            SecurityContextHolder.clearContext();
            onUnsuccessfulAuthentication(request, response, failed);
            authenticationEntryPoint.commence(request, response, failed);
            return;
        }
        chain.doFilter(request, response);
    }

    private Map<String, String> getSingleValueMap(HttpServletRequest request) {
        Map<String, String> map = new HashMap<String, String>();
        Map<String, String[]> parameters = request.getParameterMap();
        for (String key : parameters.keySet()) {
            String[] values = parameters.get(key);
            map.put(key, values != null && values.length > 0 ? values[0] : null);
        }
        return map;
    }

    private Set<String> getScope(HttpServletRequest request) {
        return OAuth2Utils.parseParameterList(request.getParameter("scope"));
    }

    @Override
    public void onSuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                           Authentication authResult) throws IOException {
        if (authResult.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails customUserDetails = (CustomUserDetails) authResult.getPrincipal();
            UserDTO userDTO = customUserDetails.getUser();
            if (userDTO.getLockedTime() != null || userDTO.getFailedAttempt() > 0) {
                userDetailsService.resetFailedAttempts(userDTO.getUsername());
            }
        }
    }

    @Override
    public void onUnsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                             AuthenticationException failed) throws IOException {
        ThrowableAnalyzer throwableAnalyzer = new DefaultThrowableAnalyzer();
        Throwable[] causeChain = throwableAnalyzer.determineCauseChain(failed);
        Exception exception = (BadCredentialsException) throwableAnalyzer.getFirstThrowableOfType(BadCredentialsException.class, causeChain);
        if (exception instanceof BadCredentialsException) {
            String username = request.getParameter("username");
            UserDTO user = userDetailsService.findByUsernameOrEmail(username);
            if (user != null) {
                userDetailsService.increaseFailedAttempts(user);
            }
        }
    }
}
