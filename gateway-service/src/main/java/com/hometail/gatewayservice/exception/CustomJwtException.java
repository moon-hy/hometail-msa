package com.hometail.gatewayservice.exception;

import io.jsonwebtoken.JwtException;

public class CustomJwtException extends JwtException {

    public static JwtException ROLE_NOT_MATCH = new CustomJwtException("Forbidden");

    public CustomJwtException(String message) {
        super(message);
    }

    public CustomJwtException(String message, Throwable cause) {
        super(message, cause);
    }
}
