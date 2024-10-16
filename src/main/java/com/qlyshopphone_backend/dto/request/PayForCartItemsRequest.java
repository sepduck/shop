package com.qlyshopphone_backend.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class PayForCartItemsRequest {
    private List<Long> cartIds;
    private Long customerId;
}
