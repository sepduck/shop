package com.qlyshopphone_backend.dto.request;

import lombok.Data;

@Data
public class AddToCartRequest {
    private Long productVariantId;
    private Long quantity;
}