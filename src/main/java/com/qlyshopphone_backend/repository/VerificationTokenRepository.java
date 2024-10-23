package com.qlyshopphone_backend.repository;

import com.qlyshopphone_backend.model.Users;
import com.qlyshopphone_backend.model.VerificationTokens;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationTokens, Long> {
    Optional<VerificationTokens> findByToken(String token);

    Optional<VerificationTokens> findByUser(Users user);
}
