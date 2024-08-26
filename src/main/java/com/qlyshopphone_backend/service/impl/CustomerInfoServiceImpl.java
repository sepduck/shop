package com.qlyshopphone_backend.service.impl;

import com.qlyshopphone_backend.dto.CustomerInfoDTO;
import com.qlyshopphone_backend.mapper.CustomerInfoMapper;
import com.qlyshopphone_backend.model.BaseReponse;
import com.qlyshopphone_backend.model.CustomerInfo;
import com.qlyshopphone_backend.model.Users;
import com.qlyshopphone_backend.repository.CustomerInfoRepository;
import com.qlyshopphone_backend.repository.UserRepository;
import com.qlyshopphone_backend.service.CustomerInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerInfoServiceImpl extends BaseReponse implements CustomerInfoService {
    private final CustomerInfoRepository customerInfoRepository;
    private final UserRepository usersRepository;

    @Override
    public CustomerInfo createCustomerInfo(CustomerInfoDTO customerInfoDTO) {
        Optional<Users> usersOptional = usersRepository.findById(customerInfoDTO.getUserId());
        if (!usersOptional.isPresent()) {
            throw new RuntimeException("User not found");
        }
        Users users = usersOptional.get();
        CustomerInfo customerInfo = CustomerInfoMapper.infoEntity(customerInfoDTO, users);

        customerInfo.setUser(users);
        users.getCustomerInfo().add(customerInfo);
        return customerInfoRepository.save(customerInfo);
    }

    @Override
    public ResponseEntity<?> updateCustomerInfo(int customerId, CustomerInfoDTO customerInfoDTO) {
        CustomerInfo customerInfo = customerInfoRepository.findById(customerId).orElseThrow(() -> new RuntimeException("Customer Info not found"));
        customerInfo.setCustomerName(customerInfoDTO.getCustomerName());
        customerInfo.setPhone(customerInfoDTO.getPhone());
        customerInfo.setAddress(customerInfoDTO.getAddress());
        customerInfo.setDeleteCustomerInfo(customerInfoDTO.isDeleteCustomerInfo());
        CustomerInfo updatedCustomerInfo = customerInfoRepository.save(customerInfo);
        return getResponseEntity(CustomerInfoMapper.infoDTO(updatedCustomerInfo));
    }

    @Override
    public void deleteCustomerInfo(int customerId) {
        customerInfoRepository.deleteById(customerId);
    }

    @Override
    public CustomerInfo findById(int customerInfoId) {
        Optional<CustomerInfo> customerInfo = customerInfoRepository.findById(customerInfoId);
        if (customerInfo.isPresent()) {
            return customerInfo.get();
        }else {
            throw new RuntimeException("Customer Info not found");
        }
    }


}
