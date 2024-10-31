package com.qlyshopphone_backend.service.impl;

import com.qlyshopphone_backend.dto.request.CustomerInfoRequest;
import com.qlyshopphone_backend.model.Address;
import com.qlyshopphone_backend.model.CustomerInfo;
import com.qlyshopphone_backend.model.Users;
import com.qlyshopphone_backend.model.enums.Status;
import com.qlyshopphone_backend.repository.AddressRepository;
import com.qlyshopphone_backend.repository.CustomerInfoRepository;
import com.qlyshopphone_backend.repository.projection.CustomerInfoProjection;
import com.qlyshopphone_backend.service.CustomerInfoService;
import com.qlyshopphone_backend.service.util.AddressService;
import com.qlyshopphone_backend.service.util.EntityFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class CustomerInfoServiceImpl implements CustomerInfoService {
    private final CustomerInfoRepository customerInfoRepository;
    private final AddressService addressService;
    private final EntityFinder entityFinder;
    private final AddressRepository addressRepository;

    @Override
    public List<CustomerInfoProjection> getCustomerInfo() {
        Users authUser = entityFinder.getCurrentAuthenticatedUser();
        return customerInfoRepository.findCustomerInfoByUser(authUser.getId(), Status.ACTIVE);
    }

    @Override
    public CustomerInfoProjection getCustomerInfoByIdFromUser(Long customerInfoId) {
        Users authUser = entityFinder.getCurrentAuthenticatedUser();
        return customerInfoRepository.findCustomerInfoByIdFromUser(authUser.getId(), customerInfoId, Status.ACTIVE);
    }

    @Override
    public boolean createCustomerInfo(CustomerInfoRequest request) {
        Users authUser = entityFinder.getCurrentAuthenticatedUser();

        long customerInfoCount = customerInfoRepository.countByUserId(authUser.getId());
        if (customerInfoCount > 5) {
            return false;
        }
        Address address = new Address();
        addressService.setAddressDetails(address, request.getWardId(), request.getCityId(), request.getCountryId(), request.getStreet());
        addressRepository.save(address);

        CustomerInfo customerInfo = new CustomerInfo(request.getName(), request.getPhone(), address, authUser, Status.ACTIVE);
        CustomerInfo saveCustomerInfo = customerInfoRepository.save(customerInfo);
        customerInfoRepository.save(saveCustomerInfo);
        return true;
    }

    @Override
    public boolean updateCustomerInfo(Long customerId, CustomerInfoRequest request) {
        Users authUser = entityFinder.getCurrentAuthenticatedUser();

        Address address = entityFinder.findAddressById(authUser.getAddress().getId());
        CustomerInfo customerInfo = entityFinder.findCustomerInfoByUserIdAndId(authUser.getId(), customerId);
        addressService.setAddressDetails(address, request.getWardId(), request.getCityId(), request.getCountryId(), request.getStreet());
        addressRepository.save(address);
        customerInfo.setName(request.getName());
        customerInfo.setPhone(request.getPhone());
        customerInfo.setAddress(address);
        customerInfoRepository.save(customerInfo);
        return true;
    }

    @Override
    public boolean deleteCustomerInfo(Long customerId) {
        Users authUser = entityFinder.getCurrentAuthenticatedUser();
        CustomerInfo customerInfo = entityFinder.findCustomerInfoByUserIdAndId(authUser.getId(), customerId);
        customerInfo.setStatus(Status.DELETED);
        customerInfoRepository.save(customerInfo);
        return true;
    }
}
