package com.qlyshopphone_backend.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductVariantRequest {
    private Long productId;
    private Long colorId;
    private Long sizeId;
    private BigDecimal price;
    private Long stock;

}
