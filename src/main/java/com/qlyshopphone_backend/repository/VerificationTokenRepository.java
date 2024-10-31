package com.qlyshopphone_backend.repository;

import com.qlyshopphone_backend.model.Users;
import com.qlyshopphone_backend.model.VerificationTokens;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationTokens, Long> {
    @Query("""
            SELECT vt
            FROM VerificationTokens vt
            WHERE vt.token = :token
            """)
    Optional<VerificationTokens> findByToken(String token);

    @Query("""
            SELECT vt
            FROM VerificationTokens vt
            WHERE vt.user.id = :userId
            """)
    Optional<VerificationTokens> findByUserId(Long userId);
}
