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
    private Long productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "capital_price")
    private BigDecimal capitalPrice;

    @Column(name = "inventory")
    private Long inventory;

    @ManyToOne
    @JoinColumn(name = "group_product_id")
    private GroupProduct groupProduct;

    @ManyToOne
    @JoinColumn(name = "trademark_id")
    private Trademark trademark;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @Column(name = "weight")
    private BigDecimal weight;

    @Column(name = "delete_product")
    private boolean deleteProduct;

    @Column(name = "direct_sales")
    private boolean directSales;

    @ManyToOne
    @JoinColumn(name = "properties_id")
    private Properties properties;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "unit_id")
    private Unit unit;

    @Lob
    @Column(name = "file")
    private byte[] file;
}
