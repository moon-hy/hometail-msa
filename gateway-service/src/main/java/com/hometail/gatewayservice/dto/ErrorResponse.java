package com.hometail.gatewayservice.dto;

import lombok.Getter;

@Getter
public class ErrorResponse {

    private String detail;

    public ErrorResponse(String detail) {
        this.detail = detail;
    }
}
