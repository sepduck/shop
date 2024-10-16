package com.qlyshopphone_backend.mapper;

import com.qlyshopphone_backend.dto.request.CustomerInfoRequest;
import com.qlyshopphone_backend.model.CustomerInfo;
import com.qlyshopphone_backend.model.Users;

public class CustomerInfoMapper {
    public static CustomerInfoRequest infoDTO(CustomerInfo customerInfo) {
        CustomerInfoRequest customerInfoRequest = new CustomerInfoRequest();
        customerInfoRequest.setCustomerId(customerInfo.getCustomerId());
        customerInfoRequest.setCustomerName(customerInfo.getCustomerName());
        customerInfoRequest.setPhone(customerInfo.getPhone());
        customerInfoRequest.setAddress(customerInfo.getAddress());
        customerInfoRequest.setDeleteCustomerInfo(customerInfo.isDeleteCustomerInfo());
        customerInfoRequest.setUserId(customerInfoRequest.getUserId());
        return customerInfoRequest;
    }
    public static CustomerInfo infoEntity(CustomerInfoRequest customerInfoRequest, Users user) {
        CustomerInfo customerInfo = new CustomerInfo();
        customerInfo.setCustomerId(customerInfoRequest.getCustomerId());
        customerInfo.setCustomerName(customerInfoRequest.getCustomerName());
        customerInfo.setPhone(customerInfoRequest.getPhone());
        customerInfo.setAddress(customerInfoRequest.getAddress());
        customerInfo.setDeleteCustomerInfo(customerInfoRequest.isDeleteCustomerInfo());
        customerInfo.setUser(user);
        return customerInfo;
    }
}
