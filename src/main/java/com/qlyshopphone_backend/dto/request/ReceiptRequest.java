package com.qlyshopphone_backend.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class ReceiptRequest {
    private Long supplierId;
    private Long locationId;
    private List<ReceiptDetailRequest> detail;
}
