package com.hometail.authservice.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RestResponse {

    private String code;
    private String message;
    private Object data;
}
