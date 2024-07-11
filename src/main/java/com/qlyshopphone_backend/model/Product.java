package com.qlyshopphone_backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Integer productId;

    @Column(name = "product_name", length = 50, nullable = false)
    private String productName;

    @Column(name = "price", precision = 15, scale = 2, nullable = false)
    private BigDecimal price;

    @Column(name = "capital_price", precision = 15, scale = 2, nullable = false)
    private BigDecimal capitalPrice;

    @Column(name = "inventory", nullable = false)
    private Integer inventory;
    @ManyToOne
    @JoinColumn(name = "group_product_id", nullable = false)
    private GroupProduct groupProduct;

    @ManyToOne
    @JoinColumn(name = "trademark_id", nullable = false)
    private Trademark trademark;

    @ManyToOne
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    @Column(name = "weight", precision = 15, scale = 2, nullable = false)
    private BigDecimal weight;

    @Column(name = "delete_product", columnDefinition = "boolean default false")
    private boolean deleteProduct = false;

    @Column(name = "direct_sales")
    private boolean directSales;

    @ManyToOne
    @JoinColumn(name = "properties_id", nullable = false)
    private Properties properties;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "unit_id", nullable = false)
    private Unit unit;

    @Lob
    @Column(name = "file", columnDefinition = "MEDIUMBLOB")
    private byte[] file;
}
