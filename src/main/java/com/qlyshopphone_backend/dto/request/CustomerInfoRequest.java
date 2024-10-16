package com.qlyshopphone_backend.dto.request;

import lombok.Data;

@Data
public class CustomerInfoRequest {
    private Long customerId;
    private String customerName;
    private String phone;
    private String address;
    private boolean deleteCustomerInfo;
    private Long userId;
}
