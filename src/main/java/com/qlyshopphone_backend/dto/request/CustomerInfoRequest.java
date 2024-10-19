package com.qlyshopphone_backend.dto.request;

import lombok.Data;

@Data
public class CustomerInfoRequest {
    private Long id;
    private String name;
    private String phone;
    private String address;
    private boolean deleteCustomerInfo;
    private Long userId;
}
