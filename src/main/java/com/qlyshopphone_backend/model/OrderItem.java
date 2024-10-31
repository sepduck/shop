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
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "product_variant_id")
    private ProductVariants productVariant;
    private Long quantity;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Orders order;
    private BigDecimal price;
    public OrderItem(ProductVariants productVariant, Long quantity, Orders order, BigDecimal price) {
        this.productVariant = productVariant;
        this.quantity = quantity;
        this.order = order;
        this.price = price;
    }
}
