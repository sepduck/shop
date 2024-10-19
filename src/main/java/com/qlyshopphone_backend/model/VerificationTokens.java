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
    private Users users;

    private LocalDateTime expiryDate;

    public VerificationTokens(String token, Users users, LocalDateTime expiryDate) {
        this.token = token;
        this.users = users;
        this.expiryDate = expiryDate;
    }
}
