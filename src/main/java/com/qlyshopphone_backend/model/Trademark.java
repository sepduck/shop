package com.qlyshopphone_backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Trademark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trademark_id")
    private Integer trademarkId;

    @Column(name = "trademark_name", length = 50, nullable = false)
    private String trademarkName;
}
