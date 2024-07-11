package com.qlyshopphone_backend.mapper;

import com.qlyshopphone_backend.dto.CustomerInfoDTO;
import com.qlyshopphone_backend.model.CustomerInfo;
import com.qlyshopphone_backend.model.Users;

public class CustomerInfoMapper {
    public static CustomerInfoDTO infoDTO(CustomerInfo customerInfo) {
        CustomerInfoDTO customerInfoDTO = new CustomerInfoDTO();
        customerInfoDTO.setCustomerId(customerInfo.getCustomerId());
        customerInfoDTO.setCustomerName(customerInfo.getCustomerName());
        customerInfoDTO.setPhone(customerInfo.getPhone());
        customerInfoDTO.setAddress(customerInfo.getAddress());
        customerInfoDTO.setDeleteCustomerInfo(customerInfo.isDeleteCustomerInfo());
        customerInfoDTO.setUserId(customerInfoDTO.getUserId());
        return customerInfoDTO;
    }
    public static CustomerInfo infoEntity(CustomerInfoDTO customerInfoDTO, Users user) {
        CustomerInfo customerInfo = new CustomerInfo();
        customerInfo.setCustomerId(customerInfoDTO.getCustomerId());
        customerInfo.setCustomerName(customerInfoDTO.getCustomerName());
        customerInfo.setPhone(customerInfoDTO.getPhone());
        customerInfo.setAddress(customerInfoDTO.getAddress());
        customerInfo.setDeleteCustomerInfo(customerInfoDTO.isDeleteCustomerInfo());
        customerInfo.setUser(user);
        return customerInfo;
    }
}
