package com.qlyshopphone_backend.service;

import com.qlyshopphone_backend.dto.request.ProductAttributeRequest;
import com.qlyshopphone_backend.dto.request.SupplierRequest;
import com.qlyshopphone_backend.dto.response.ProductAttributeResponse;
import com.qlyshopphone_backend.dto.response.SupplierResponse;
import com.qlyshopphone_backend.model.Suppliers;
import com.qlyshopphone_backend.repository.projection.SupplierProjection;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SupplierService {
    List<SupplierProjection> getSupplier(Pageable pageable);

    SupplierResponse createSupplier(SupplierRequest supplierRequest);

    boolean updateSupplierName(SupplierRequest request, Long id);

    boolean updateSupplierPhone(SupplierRequest request, Long id);

    boolean updateSupplierEmail(SupplierRequest request, Long id);

    boolean updateSupplierCompany(SupplierRequest request, Long id);

    boolean updateSupplierTaxCode(SupplierRequest request, Long id);

    boolean updateInfoGroupInSupplier(SupplierRequest request, Long id);

    boolean updateSupplierAddress(SupplierRequest request, Long id);

    boolean inactiveSupplier(Long id);

    List<ProductAttributeResponse> getAllGroupSupplier();

    ProductAttributeResponse createGroupSupplier(ProductAttributeRequest request);

    ProductAttributeResponse updateGroupSupplier(ProductAttributeRequest request, Long groupSupplierId);

    Suppliers findSupplierById(Long id);

//    List<Suppliers> searchByPhoneNumber(String phoneNumber);
//
//    List<Suppliers> searchByTaxCode(String taxCode);

//    List<Suppliers> searchBySupplierName(String supplierName);

//    List<Suppliers> searchByGroupSupplier(Long groupSupplierId);

//    List<Suppliers> getSupplier();
//
//    List<Suppliers> searchNoActive(int number);

}
