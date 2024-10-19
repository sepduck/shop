package com.qlyshopphone_backend.dto.response;

import lombok.Data;

@Data
public class ProductResponse {
    private Long id;
    private String name;
    private float price;
    private float capitalPrice;
    private Long inventory;
    private float weight;
    private boolean directSales;
    private String groupProductName;
    private String trademarkName;
    private String locationName;
    private String propertiesName;
    private String categoryName;
    private String unitName;
}
