package com.qlyshopphone_backend.service;

import com.qlyshopphone_backend.dto.request.GroupSupplierRequest;
import com.qlyshopphone_backend.dto.request.SupplierRequest;
import com.qlyshopphone_backend.model.GroupSupplier;

import java.util.List;
import java.util.Map;

public interface SupplierService {
    List<Map<String, Object>> getAllSuppliers();

    String saveSuppliers(SupplierRequest supplierRequest);

    String updateSuppliers(Long supplierId, SupplierRequest supplierRequest) throws Exception;

    String deleteSuppliers(Long supplierId);

    List<Map<String, Object>> searchByPhoneNumber(String phoneNumber);

    List<Map<String, Object>> searchByTaxCode(String taxCode);

    List<Map<String, Object>> searchBySupplierName(String supplierName);

    List<Map<String, Object>> searchByGroupSupplier(Long groupSupplierId);

    List<Map<String, Object>> getSupplier();

    List<Map<String, Object>> searchNoActive(int number);

    List<GroupSupplier> getAllGroupSupplier();

    String saveGroupSupplier(GroupSupplierRequest groupSupplierRequest);

    String updateGroupSupplier(GroupSupplierRequest groupSupplierRequest, Long groupSupplierId);

    String deleteGroupSupplier(Long groupSupplierId);

}
