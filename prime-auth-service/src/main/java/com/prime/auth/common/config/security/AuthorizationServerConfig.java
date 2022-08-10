package com.prime.auth.common.config.security;

import com.prime.auth.service.CustomClientDetailsService;
import com.prime.auth.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import javax.sql.DataSource;
import java.security.KeyPair;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private final DataSource dataSource;

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    @Value("${spring.security.jwt.key.store}")
    private String keyStore;

    @Value("${spring.security.jwt.key.store-password}")
    private String keyStorePassword;

    @Value("${spring.security.jwt.key.pair-alias}")
    private String keyPairAlias;

    @Value("${spring.security.jwt.key.pair-password}")
    private String keyPairPassword;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    private final TokenStore tokenStore;

    @Autowired
    public AuthorizationServerConfig(DataSource dataSource,
                                     AuthenticationManager authenticationManager,
                                     PasswordEncoder passwordEncoder,
                                     @Qualifier("redisTokenStore") TokenStore tokenStore) {
        this.dataSource = dataSource;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.tokenStore = tokenStore;
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        DefaultOAuth2RequestFactory oAuth2RequestFactory = new DefaultOAuth2RequestFactory(new CustomClientDetailsService(dataSource));
        CustomAuthenticationFilter filter = new CustomAuthenticationFilter(authenticationManager, oAuth2RequestFactory, userDetailsService);

        security
                // Allow form authentication
                .allowFormAuthenticationForClients()
                // After authentication, you can access /oauth/token_key to obtain the token encryption public key
                .tokenKeyAccess("permitAll()")
                // Check token access /oauth/check_token
                .checkTokenAccess("isAuthenticated()")
                .addTokenEndpointAuthenticationFilter(filter);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // Use custom ClientDetails to add cache support
        CustomClientDetailsService clientDetailsService = new CustomClientDetailsService(dataSource);
        clientDetailsService.setPasswordEncoder(passwordEncoder);
        clients.withClientDetails(clientDetailsService);
        clients.jdbc(this.dataSource);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
        List<TokenEnhancer> delegates = new ArrayList<>();

        delegates.add(tokenEnhancer());
        delegates.add(accessTokenConverter());

        // Configure JWT content enhancement
        enhancerChain.setTokenEnhancers(delegates);
        endpoints.authenticationManager(authenticationManager) // Enable password mode authorization
                .userDetailsService(userDetailsService)
                .accessTokenConverter(accessTokenConverter())
                .tokenEnhancer(enhancerChain)
                .tokenStore(tokenStore);
    }

    /**
     * Token converter
     * The default is uuid format, we specify the token format here as jwt
     *
     * @return
     */
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new CustomJwtAccessTokenConverter();
        // Sign the token using an asymmetric encryption algorithm
        converter.setKeyPair(keyPair());
        return converter;
    }

    @Bean
    public KeyPair keyPair() {
        // Get the key pair from the certificate jwt.jks in the classpath directory
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource(keyStore),
                keyStorePassword.toCharArray());
        return keyStoreKeyFactory.getKeyPair(keyPairAlias, keyPairPassword.toCharArray());
    }

    /**
     * JWT content enhancer, used to extend JWT content, can save user data
     *
     * @return
     */
    @Bean
    public TokenEnhancer tokenEnhancer() {
        return (oAuth2AccessToken, oAuth2Authentication) -> oAuth2AccessToken;
    }

}
