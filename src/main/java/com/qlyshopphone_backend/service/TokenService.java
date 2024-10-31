package com.qlyshopphone_backend.service;

import java.time.LocalDateTime;

public interface TokenService {
    boolean blacklistToken(String token, LocalDateTime expiryDate);

    boolean isTokenBlacklisted(String token);

    void cleanupExpiredTokens();
}
