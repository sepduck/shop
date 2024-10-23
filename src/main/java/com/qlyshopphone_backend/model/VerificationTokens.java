package com.qlyshopphone_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class VerificationTokens {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    @OneToOne(targetEntity = Users.class)
    @JoinColumn(name = "user_id")
    private Users user;

    private LocalDateTime expiresAt;

    public VerificationTokens(String token, Users user, LocalDateTime expiryDate) {
        this.token = token;
        this.user = user;
        this.expiresAt = expiryDate;
    }
}
