package com.qlyshopphone_backend.service;

import com.qlyshopphone_backend.dto.SupplierDTO;
import com.qlyshopphone_backend.exceptions.DataNotFoundException;
import com.qlyshopphone_backend.model.Supplier;
import com.qlyshopphone_backend.model.Users;
import org.springframework.http.ResponseEntity;

public interface SupplierService {
    ResponseEntity<?> getAllSuppliers();

    ResponseEntity<?> saveSuppliers(SupplierDTO supplierDTO, Users users);

    Supplier updateSuppliers(int supplierId, SupplierDTO supplierDTO, Users users) throws Exception;

    ResponseEntity<?> deleteSuppliers(int supplierId, Users users);

    ResponseEntity<?> findBySuppliersId(int supplierId);

    ResponseEntity<?> searchByPhoneNumber(String phoneNumber);

    ResponseEntity<?> searchByTaxCode(String taxCode);

    ResponseEntity<?> searchBySupplierName(String supplierName);

    ResponseEntity<?> searchByGroupSupplier(int groupSupplierId);

    ResponseEntity<?> getSupplier();

    ResponseEntity<?> searchNoActive(int number);
}
