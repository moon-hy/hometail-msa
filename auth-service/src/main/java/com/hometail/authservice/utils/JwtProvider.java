package com.hometail.authservice.utils;

import com.hometail.authservice.domain.Account;
import com.hometail.authservice.dto.TokenDto;
import com.hometail.authservice.exception.InvalidRequestException;
import com.hometail.authservice.service.RefreshTokenService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.util.Date;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class JwtProvider {

    @Value("${token.access.expired-time}")
    private Long ACCESS_TOKEN_EXPIRED_TIME;

    @Value("${token.refresh.expired-time}")
    private Long REFRESH_TOKEN_EXPIRED_TIME;

    @Value("${token.secret-key}")
    private String SECRET_KEY;

    private final RefreshTokenService refreshTokenService;

    public String createJwtAccessToken(Long id) {
        Claims claims = Jwts.claims().setSubject(id.toString());
        claims.setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRED_TIME));
        claims.setIssuedAt(new Date());

        String accessToken = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .addClaims(claims)
                .signWith(Keys.hmacShaKeyFor(getByteSecret(SECRET_KEY)), SignatureAlgorithm.HS512).compact();

        return accessToken;
    }

    public String createJwtRefreshToken() {
        Claims claims = Jwts.claims().setId(UUID.randomUUID().toString());
        claims.setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRED_TIME));
        claims.setIssuedAt(new Date());

        String refreshToken = Jwts.builder()
                .addClaims(claims)
                .signWith(Keys.hmacShaKeyFor(getByteSecret(SECRET_KEY)), SignatureAlgorithm.HS512).compact();

        return refreshToken;
    }

    private byte[] getByteSecret(String secret) {
        return DatatypeConverter.parseBase64Binary(secret);
    }

    public Claims getClaimsByJwt(String jwt) {

        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getByteSecret(SECRET_KEY)).build()
                    .parseClaimsJws(jwt).getBody();
        } catch (ExpiredJwtException exception) {
            return exception.getClaims();
        }
    }

    public String parseRequest(HttpServletRequest request) {

        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            throw InvalidRequestException.NotExistsAccessToken;
        }

        return authorization.substring(7);
    }

    public Date getExpiresInByJwt(String jwt) {
        return getClaimsByJwt(jwt).getExpiration();
    }

    public int getExpiresInSecondsByJwt(String jwt) {
        return (int) getExpiresInByJwt(jwt).getTime() / 1000;
    }

    public String getRefreshTokenIdByJwt(String jwt) {
        return getClaimsByJwt(jwt).getId();
    }

    public Long getAccountIdByJwt(String jwt) {
        return Long.parseLong(getClaimsByJwt(jwt).getSubject());
    }

    public TokenDto toDto(String jwt) {

        return TokenDto.builder()
                .tokenType("Bearer")
                .accessToken(jwt)
                .expiresIn(getExpiresInByJwt(jwt))
                .build();
    }

    public String reissueAccessTokenWithRefreshToken(String accessToken, String refreshToken) {

        String refreshTokenId = getRefreshTokenIdByJwt(refreshToken);

        // 올바른 토큰 값인지 비교를 위해 accessToken 과 refreshToken 의 accountId 가져옴
        Long accountId = getAccountIdByJwt(accessToken);
        Long tokenAccountId = refreshTokenService.getAccountIdByRefreshTokenId(refreshTokenId);

        // accountId 가 다를 경우 exception
        if (!accountId.equals(tokenAccountId)) {
            throw InvalidRequestException.BadRequest;
        }

        // 새로운 access token 생성
        String newAccessToken = createJwtAccessToken(accountId);

        return newAccessToken;
    }
}
