package com.qlyshopphone_backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity

public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "supplier_id")
    private Long supplierId;

    @Column(name = "supplier_name", length = 50, nullable = false)
    private String supplierName;

    @Column(name = "phone_number", length = 11, nullable = false)
    private String phoneNumber;

    @Column(name = "address", length = 250, nullable = false)
    private String address;

    @Column(name = "email", length = 100, nullable = false)
    private String email;

    @Column(name = "company", length = 250, nullable = false)
    private String company;

    @Column(name = "tax_code", length = 10, nullable = false)
    private String taxCode;

    @Column(name = "delete_supplier", columnDefinition = "boolean default false")
    private boolean deleteSupplier = false;

    @ManyToOne
    @JoinColumn(name = "group_supplier_id", nullable = false)
    private GroupSupplier groupSupplier;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}
