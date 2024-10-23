package com.qlyshopphone_backend.service.impl;

import com.qlyshopphone_backend.config.jwt.JwtProvider;
import static com.qlyshopphone_backend.constant.ErrorMessage.*;
import com.qlyshopphone_backend.model.BlacklistedTokens;
import com.qlyshopphone_backend.repository.BlacklistedTokenRepository;
import com.qlyshopphone_backend.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    private final BlacklistedTokenRepository blacklistedTokenRepository;
    private final JwtProvider jwtProvider;

    @Override
    public boolean blacklistToken(String token, LocalDateTime expiryDate) {
        if (!jwtProvider.validateToken(token)){
            throw new IllegalArgumentException(THE_TOKEN_IS_INVALID_OR_HAS_EXPIRED);
        }
        if (isTokenBlacklisted(token)){
            throw new IllegalArgumentException(TOKEN_HAS_BEEN_BLACKLISTED);
        }
        BlacklistedTokens blacklistedTokens = new BlacklistedTokens();
        blacklistedTokens.setToken(token);
        blacklistedTokens.setExpiryDate(expiryDate);
        blacklistedTokenRepository.save(blacklistedTokens);
        return true;
    }

    @Override
    public boolean isTokenBlacklisted(String token) {
        return blacklistedTokenRepository.findByToken(token).isPresent();
    }

    @Override
    public void cleanupExpiredTokens() {
        blacklistedTokenRepository.deleteAll(
                blacklistedTokenRepository.findAll().stream()
                        .filter(token -> token.getExpiryDate().isBefore(LocalDateTime.now()))
                        .toList()
        );
    }
}
