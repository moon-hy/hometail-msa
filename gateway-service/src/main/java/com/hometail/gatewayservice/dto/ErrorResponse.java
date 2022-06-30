package com.hometail.gatewayservice.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ErrorResponse {

    private int status;
    private String message;
    private String detail;

    @Builder
    public ErrorResponse(HttpStatus httpStatus, String detail) {
        this.status = httpStatus.value();
        this.message = httpStatus.name();
        this.detail = detail;
    }
}
