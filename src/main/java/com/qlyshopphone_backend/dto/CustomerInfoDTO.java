package com.qlyshopphone_backend.dto;
import static com.qlyshopphone_backend.constant.ErrorMessage.*;

import lombok.Data;

@Data
public class CustomerInfoDTO {
    private Long customerId;
    private String customerName;
    private String phone;
    private String address;
    private boolean deleteCustomerInfo;
    private Long userId;
}
