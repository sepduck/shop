package com.qlyshopphone_backend.dto.response;

import lombok.Data;

@Data
public class ProductResponse {
    private Long id;
    private String name;
    private String groupProductName;
    private String trademarkName;
    private String categoryName;
    private String unitName;
}
