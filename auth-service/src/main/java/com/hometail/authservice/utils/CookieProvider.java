package com.hometail.authservice.utils;

import com.hometail.authservice.exception.InvalidRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@RequiredArgsConstructor
@Component
public class CookieProvider {

    private final JwtProvider jwtProvider;

    @Value("${token.refresh.name}")
    private String REFRESH_TOKEN_NAME;

    public ResponseCookie createJwtRefreshTokenCookie(String refreshToken) {

//        Cookie cookie = new Cookie(REFRESH_TOKEN_NAME, refreshToken);
//        cookie.setHttpOnly(true);
//        cookie.setSecure(false);
//        cookie.setMaxAge(jwtProvider.getExpiresInSecondsByJwt(refreshToken));
//        cookie.setPath("/");

        return ResponseCookie.from(REFRESH_TOKEN_NAME, refreshToken)
                .httpOnly(true)
                .secure(false)
                .maxAge(jwtProvider.getExpiresInSecondsByJwt(refreshToken))
                .path("/")
                .build();
    }

    public ResponseCookie removeJwtRefreshTokenCookie() {

        return ResponseCookie.from(REFRESH_TOKEN_NAME, null)
                .maxAge(0)
                .path("/")
                .build();
    }

    public String parseRequest(HttpServletRequest request) {

        return Arrays.stream(request.getCookies())
                .filter(c -> c.getName().equals(REFRESH_TOKEN_NAME))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() -> InvalidRequestException.NotExistsRefreshToken);
    }
}
