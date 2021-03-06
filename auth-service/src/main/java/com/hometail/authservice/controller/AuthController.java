package com.hometail.authservice.controller;

import com.hometail.authservice.domain.Account;
import com.hometail.authservice.dto.request.LoginRequestDto;
import com.hometail.authservice.dto.request.SignupRequestDto;
import com.hometail.authservice.service.AccountService;
import com.hometail.authservice.service.RefreshTokenService;
import com.hometail.authservice.utils.CookieProvider;
import com.hometail.authservice.utils.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RequestMapping("/auth-service")
@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AccountService accountService;
    private final JwtProvider jwtProvider;
    private final CookieProvider cookieProvider;

    //
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody @Valid SignupRequestDto dto) {

        accountService.addAccount(dto.toEntity());
        // login 으로 리다이렉트
        return login(new LoginRequestDto(dto.getEmail(), dto.getPassword()));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequestDto dto) {

        // request의 email, password 받은 entity 생성, accessToken 생성
        Account account = accountService.getAccount(dto.toEntity());
        String accessToken = jwtProvider.createJwtAccessToken(account.getId());

        // refreshToken 생성, 쿠키에 저장
        String refreshToken = jwtProvider.createJwtRefreshToken();
        ResponseCookie responseCookie = cookieProvider.createJwtRefreshTokenCookie(refreshToken);

        // refreshToken 저장
        String refreshTokenId = jwtProvider.getClaimsByJwt(refreshToken).getId();
        refreshTokenService.updateRefreshToken(account.getId(), refreshTokenId);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body(jwtProvider.toDto(accessToken));
    }

    @GetMapping("/reissue")
    public ResponseEntity<?> reissue(HttpServletRequest request,
                                     @RequestHeader("X-Authorization-Id") Long accountId) {

        // parse the request
        String refreshToken = cookieProvider.parseRequest(request);

        // 새로운 access token 생성
        String newAccessToken = refreshTokenService.reissueAccessToken(accountId, refreshToken);

        return ResponseEntity.ok()
                .body(jwtProvider.toDto(newAccessToken));
    }

    @DeleteMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("X-Authorization-Id") Long accountId) {

        refreshTokenService.removeRefreshTokenByAccountId(accountId);
        ResponseCookie cookie = cookieProvider.removeJwtRefreshTokenCookie();

        return ResponseEntity.noContent()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .build();
    }

    @GetMapping("/account")
    public ResponseEntity<?> account(@RequestHeader("X-Authorization-Id") Long accontId) {

        Account account = accountService.getAccountById(accontId);
        return ResponseEntity.ok()
                .body(account);
    }
}
