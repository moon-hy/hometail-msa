package com.hometail.authservice.utils;

import com.hometail.authservice.dto.TokenDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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

    public String createJwtAccessToken(Long id) {
        Claims claims = Jwts.claims().setSubject(id.toString());
        claims.setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRED_TIME));
        claims.setIssuedAt(new Date());
        claims.put("role", "ROLE_USER");

        String accessToken = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .addClaims(claims)
                .signWith(Keys.hmacShaKeyFor(getByteSecret()), SignatureAlgorithm.HS512).compact();

        return accessToken;
    }

    public String createJwtRefreshToken() {
        Claims claims = Jwts.claims().setId(UUID.randomUUID().toString());
        claims.setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRED_TIME));
        claims.setIssuedAt(new Date());

        String refreshToken = Jwts.builder()
                .addClaims(claims)
                .signWith(Keys.hmacShaKeyFor(getByteSecret()), SignatureAlgorithm.HS512).compact();

        return refreshToken;
    }

    private byte[] getByteSecret() {
        return DatatypeConverter.parseBase64Binary(SECRET_KEY);
    }

    public Claims getClaimsByJwt(String jwt) {

        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getByteSecret()).build()
                    .parseClaimsJws(jwt).getBody();
        } catch (ExpiredJwtException exception) {
            return exception.getClaims();
        }
    }

    public Long getExpiresInByJwt(String jwt) {
        return getClaimsByJwt(jwt).getExpiration().getTime();
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
}
