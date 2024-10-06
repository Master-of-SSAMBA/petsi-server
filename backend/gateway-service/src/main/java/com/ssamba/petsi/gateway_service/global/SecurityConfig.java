package com.ssamba.petsi.gateway_service.global;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity.CorsSpec;
import org.springframework.security.config.web.server.ServerHttpSecurity.CsrfSpec;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.util.matcher.PathPatternParserServerWebExchangeMatcher;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http, HeaderFilter headerFilter) {
        http
                .securityMatcher(new PathPatternParserServerWebExchangeMatcher("/api/**"))
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/api/v1/user/signup", "api/v1/user/email-check", "/api/v1/auth/**").permitAll()
                        .pathMatchers("/api/v1/**").authenticated()
                        .anyExchange().permitAll()
                )
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
                .csrf(CsrfSpec::disable)
                .cors(CorsSpec::disable)
                .addFilterAt(headerFilter, SecurityWebFiltersOrder.AUTHORIZATION);
        return http.build();
    }
}
