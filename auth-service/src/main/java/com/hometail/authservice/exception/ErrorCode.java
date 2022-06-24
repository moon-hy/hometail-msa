package com.hometail.authservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    INVALID_INPUT_VALUE(400, "Invalid Input Value"),
    METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    NOT_FOUND(404, "Not Found"),

    EMAIL_DUPLICATED(400, "Email is Duplicated"),
    LOGIN_INPUT_INVALID(400, "Invalid Login Input Value"),
    ;

    private final int status;
    private final String message;
}
