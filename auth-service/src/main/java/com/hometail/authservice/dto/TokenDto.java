package com.hometail.authservice.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TokenDto {

    private String tokenType;
    private String accessToken;
    private String refreshToken;
    private Long expiresIn;
}
