package com.hometail.authservice.dto;

import com.hometail.authservice.exception.ErrorCode;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;

@Getter
public class ErrorResponse {

    private LocalTime timestamp;
    private int status;
    private String message;
    private String details;

    @Builder
    public ErrorResponse(ErrorCode errorCode, String details) {
        this.timestamp = LocalTime.now();
        this.status = errorCode.getStatus();
        this.message = errorCode.getMessage();
        this.details = details;
    }
}
