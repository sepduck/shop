package com.qlyshopphone_backend.service;

import com.qlyshopphone_backend.dto.CustomerInfoDTO;
import com.qlyshopphone_backend.model.CustomerInfo;
import org.springframework.http.ResponseEntity;

public interface CustomerInfoService {
    CustomerInfo createCustomerInfo(CustomerInfoDTO customerInfoDTO);

    ResponseEntity<?> updateCustomerInfo(int customerId, CustomerInfoDTO customerInfoDTO);

    void deleteCustomerInfo(int customerId);

    CustomerInfo findById(int customerId);

}
