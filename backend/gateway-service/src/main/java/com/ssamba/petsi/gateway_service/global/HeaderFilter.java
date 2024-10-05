package com.ssamba.petsi.gateway_service.global;

import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class HeaderFilter implements WebFilter {
    private static final String USER_ID_CLAIM = "userId";
    private static final String USER_Key_CLAIM = "userKey";
    private static final String X_USER_ID_HEADER = "X-User-Id";
    private static final String X_USER_KEY_HEADER = "X-User-Key";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .filter(authentication -> authentication instanceof JwtAuthenticationToken)
                .map(authentication -> (JwtAuthenticationToken) authentication)
                .map(JwtAuthenticationToken::getToken)
                .map(jwt -> {
                    String userId = jwt.getClaimAsString(USER_ID_CLAIM);
                    String userKey = jwt.getClaimAsString(USER_Key_CLAIM);
                    if (userId != null) {
                        exchange.getRequest().mutate()
                                .header(X_USER_ID_HEADER, userId)
                                .build();
                    }
                    if (userKey != null) {
                        exchange.getRequest().mutate()
                                .header(X_USER_KEY_HEADER, userKey)
                                .build();
                    }
                    return exchange;
                })
                .defaultIfEmpty(exchange)
                .flatMap(chain::filter);
    }
}
