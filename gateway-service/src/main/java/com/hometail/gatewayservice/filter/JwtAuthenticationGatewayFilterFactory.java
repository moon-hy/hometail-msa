package com.hometail.gatewayservice.filter;

import com.hometail.gatewayservice.dto.TokenUser;
import com.hometail.gatewayservice.exception.CustomHeaderException;
import com.hometail.gatewayservice.exception.CustomJwtException;
import com.hometail.gatewayservice.util.JwtUtils;

import lombok.Setter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

@Component
public class JwtAuthenticationGatewayFilterFactory extends
        AbstractGatewayFilterFactory<JwtAuthenticationGatewayFilterFactory.Config> {

    private static final String ROLE_KEY = "role";
    private static final String ROLE_ADMIN = "ROLE_ADMIN";
    private final JwtUtils jwtUtils;

    public JwtAuthenticationGatewayFilterFactory(JwtUtils jwtUtils) {
        super(Config.class);
        this.jwtUtils = jwtUtils;
    }

    @Setter
    public static class Config {
        private String role;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            String authorization = getAuthorization(request);
            String token = getToken(authorization);

            TokenUser tokenUser = jwtUtils.decode(token);
            checkRole(tokenUser, config.role);

            addAuthorizationHeaders(request, tokenUser);
            return chain.filter(exchange.mutate().request(request).build());
        });
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Collections.singletonList(ROLE_KEY);
    }

    public void checkRole(TokenUser tokenUser, String role) {
        if (!tokenUser.getRole().equals(ROLE_ADMIN) && !role.equals(tokenUser.getRole())) {
            throw CustomJwtException.ROLE_NOT_MATCH;
        }
    }

    private void addAuthorizationHeaders(ServerHttpRequest request, TokenUser tokenUser) {
        request.mutate()
                .header("X-Authorization-Id", tokenUser.getId().toString())
                .build();
    }

    private String getAuthorization(ServerHttpRequest request) {
        try {
            return request.getHeaders().getOrEmpty(HttpHeaders.AUTHORIZATION).get(0);
        } catch (Exception e){
            throw CustomHeaderException.MISSING_AUTHORIZATION;
        }
    }

    public String getToken(String authorization) {

        if (authorization == null || !authorization.startsWith("Bearer ")){
            throw CustomHeaderException.INVALID_AUTHORIZATION;
        }
        return authorization.substring(7);
    }

    private Mono<Void> onError(ServerHttpResponse response, String message, HttpStatus status) {
        response.setStatusCode(status);
        DataBuffer buffer = response.bufferFactory().wrap(message.getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Mono.just(buffer));
    }

}
