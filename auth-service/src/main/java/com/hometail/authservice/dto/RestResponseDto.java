package com.hometail.authservice.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class RestResponseDto {

    private final int status;
    private final String message;
    private final Object data;

    @Builder
    public RestResponseDto(HttpStatus httpStatus, Object data) {

        this.status = httpStatus.value();
        this.message = httpStatus.name();
        this.data = data;
    }
}
