package com.hometail.authservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonResponseDto {

    private int status;
    private String message;
    private Object data;

    public CommonResponseDto(int status, String message, Object data) {

        this.status = status;
        this.message = message;
        this.data = data;
    }
}
