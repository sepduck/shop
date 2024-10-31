package com.qlyshopphone_backend.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemRequest {
    private Long productVariantId;
    private Long quantity;
    private BigDecimal price;
}
