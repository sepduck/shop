package com.qlyshopphone_backend.service.impl;

import com.qlyshopphone_backend.dao.SupplierDAO;
import com.qlyshopphone_backend.dto.SupplierDTO;
import com.qlyshopphone_backend.exceptions.DataNotFoundException;
import com.qlyshopphone_backend.model.*;
import com.qlyshopphone_backend.repository.GroupSupplierRepository;
import com.qlyshopphone_backend.repository.ProductRepository;
import com.qlyshopphone_backend.repository.SupplierRepository;
import com.qlyshopphone_backend.service.NotificationService;
import com.qlyshopphone_backend.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SupplierServiceImpl extends BaseReponse implements SupplierService {
    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private GroupSupplierRepository groupSupplierRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private SupplierDAO supplierDAO;
    @Autowired
    private NotificationService notificationService;

    @Override
    public ResponseEntity<?> getSupplier() {
        return getResponseEntity(supplierRepository.getSuppliers());
    }

    @Override
    public ResponseEntity<?> searchNoActive(int number) {
        switch (number) {
            case 1:
                return getResponseEntity(supplierRepository.getSuppliers());
            case 2:
                return getResponseEntity(supplierRepository.getAllSuppliers());
            case 3:
                return getResponseEntity(supplierRepository.searchNoActive());
            default:
                return getResponseEntity("Supplier not found");
        }
    }

    @Override
    public ResponseEntity<?> getAllSuppliers() {
        return getResponseEntity(supplierRepository.getAllSuppliers());
    }

    @Override
    public ResponseEntity<?> saveSuppliers(SupplierDTO supplierDTO, Users users) {
        try {
            GroupSupplier existingGroupSupplier = groupSupplierRepository.findById(supplierDTO.getGroupSupplierId())
                    .orElseThrow(() -> new DataNotFoundException("GroupSupplier not found"));
            Product existingProduct = productRepository.findById(supplierDTO.getProductId())
                    .orElseThrow(() -> new DataNotFoundException("Product not found"));
            Supplier supplier = new Supplier();
            supplier.setSupplierName(supplierDTO.getSupplierName());
            supplier.setPhoneNumber(supplierDTO.getPhoneNumber());
            supplier.setAddress(supplierDTO.getAddress());
            supplier.setEmail(supplierDTO.getEmail());
            supplier.setCompany(supplierDTO.getCompany());
            supplier.setTaxCode(supplierDTO.getTaxCode());
            supplier.setDeleteSupplier(supplierDTO.isDeleteProduct());
            supplier.setGroupSupplier(existingGroupSupplier);
            supplier.setProduct(existingProduct);
            supplierRepository.save(supplier);

            String message = users.getFullName() + " đã thêm nhà cung cấp " + supplier.getSupplierName();
            notificationService.saveNotification(message, users);
            return getResponseEntity("Supplier saved successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Supplier save failed");
        }
    }

    @Override
    public Supplier updateSuppliers(int supplierId, SupplierDTO supplierDTO, Users users) throws Exception{
        Supplier existingSupplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new RuntimeException("Supplier not found"));
        GroupSupplier existingGroupSupplier = groupSupplierRepository.findById(supplierDTO.getGroupSupplierId())
                .orElseThrow(() -> new DataNotFoundException("GroupSupplier not found"));
        Product existingProduct = productRepository.findById(supplierDTO.getProductId())
                .orElseThrow(() -> new DataNotFoundException("Product not found"));
        existingSupplier.setSupplierName(supplierDTO.getSupplierName());
        existingSupplier.setPhoneNumber(supplierDTO.getPhoneNumber());
        existingSupplier.setAddress(supplierDTO.getAddress());
        existingSupplier.setEmail(supplierDTO.getEmail());
        existingSupplier.setCompany(supplierDTO.getCompany());
        existingSupplier.setTaxCode(supplierDTO.getTaxCode());
        existingSupplier.setDeleteSupplier(supplierDTO.isDeleteProduct());
        existingSupplier.setGroupSupplier(existingGroupSupplier);
        existingSupplier.setProduct(existingProduct);

        String message = users.getFullName() + " đã sửa nhà cung cấp " + existingSupplier.getSupplierName();
        notificationService.saveNotification(message, users);
        return supplierRepository.save(existingSupplier);
    }

    @Override
    public ResponseEntity<?> deleteSuppliers(int supplierId, Users users) {
        try {
            supplierDAO.deleteSupplier(supplierId);
            String message = users.getFullName() + " đã xóa nhà cung cấp có ID: NCC00" + supplierId;
            notificationService.saveNotification(message, users);
            return getResponseEntity("Supplier deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Supplier delete failed");
        }
    }

    @Override
    public ResponseEntity<?> findBySuppliersId(int supplierId) {
        return getResponseEntity(supplierRepository.findById(supplierId));
    }

    @Override
    public ResponseEntity<?> searchByPhoneNumber(String phoneNumber) {
        try {
            return getResponseEntity(supplierRepository.searchAllByPhoneNumber(phoneNumber));
        } catch (Exception e) {
            return getResponseEntity("Supplier not found");
        }
    }

    @Override
    public ResponseEntity<?> searchByTaxCode(String taxCode) {
        try {
            return getResponseEntity(supplierRepository.searchAllByTaxCode(taxCode));
        } catch (Exception e) {
            return getResponseEntity("Supplier not found");
        }
    }

    @Override
    public ResponseEntity<?> searchBySupplierName(String supplierName) {
        try {
            return getResponseEntity(supplierRepository.searchAllBySupplierNameLike(supplierName));
        } catch (Exception e) {
            return getResponseEntity("Supplier not found");
        }
    }

    @Override
    public ResponseEntity<?> searchByGroupSupplier(int groupSupplierId) {
        return getResponseEntity(supplierRepository.searchByGroupSupplierId(groupSupplierId));
    }
}
