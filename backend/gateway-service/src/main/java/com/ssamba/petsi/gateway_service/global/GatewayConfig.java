package com.ssamba.petsi.gateway_service.global;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("user-service", r -> r.path("/api/v1/user/**")
                        .filters(f -> f
                                .tokenRelay()
                                .filter(addHeadersFilter()))
                        .uri("http://user-service:9001"))

                .route("pet-service", r -> r.path("/api/v1/pet/**")
                        .filters(f -> f
                                .tokenRelay()
                                .filter(addHeadersFilter()))
                        .uri("http://pet-service:9002"))

                .route("account-service", r -> r.path("/api/v1/account/**")
                        .filters(f -> f
                                .tokenRelay()
                                .filter(addHeadersFilter()))
                        .uri("http://account-service:9003"))

                .route("picture-service", r -> r.path("/api/v1/picture/**")
                        .filters(f -> f
                                .tokenRelay()
                                .filter(addHeadersFilter()))
                        .uri("http://picture-service:9004"))
                .route("expense-service", r -> r.path("/api/v1/expense/**")
                        .filters(f -> f
                                .tokenRelay()
                                .filter(addHeadersFilter()))
                        .uri("http://expense-service:9005"))

                .route("schedule-service", r -> r.path("/api/v1/schedule/**")
                        .filters(f -> f
                                .tokenRelay()
                                .filter(addHeadersFilter()))
                        .uri("http://schedule-service:9006"))

                .route("notification-service", r -> r.path("/api/v1/notification/**")
                        .filters(f -> f
                                .tokenRelay()
                                .filter(addHeadersFilter()))
                        .uri("http://notification-service:9007"))
                .build();
    }

    private GatewayFilter addHeadersFilter() {
        return (exchange, chain) -> exchange.getPrincipal()
                .flatMap(principal -> {
                    if (principal instanceof JwtAuthenticationToken) {
                        Jwt jwt = ((JwtAuthenticationToken) principal).getToken();
                        if (jwt != null) {
                            String userId = jwt.getClaimAsString("user_id");
                            String userKey = jwt.getClaimAsString("user_key");

                            exchange.getRequest().mutate()
                                    .header("X-User-Id", userId)
                                    .header("X-User-Key", userKey)
                                    .build();
                        }
                    }
                    return chain.filter(exchange);
                });
    }
}