package com.hometail.authservice.service;

import com.hometail.authservice.domain.RefreshToken;
import com.hometail.authservice.exception.InvalidRequestException;
import com.hometail.authservice.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public String addRefreshToken(Long accountId, String tokenId) {

        return refreshTokenRepository.save(RefreshToken.builder()
                .tokenId(tokenId)
                .accountId(accountId).build()).getTokenId();
    }

    public RefreshToken getRefreshTokenById(String tokenId) {

        return refreshTokenRepository.findByTokenId(tokenId)
                .orElseThrow(() -> InvalidRequestException.NotExistsRefreshToken);

    }

    public Long getAccountIdByRefreshTokenId(String tokenId) {

        return getRefreshTokenById(tokenId).getAccountId();
    }
}
