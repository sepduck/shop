package com.qlyshopphone_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Products product;
    @ManyToOne
    @JoinColumn(name = "location_id", nullable = false)
    private Locations location;
    private Long quantity;
    public Inventory(Products product, Long quantity) {
        this.product = product;
        this.quantity = quantity;
    }
}
