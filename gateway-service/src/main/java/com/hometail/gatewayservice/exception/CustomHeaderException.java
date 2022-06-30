package com.hometail.gatewayservice.exception;

public class CustomHeaderException extends RuntimeException{

    public static CustomHeaderException MISSING_AUTHORIZATION = new CustomHeaderException("Missing Authorization Exception.");
    public static CustomHeaderException INVALID_AUTHORIZATION = new CustomHeaderException("Invalid Authorization Exception.");

    public CustomHeaderException(String message) {
        super(message);
    }
}
