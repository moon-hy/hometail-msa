package com.hometail.gatewayservice.config;

import com.hometail.gatewayservice.exception.CustomExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .build();
    }

    @Bean
    public ErrorWebExceptionHandler exceptionHandler() {
        return new CustomExceptionHandler();
    }
}