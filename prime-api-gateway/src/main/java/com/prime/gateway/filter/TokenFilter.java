package com.prime.gateway.filter;

import lombok.Getter;
import lombok.Setter;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class TokenFilter extends AbstractGatewayFilterFactory<TokenFilter.Config> {

    private final String MOBILE_PATTERN = "Mobile|iP(hone|od|ad)|Android";
    private final String TOKEN_PARAM = "token";
    private final String CONNECTION_CLOSE = "close";

    public TokenFilter() {
        super(Config.class);
    }

    private Mono<Void> onError(ServerWebExchange exchange, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);

        return response.setComplete();
    }

    public Mono<Void> send301Redirect(ServerWebExchange exchange, String newUrl) {
        ServerHttpResponse response = exchange.getResponse();
        HttpHeaders responseHeaders = exchange.getResponse().getHeaders();

        responseHeaders.set(HttpHeaders.LOCATION, newUrl);
        responseHeaders.setConnection(CONNECTION_CLOSE);
        response.setStatusCode(HttpStatus.MOVED_PERMANENTLY);

        return response.setComplete();
    }

    private boolean isUserAgentMobile(String userAgentHeader) {
        Pattern pattern = Pattern.compile(MOBILE_PATTERN);
        Matcher matcher = pattern.matcher(userAgentHeader);
        boolean matchFound = matcher.find();
        return matchFound;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            if (!request.getHeaders().containsKey(HttpHeaders.USER_AGENT.toLowerCase())) {
                return this.onError(exchange, HttpStatus.FORBIDDEN);
            }

            String userAgentHeader = request.getHeaders().get(HttpHeaders.USER_AGENT.toLowerCase()).get(0);
            String token = request.getQueryParams().getFirst(TOKEN_PARAM);
            String uri;

            if (this.isUserAgentMobile(userAgentHeader)) {
                uri = config.getMobileUrl() + token;
            } else {
                uri = config.getWebUrl() + token;
            }

            return send301Redirect(exchange, uri);
        };
    }

    @Getter
    @Setter
    public static class Config {
        private String mobileUrl;
        private String webUrl;
    }
}
