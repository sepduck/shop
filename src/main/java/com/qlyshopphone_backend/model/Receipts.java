package com.qlyshopphone_backend.model;

import com.google.type.Decimal;
import com.qlyshopphone_backend.model.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Receipts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Suppliers supplier;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Locations location;

    private BigDecimal totalValue;

    private Status status;

    private Long createdBy;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Receipts(Suppliers supplier, Locations location, Status status, Long createdBy) {
        this.supplier = supplier;
        this.location = location;
        this.status = status;
        this.createdBy = createdBy;
    }
}
