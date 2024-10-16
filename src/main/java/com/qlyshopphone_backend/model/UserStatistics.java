package com.qlyshopphone_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_statistics")
public class UserStatistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_statistics_id")
    private Long userStatisticsId;

    @OneToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @Column(name = "total_amount_paid")
    private BigDecimal totalAmountPaid;

    @Column(name = "total_item_bought")
    private Long totalItemBought;

    public UserStatistics(Users user) {
        this.user = user;
    }
}
