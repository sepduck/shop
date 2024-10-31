package com.qlyshopphone_backend.service;

import com.qlyshopphone_backend.dto.request.ProductAttributeRequest;
import com.qlyshopphone_backend.dto.request.SupplierRequest;
import com.qlyshopphone_backend.dto.response.ProductAttributeResponse;
import com.qlyshopphone_backend.dto.response.SupplierResponse;
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

    List<SupplierProjection> searchSuppliersByPhone(String phone);

    List<SupplierProjection> searchSuppliersByTaxCode(String taxCode);

    List<SupplierProjection> searchSuppliersByName(String name);

    List<SupplierProjection> searchSuppliersByGroupSupplier(Long id);

    List<SupplierProjection> searchSuppliersByStatus(String status);
}
