package com.ssamba.petsi.gateway_service.global;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class HeaderFilter implements WebFilter {

    private static final String USER_ID_CLAIM = "userId";
    private static final String USER_KEY_CLAIM = "userKey";
    private static final String X_USER_ID_HEADER = "X-User-Id";
    private static final String X_USER_KEY_HEADER = "X-User-Key";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return ReactiveSecurityContextHolder.getContext()
                .doOnNext(context -> log.debug("Security Context found"))
                .map(SecurityContext::getAuthentication)
                .doOnNext(auth -> log.debug("Authentication type: {}", auth.getClass().getSimpleName()))
                .filter(authentication -> authentication instanceof JwtAuthenticationToken)
                .map(authentication -> (JwtAuthenticationToken) authentication)
                .map(JwtAuthenticationToken::getToken)
                .map(jwt -> {
                    String userId = jwt.getClaimAsString(USER_ID_CLAIM);
                    String userKey = jwt.getClaimAsString(USER_KEY_CLAIM);

                    log.debug("Extracted userId: {}, userKey: {}", userId, userKey);
                    
                    ServerHttpRequest request = exchange.getRequest().mutate()
                            .headers(headers -> {
                                if (userId != null) {
                                    headers.set(X_USER_ID_HEADER, userId);
                                }
                                if (userKey != null) {
                                    headers.set(X_USER_KEY_HEADER, userKey);
                                }
                            })
                            .build();

                    log.debug("Set headers - X-User-Id: {}, X-User-Key: {}",
                            request.getHeaders().getFirst(X_USER_ID_HEADER),
                            request.getHeaders().getFirst(X_USER_KEY_HEADER));

                    return exchange.mutate().request(request).build();
                })
                .defaultIfEmpty(exchange)
                .doOnNext(modifiedExchange -> {
                    HttpHeaders headers = modifiedExchange.getRequest().getHeaders();
                    log.debug("Final headers - X-User-Id: {}, X-User-Key: {}",
                            headers.getFirst(X_USER_ID_HEADER),
                            headers.getFirst(X_USER_KEY_HEADER));
                })
                .flatMap(chain::filter);
    }
}
