package com.qlyshopphone_backend.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PurchaseDTO {
    private Integer purchaseId;
    private Integer userId;
    private Integer customerInfoId;
    private BigDecimal totalPrice;
    private Integer totalAmount;
    private LocalDateTime purchaseDate;
}
