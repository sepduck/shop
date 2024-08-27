package com.qlyshopphone_backend.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PurchaseDTO {
    private Long purchaseId;
    private Long userId;
    private Long customerInfoId;
    private BigDecimal totalPrice;
    private Long totalAmount;
    private LocalDateTime purchaseDate;
}
