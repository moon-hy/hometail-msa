package com.hometail.gatewayservice.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hometail.gatewayservice.dto.ErrorResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Slf4j
public class CustomExceptionHandler implements ErrorWebExceptionHandler {

    private String make(ErrorResponse errorResponse) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(errorResponse);
    }

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {

        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        String detail = "Something Wrong: ".concat(ex.getMessage());

        String body = null;

        if (ex instanceof ExpiredJwtException) {
            httpStatus = HttpStatus.UNAUTHORIZED;
            detail = "Expired Token: ".concat(ex.getMessage());
        }
        else if (ex instanceof JwtException) {
            httpStatus = HttpStatus.UNAUTHORIZED;
            detail = "Invalid Token: ".concat(ex.getMessage());
        }

        if (ex instanceof CustomHeaderException) {
            httpStatus = HttpStatus.UNAUTHORIZED;
            detail = "Authorization Header Exception: ".concat(ex.getMessage());
        }

        try {
            body = make(new ErrorResponse(detail));
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }

        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
        exchange.getResponse().setStatusCode(httpStatus);
        return exchange.getResponse().writeWith(Flux.just(buffer));
    }
}
