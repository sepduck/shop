package com.qlyshopphone_backend.model;

import com.google.type.Decimal;
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
public class ReceiptDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "receipt_id", nullable = false)
    private Receipts receipt;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Products product;

    private Long quantity;

    private BigDecimal purchasePrice;

    private BigDecimal total;

    public ReceiptDetails(Receipts receipt, Products product, Long quantity, BigDecimal purchasePrice, BigDecimal total) {
        this.receipt = receipt;
        this.product = product;
        this.quantity = quantity;
        this.purchasePrice = purchasePrice;
        this.total = total;
    }
}
