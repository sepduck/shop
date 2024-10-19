package com.qlyshopphone_backend.mapper;

import com.qlyshopphone_backend.dto.request.CustomerInfoRequest;
import com.qlyshopphone_backend.model.CustomerInfo;
import com.qlyshopphone_backend.model.Users;

public class CustomerInfoMapper {
    public static CustomerInfoRequest infoDTO(CustomerInfo customerInfo) {
        CustomerInfoRequest customerInfoRequest = new CustomerInfoRequest();
        customerInfoRequest.setId(customerInfo.getId());
        customerInfoRequest.setName(customerInfo.getName());
        customerInfoRequest.setPhone(customerInfo.getPhone());
        customerInfoRequest.setAddress(customerInfo.getAddress());
        customerInfoRequest.setDeleteCustomerInfo(customerInfo.isDeleteCustomerInfo());
        customerInfoRequest.setUserId(customerInfoRequest.getUserId());
        return customerInfoRequest;
    }
    public static CustomerInfo infoEntity(CustomerInfoRequest customerInfoRequest, Users users) {
        CustomerInfo customerInfo = new CustomerInfo();
        customerInfo.setId(customerInfoRequest.getId());
        customerInfo.setName(customerInfoRequest.getName());
        customerInfo.setPhone(customerInfoRequest.getPhone());
        customerInfo.setAddress(customerInfoRequest.getAddress());
        customerInfo.setDeleteCustomerInfo(customerInfoRequest.isDeleteCustomerInfo());
        customerInfo.setUsers(users);
        return customerInfo;
    }
}
