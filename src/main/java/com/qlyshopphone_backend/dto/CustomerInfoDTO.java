package com.qlyshopphone_backend.dto;

import lombok.Data;

@Data
public class CustomerInfoDTO {
    private Integer customerId;
    private String customerName;
    private String phone;
    private String address;
    private boolean deleteCustomerInfo;
    private Integer userId;
}
