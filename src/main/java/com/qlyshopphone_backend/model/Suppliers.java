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
public class Suppliers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String phoneNumber;

    private String address;

    private String email;

    private String company;

    private String taxCode;

    private Status status;

    @ManyToOne
    @JoinColumn(name = "group_supplier_id")
    private GroupSuppliers groupSuppliers;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Products products;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
