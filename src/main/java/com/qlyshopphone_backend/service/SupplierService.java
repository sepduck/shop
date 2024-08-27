package com.qlyshopphone_backend.service;

import com.qlyshopphone_backend.dto.GroupSupplierDTO;
import com.qlyshopphone_backend.dto.SupplierDTO;
import com.qlyshopphone_backend.model.GroupSupplier;
import com.qlyshopphone_backend.model.Users;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface SupplierService {
    List<Map<String, Object>> getAllSuppliers();

    String saveSuppliers(SupplierDTO supplierDTO, Users users);

    String updateSuppliers(Long supplierId, SupplierDTO supplierDTO, Users users) throws Exception;

    String deleteSuppliers(Long supplierId, Users users);

    List<Map<String, Object>> searchByPhoneNumber(String phoneNumber);

    List<Map<String, Object>> searchByTaxCode(String taxCode);

    List<Map<String, Object>> searchBySupplierName(String supplierName);

    List<Map<String, Object>> searchByGroupSupplier(Long groupSupplierId);

    List<Map<String, Object>> getSupplier();

    List<Map<String, Object>> searchNoActive(int number);

    List<GroupSupplier> getAllGroupSupplier();

    String saveGroupSupplier(GroupSupplierDTO groupSupplierDTO);

    String updateGroupSupplier(GroupSupplierDTO groupSupplierDTO, Long groupSupplierId);

    String deleteGroupSupplier(Long groupSupplierId);

}
