package com.qlyshopphone_backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class GroupSupplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_supplier_id")
    private Long groupSupplierId;

    @Column(name = "group_supplier_name", length = 50, nullable = false)
    private String  groupSupplierName;

    @Column(name = "note", length = 200)
    private String  note;
}
