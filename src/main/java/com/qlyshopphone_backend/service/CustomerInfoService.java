package com.qlyshopphone_backend.service;

import com.qlyshopphone_backend.dto.request.CustomerInfoRequest;
import com.qlyshopphone_backend.repository.projection.CustomerInfoProjection;

import java.util.List;

public interface CustomerInfoService {
    List<CustomerInfoProjection> getCustomerInfo();

    CustomerInfoProjection getCustomerInfoByIdFromUser(Long customerId);

    boolean createCustomerInfo(CustomerInfoRequest customerInfoRequest);

    boolean updateCustomerInfo(Long customerId, CustomerInfoRequest customerInfoRequest);

    boolean deleteCustomerInfo(Long customerId);

}
