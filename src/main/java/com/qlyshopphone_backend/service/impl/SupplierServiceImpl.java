package com.qlyshopphone_backend.service.impl;

import com.qlyshopphone_backend.dto.request.ProductAttributeRequest;
import com.qlyshopphone_backend.dto.request.SupplierRequest;
import com.qlyshopphone_backend.dto.response.ProductAttributeResponse;
import com.qlyshopphone_backend.dto.response.SupplierResponse;
import com.qlyshopphone_backend.exceptions.ApiRequestException;
import com.qlyshopphone_backend.mapper.BasicMapper;
import com.qlyshopphone_backend.model.*;
import com.qlyshopphone_backend.model.enums.Status;
import com.qlyshopphone_backend.repository.AddressRepository;
import com.qlyshopphone_backend.repository.GroupSupplierRepository;
import com.qlyshopphone_backend.repository.SupplierRepository;
import com.qlyshopphone_backend.repository.projection.SupplierProjection;
import com.qlyshopphone_backend.service.SupplierService;
import com.qlyshopphone_backend.service.util.AddressService;
import com.qlyshopphone_backend.service.util.ProductServiceHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.qlyshopphone_backend.constant.ErrorMessage.*;

@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {
    private final SupplierRepository supplierRepository;
    private final GroupSupplierRepository groupSupplierRepository;
    private final BasicMapper basicMapper;
    private final ProductServiceHelper productServiceHelper;
    private final AddressService addressService;
    private final AddressRepository addressRepository;

    @Override
    public List<SupplierProjection> getSupplier(Pageable pageable) {
        return supplierRepository.getAllSuppliers(pageable);
    }

    @Transactional
    @Override
    public SupplierResponse createSupplier(SupplierRequest request) {
        GroupSuppliers groupSupplier = groupSupplierRepository.findById(request.getGroupSupplierId())
                .orElseThrow(() -> new ApiRequestException(GROUP_SUPPLIER_NOT_FOUND, HttpStatus.BAD_REQUEST));
        Suppliers supplier = basicMapper.convertToRequest(request, Suppliers.class);
        supplier.setStatus(Status.ACTIVE);
        supplier.setGroupSupplier(groupSupplier);

        Address address = new Address();
        addressService.setAddressDetails(
                address,
                request.getWardId(),
                request.getCityId(),
                request.getCountryId(),
                request.getStreet()
        );
        supplier.setAddress(address);

        supplierRepository.save(supplier);
        return basicMapper.convertToResponse(supplier, SupplierResponse.class);

    }

    @Transactional
    @Override
    public boolean updateSupplierName(SupplierRequest request, Long id) {
        Suppliers supplier = findSupplierById(id);
        supplier.setName(request.getName());
        supplierRepository.save(supplier);
        return true;
    }

    @Transactional
    @Override
    public boolean updateSupplierPhone(SupplierRequest request, Long id) {
        Suppliers supplier = findSupplierById(id);
        supplier.setPhoneNumber(request.getPhoneNumber());
        supplierRepository.save(supplier);
        return true;
    }

    @Transactional
    @Override
    public boolean updateSupplierEmail(SupplierRequest request, Long id) {
        Suppliers supplier = findSupplierById(id);
        supplier.setEmail(request.getEmail());
        supplierRepository.save(supplier);
        return true;
    }

    @Transactional
    @Override
    public boolean updateSupplierCompany(SupplierRequest request, Long id) {
        Suppliers supplier = findSupplierById(id);
        supplier.setCompany(request.getCompany());
        supplierRepository.save(supplier);
        return true;
    }

    @Transactional
    @Override
    public boolean updateSupplierTaxCode(SupplierRequest request, Long id) {
        Suppliers supplier = findSupplierById(id);
        supplier.setTaxCode(request.getTaxCode());
        supplierRepository.save(supplier);
        return true;
    }
    @Transactional
    @Override
    public boolean updateInfoGroupInSupplier(SupplierRequest request, Long id) {
        Suppliers supplier = findSupplierById(id);
        GroupSuppliers groupSupplier = groupSupplierRepository.findById(request.getGroupSupplierId())
                .orElseThrow(() -> new ApiRequestException(GROUP_SUPPLIER_NOT_FOUND, HttpStatus.BAD_REQUEST));
        supplier.setGroupSupplier(groupSupplier);
        supplierRepository.save(supplier);
        return true;
    }
    @Transactional
    @Override
    public boolean updateSupplierAddress(SupplierRequest request, Long id) {
        Suppliers supplier = findSupplierById(id);
        Address address = addressRepository.findById(supplier.getAddress().getId())
                .orElseThrow(() -> new ApiRequestException(ADDRESS_NOT_FOUND, HttpStatus.BAD_REQUEST));
        addressService.setAddressDetails(
                address,
                request.getWardId(),
                request.getCityId(),
                request.getCountryId(),
                request.getStreet()
        );
        addressRepository.save(address);
        return true;
    }

    @Override
    public boolean inactiveSupplier(Long id) {
        Suppliers supplier = findSupplierById(id);
        supplier.setStatus(Status.INACTIVE);
        supplierRepository.save(supplier);
        return true;
    }
    
    @Override
    public List<ProductAttributeResponse> getAllGroupSupplier() {
        List<GroupSuppliers> list = groupSupplierRepository.findAll();
        return basicMapper.convertToResponseList(list, ProductAttributeResponse.class);
    }

    @Override
    public ProductAttributeResponse createGroupSupplier(ProductAttributeRequest request) {
        GroupSuppliers groupSupplier = new GroupSuppliers();
        groupSupplier.setName(request.getName());
        groupSupplierRepository.save(groupSupplier);
        return basicMapper.convertToResponse(groupSupplier, ProductAttributeResponse.class);
    }

    @Override
    public ProductAttributeResponse updateGroupSupplier(ProductAttributeRequest request, Long id) {
        return productServiceHelper.updateAttribute(
                id,
                request,
                groupSupplierRepository::findById,
                groupSupplierRepository::save,
                GroupSuppliers::getName,
                GroupSuppliers::setName,
                GROUP_SUPPLIER_NOT_FOUND
        );
    }

    @Override
    public Suppliers findSupplierById(Long id) {
        return supplierRepository.findById(id).orElseThrow(() -> new ApiRequestException(SUPPLIER_NOT_FOUND, HttpStatus.BAD_REQUEST));
    }


/*    @Override
    public List<Suppliers> searchByPhoneNumber(String phoneNumber) {
        return supplierRepository.searchAllByPhoneNumber(phoneNumber);
    }

    @Override
    public List<Suppliers> searchByTaxCode(String taxCode) {

        return supplierRepository.searchAllByTaxCode(taxCode);
    }*/

//    @Override
//    public List<Suppliers> searchBySupplierName(String supplierName) {
//        return supplierRepository.searchAllBySupplierNameLike(supplierName);
//    }

//    @Override
//    public List<Suppliers> searchByGroupSupplier(Long groupSupplierId) {
//        return supplierRepository.searchByid(groupSupplierId);
//    }



}
