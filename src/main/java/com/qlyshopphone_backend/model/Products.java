package com.qlyshopphone_backend.model;

import com.qlyshopphone_backend.model.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private float price;

    private float capitalPrice;

    private Long inventory;

    private float weight;

    @Enumerated(EnumType.STRING)
    private Status status;

    private boolean directSales;

    @ManyToOne
    @JoinColumn(name = "group_product_id")
    private GroupProducts groupProducts;

    @ManyToOne
    @JoinColumn(name = "trademark_id")
    private Trademarks trademarks;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Locations locations;

    @ManyToOne
    @JoinColumn(name = "properties_id")
    private Properties properties;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Categories categories;

    @ManyToOne
    @JoinColumn(name = "unit_id")
    private Units units;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;


}
