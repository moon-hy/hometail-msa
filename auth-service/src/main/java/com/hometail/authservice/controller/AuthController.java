package com.hometail.authservice.controller;

import com.hometail.authservice.domain.Account;
import com.hometail.authservice.dto.request.LoginRequestDto;
import com.hometail.authservice.dto.request.SignupRequestDto;
import com.hometail.authservice.dto.response.RestResponseDto;
import com.hometail.authservice.service.AccountService;
import com.hometail.authservice.service.RefreshTokenService;
import com.hometail.authservice.utils.CookieProvider;
import com.hometail.authservice.utils.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AccountService accountService;
    private final JwtProvider jwtProvider;
    private final CookieProvider cookieProvider;

    //
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid SignupRequestDto dto) {

        accountService.addAccount(dto.toEntity());
        // login 으로 리다이렉트
        return login(new LoginRequestDto(dto.getEmail(), dto.getPassword()));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid LoginRequestDto dto) {

        // request의 email, password 받은 entity 생성, accessToken 생성
        Account account = accountService.getAccount(dto.toEntity());
        String accessToken = jwtProvider.createJwtAccessToken(account.getId());

        // refreshToken 생성, 쿠키에 저장
        String refreshToken = jwtProvider.createJwtRefreshToken();
        ResponseCookie responseCookie = cookieProvider.createJwtRefreshTokenCookie(refreshToken);

        // refreshToken 저장
        String refreshTokenId = jwtProvider.getClaimsByJwt(refreshToken).getId();
        refreshTokenService.addRefreshToken(account.getId(), refreshTokenId);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body(RestResponseDto.builder()
                        .httpStatus(HttpStatus.OK)
                        .data(jwtProvider.toDto(accessToken)).build());
    }

    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(HttpServletRequest request) {

        // parse the request
        String accessToken = jwtProvider.parseRequest(request);
        String refreshToken = cookieProvider.parseRequest(request);

        // 새로운 access token 생성
        String newAccessToken = jwtProvider.reissueAccessTokenWithRefreshToken(accessToken, refreshToken);

        return ResponseEntity.ok()
                .body(RestResponseDto.builder()
                        .httpStatus(HttpStatus.OK)
                        .data(jwtProvider.toDto(newAccessToken)).build());
    }
}
