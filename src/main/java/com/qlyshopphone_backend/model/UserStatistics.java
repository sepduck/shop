package com.qlyshopphone_backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
public class UserStatistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_statistics_id")
    private Long userStatisticsId;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @Column(name = "total_amount_paid", precision = 15, scale = 2, nullable = false)
    private BigDecimal totalAmountPaid;

    @Column(name = "total_item_bought", nullable = false)
    private Long totalItemBought;
}
