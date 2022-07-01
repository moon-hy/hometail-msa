package com.hometail.userservice.dto.response;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
public class RestResponseDto<T> {

    private int status;
    private String message;
    private T data;

    @Builder
    public RestResponseDto(HttpStatus httpStatus, T data) {

        this.status = httpStatus.value();
        this.message = httpStatus.name();
        this.data = data;
    }
}
