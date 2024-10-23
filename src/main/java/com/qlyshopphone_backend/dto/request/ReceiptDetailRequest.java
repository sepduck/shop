package com.qlyshopphone_backend.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ReceiptDetailRequest {
    private Long productId;
    private Long quantity;
    private BigDecimal purchasePrice;
}
