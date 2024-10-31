package com.qlyshopphone_backend.model;

import com.qlyshopphone_backend.model.enums.Status;
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
public class ProductVariants {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Products product;
    @ManyToOne
    @JoinColumn(name = "color_id")
    private ColorAttributes color;
    @ManyToOne
    @JoinColumn(name = "size_id")
    private SizeAttributes size;
    private BigDecimal price;
    private Long stock;
    @Enumerated(EnumType.STRING)
    private Status status;

    public ProductVariants(Products product, ColorAttributes color, SizeAttributes size, BigDecimal price, Long stock, Status status) {
        this.product = product;
        this.color = color;
        this.size = size;
        this.price = price;
        this.stock = stock;
        this.status = status;
    }
}
