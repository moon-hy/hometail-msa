package com.hometail.authservice.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ErrorResponseDto {

    private final int status;
    private final String message;
    private final String detail;

    @Builder
    public ErrorResponseDto(HttpStatus httpStatus, Exception exception) {

        this.status = httpStatus.value();
        this.message = httpStatus.name();
        this.detail = exception.getMessage();
    }
}
