package com.hometail.authservice.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Builder
@Getter
public class TokenDto {

    private String tokenType;
    private String accessToken;
    private String refreshToken;
    private Date expiresIn;
}
