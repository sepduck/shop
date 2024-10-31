package com.qlyshopphone_backend.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class CartIdsRequest {
    private List<Long> cartIds;
}
