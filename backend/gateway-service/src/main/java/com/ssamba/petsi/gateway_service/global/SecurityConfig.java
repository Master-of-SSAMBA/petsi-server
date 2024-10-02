package com.ssamba.petsi.gateway_service.global;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.util.matcher.PathPatternParserServerWebExchangeMatcher;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/", "/assets/**", "/fonts/**", "/images/**", "/index.html", "/Symbol.ico").permitAll()
                        .pathMatchers("/api/v1/user/signup").permitAll()
                        .pathMatchers("/api/v1/**").authenticated()
                        .anyExchange().permitAll()
                )
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .cors(ServerHttpSecurity.CorsSpec::disable);
//                .addFilterAt(addHeaderFilter(), SecurityWebFiltersOrder.AUTHORIZATION);
        return http.build();
    }

    @Bean
    public WebFilter addHeaderFilter() {
        return (ServerWebExchange exchange, WebFilterChain chain) ->
                exchange.getPrincipal()
                        .flatMap(principal -> {
                            if (principal instanceof JwtAuthenticationToken jwtToken) {
                                String userId = jwtToken.getToken().getClaimAsString("user_id");
                                String userKey = jwtToken.getToken().getClaimAsString("user_key");
                                exchange.getRequest().mutate()
                                        .header("X-User-Id", userId)
                                        .header("X-User-Key", userKey)
                                        .build();
                            }
                            return chain.filter(exchange);
                        });
    }
}
