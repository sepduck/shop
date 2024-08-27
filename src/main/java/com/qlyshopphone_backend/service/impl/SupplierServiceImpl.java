package com.qlyshopphone_backend.service.impl;

import com.qlyshopphone_backend.dto.GroupSupplierDTO;
import com.qlyshopphone_backend.dto.SupplierDTO;
import com.qlyshopphone_backend.model.*;
import com.qlyshopphone_backend.repository.GroupSupplierRepository;
import com.qlyshopphone_backend.repository.ProductRepository;
import com.qlyshopphone_backend.repository.SupplierRepository;
import com.qlyshopphone_backend.service.NotificationService;
import com.qlyshopphone_backend.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SupplierServiceImpl extends BaseReponse implements SupplierService {
    private final SupplierRepository supplierRepository;
    private final GroupSupplierRepository groupSupplierRepository;
    private final ProductRepository productRepository;
    private final NotificationService notificationService;

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
    public String saveSuppliers(SupplierDTO supplierDTO, Users users) {
        GroupSupplier existingGroupSupplier = groupSupplierRepository.findById(supplierDTO.getGroupSupplierId())
                .orElseThrow(() -> new RuntimeException("GroupSupplier not found"));
        Product existingProduct = productRepository.findById(supplierDTO.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
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
        return "Supplier saved successfully";

    }

    @Override
    public String updateSuppliers(Long supplierId, SupplierDTO supplierDTO, Users users) {
        Supplier existingSupplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new RuntimeException("Supplier not found"));
        GroupSupplier existingGroupSupplier = groupSupplierRepository.findById(supplierDTO.getGroupSupplierId())
                .orElseThrow(() -> new RuntimeException("GroupSupplier not found"));
        Product existingProduct = productRepository.findById(supplierDTO.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
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
        supplierRepository.save(existingSupplier);
        return "Supplier updated successfully";
    }

    @Override
    public String deleteSuppliers(Long supplierId, Users users) {
        supplierRepository.deleteBySupplierId(supplierId);
        String message = users.getFullName() + " đã xóa nhà cung cấp có ID: NCC00" + supplierId;
        notificationService.saveNotification(message, users);
        return "Supplier deleted successfully";
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
        return "GroupSupplier saved successfully";
    }

    @Override
    public String updateGroupSupplier(GroupSupplierDTO groupSupplierDTO, Long groupSupplierId) {
        GroupSupplier groupSupplier = groupSupplierRepository.findById(groupSupplierId)
                .orElseThrow(() -> new RuntimeException("Group supplier not found"));
        groupSupplier.setGroupSupplierName(groupSupplierDTO.getGroupSupplierName());
        groupSupplier.setNote(groupSupplierDTO.getNote());
        groupSupplierRepository.save(groupSupplier);
        return "GroupSupplier updated successfully";
    }

    @Override
    public String deleteGroupSupplier(Long groupSupplierId) {
        GroupSupplier groupSupplier = groupSupplierRepository.findById(groupSupplierId)
                .orElseThrow(() -> new RuntimeException("Group supplier not found"));
        groupSupplierRepository.deleteById(groupSupplier.getGroupSupplierId());
        return "GroupSupplier deleted successfully";
    }
}
