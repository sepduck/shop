package com.qlyshopphone_backend.service.impl;
import static com.qlyshopphone_backend.constant.ErrorMessage.*;

import com.qlyshopphone_backend.dto.GroupSupplierDTO;
import com.qlyshopphone_backend.dto.SupplierDTO;
import com.qlyshopphone_backend.model.*;
import com.qlyshopphone_backend.repository.GroupSupplierRepository;
import com.qlyshopphone_backend.repository.ProductRepository;
import com.qlyshopphone_backend.repository.SupplierRepository;
import com.qlyshopphone_backend.service.AuthenticationService;
import com.qlyshopphone_backend.service.NotificationService;
import com.qlyshopphone_backend.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {
    private final SupplierRepository supplierRepository;
    private final GroupSupplierRepository groupSupplierRepository;
    private final ProductRepository productRepository;
    private final NotificationService notificationService;
    private final AuthenticationService authenticationService;
    @Override
    public List<Map<String, Object>> getSupplier() {
        return supplierRepository.getSuppliers();
    }

    @Override
    public List<Map<String, Object>> searchNoActive(int number) {
        return switch (number) {
            case 1 -> supplierRepository.getSuppliers();
            case 2 -> supplierRepository.getAllSuppliers();
            case 3 -> supplierRepository.searchNoActive();
            default -> new ArrayList<>();
        };
    }

    @Override
    public List<Map<String, Object>> getAllSuppliers() {
        return supplierRepository.getAllSuppliers();
    }

    @Override
    public String saveSuppliers(SupplierDTO supplierDTO) {
        Users users = authenticationService.getAuthenticatedUser();
        Supplier supplier = new Supplier();
        updateSupplierProperties(supplier, supplierDTO);

        String message = users.getFullName() + " have successfully added supplier " + supplier.getSupplierName();
        notificationService.saveNotification(message, users);
        supplierRepository.save(supplier);
        return SUPPLIER_SAVED_SUCCESSFULLY;

    }

    @Override
    public String updateSuppliers(Long supplierId, SupplierDTO supplierDTO) {
        Users users = authenticationService.getAuthenticatedUser();
        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new RuntimeException(SUPPLIER_NOT_FOUND));
        updateSupplierProperties(supplier, supplierDTO);
        String message = users.getFullName() + " have edited product " + supplierDTO.getSupplierName() + " to " + supplier.getSupplierName();
        notificationService.saveNotification(message, users);
        supplierRepository.save(supplier);
        return SUPPLIER_UPDATED_SUCCESSFULLY;
    }

    private void updateSupplierProperties(Supplier supplier, SupplierDTO supplierDTO) {
        GroupSupplier existingGroupSupplier = groupSupplierRepository.findById(supplierDTO.getGroupSupplierId())
                .orElseThrow(() -> new RuntimeException(GROUP_SUPPLIER_NOT_FOUND));
        Product existingProduct = productRepository.findById(supplierDTO.getProductId())
                .orElseThrow(() -> new RuntimeException(PRODUCT_NOT_FOUND));
        supplier.setSupplierName(supplierDTO.getSupplierName());
        supplier.setPhoneNumber(supplierDTO.getPhoneNumber());
        supplier.setAddress(supplierDTO.getAddress());
        supplier.setEmail(supplierDTO.getEmail());
        supplier.setCompany(supplierDTO.getCompany());
        supplier.setTaxCode(supplierDTO.getTaxCode());
        supplier.setDeleteSupplier(supplierDTO.isDeleteProduct());
        supplier.setGroupSupplier(existingGroupSupplier);
        supplier.setProduct(existingProduct);
    }

    @Override
    public String deleteSuppliers(Long supplierId) {
        Users users = authenticationService.getAuthenticatedUser();
        Supplier supplier = supplierRepository.findById(supplierId)
                        .orElseThrow(() -> new RuntimeException(SUPPLIER_NOT_FOUND));
        supplierRepository.deleteBySupplierId(supplier.getSupplierId());
        String message = users.getFullName() + " have successfully deleted supplier " + supplier.getSupplierName();
        notificationService.saveNotification(message, users);
        return SUPPLIER_DELETED_SUCCESSFULLY;
    }

    @Override
    public List<Map<String, Object>> searchByPhoneNumber(String phoneNumber) {
        return supplierRepository.searchAllByPhoneNumber(phoneNumber);
    }

    @Override
    public List<Map<String, Object>> searchByTaxCode(String taxCode) {

        return supplierRepository.searchAllByTaxCode(taxCode);
    }

    @Override
    public List<Map<String, Object>> searchBySupplierName(String supplierName) {
        return supplierRepository.searchAllBySupplierNameLike(supplierName);
    }

    @Override
    public List<Map<String, Object>> searchByGroupSupplier(Long groupSupplierId) {
        return supplierRepository.searchByGroupSupplierId(groupSupplierId);
    }

    @Override
    public List<GroupSupplier> getAllGroupSupplier() {
        return groupSupplierRepository.findAll();
    }

    @Override
    public String saveGroupSupplier(GroupSupplierDTO groupSupplierDTO) {
        GroupSupplier groupSupplier = new GroupSupplier();
        groupSupplier.setGroupSupplierName("Group supplier - " + groupSupplierDTO.getGroupSupplierName());
        groupSupplier.setNote(groupSupplierDTO.getNote());
        groupSupplierRepository.save(groupSupplier);
        return GROUP_SUPPLIER_SAVED_SUCCESSFULLY;
    }

    @Override
    public String updateGroupSupplier(GroupSupplierDTO groupSupplierDTO, Long groupSupplierId) {
        GroupSupplier groupSupplier = groupSupplierRepository.findById(groupSupplierId)
                .orElseThrow(() -> new RuntimeException(GROUP_SUPPLIER_NOT_FOUND));
        groupSupplier.setGroupSupplierName(groupSupplierDTO.getGroupSupplierName());
        groupSupplier.setNote(groupSupplierDTO.getNote());
        groupSupplierRepository.save(groupSupplier);
        return GROUP_SUPPLIER_UPDATED_SUCCESSFULLY;
    }

    @Override
    public String deleteGroupSupplier(Long groupSupplierId) {
        GroupSupplier groupSupplier = groupSupplierRepository.findById(groupSupplierId)
                .orElseThrow(() -> new RuntimeException(GROUP_SUPPLIER_NOT_FOUND));
        groupSupplierRepository.deleteById(groupSupplier.getGroupSupplierId());
        return GROUP_SUPPLIER_DELETED_SUCCESSFULLY;
    }
}
