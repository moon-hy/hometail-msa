package com.hometail.authservice.service;

import com.hometail.authservice.domain.RefreshToken;
import com.hometail.authservice.exception.InvalidRequestException;
import com.hometail.authservice.repository.RefreshTokenRepository;
import com.hometail.authservice.utils.CookieProvider;
import com.hometail.authservice.utils.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtProvider jwtProvider;
    private final CookieProvider cookieProvider;

    public String updateRefreshToken(Long accountId, String tokenId) {
        RefreshToken refreshToken = refreshTokenRepository.findByAccountId(accountId)
                .orElse(RefreshToken.builder()
                        .accountId(accountId).build());
        refreshToken.setTokenId(tokenId);
        return refreshTokenRepository.save(refreshToken).getTokenId();

//        return refreshTokenRepository.save(RefreshToken.builder()
//                .tokenId(tokenId)
//                .accountId(accountId).build()).getTokenId();
    }

    @Transactional
    public void removeRefreshTokenByAccountId(Long accountId) {

        RefreshToken refreshToken = refreshTokenRepository.findByAccountId(accountId)
                .orElseThrow(() -> InvalidRequestException.NotExistsRefreshTokenDb);

        refreshTokenRepository.delete(refreshToken);
    }

    @Transactional
    public String reissueAccessToken(Long accountId, String refreshToken) {

        String refreshTokenId = jwtProvider.getRefreshTokenIdByJwt(refreshToken);

        // 올바른 토큰 값인지 비교를 위해 accessToken 과 refreshToken 의 accountId 가져옴
        Long tokenAccountId = refreshTokenRepository.findByTokenId(refreshTokenId)
                .orElseThrow(() -> InvalidRequestException.NotExistsRefreshTokenDb).getAccountId();

        // accountId 가 다를 경우 exception
        if (!accountId.equals(tokenAccountId)) {
            throw InvalidRequestException.BadRequest;
        }

        // 새로운 access token 생성
        return jwtProvider.createJwtAccessToken(accountId);
    }
}
