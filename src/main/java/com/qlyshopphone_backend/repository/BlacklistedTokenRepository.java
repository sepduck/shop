package com.qlyshopphone_backend.repository;

import com.qlyshopphone_backend.model.BlacklistedTokens;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BlacklistedTokenRepository extends JpaRepository<BlacklistedTokens, Long> {
    @Query("""
            SELECT blt
            FROM BlacklistedTokens blt
            WHERE blt.token = :token
            """)
    Optional<BlacklistedTokens> findByToken(String token);
}
