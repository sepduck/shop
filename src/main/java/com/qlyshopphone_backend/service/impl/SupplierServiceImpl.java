package com.qlyshopphone_backend.service.impl;

import com.qlyshopphone_backend.repository.GroupSupplierRepository;
import com.qlyshopphone_backend.repository.ProductRepository;
import com.qlyshopphone_backend.repository.SupplierRepository;
import com.qlyshopphone_backend.service.AuthenticationService;
import com.qlyshopphone_backend.service.NotificationService;
import com.qlyshopphone_backend.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {
    private final SupplierRepository supplierRepository;
    private final GroupSupplierRepository groupSupplierRepository;
    private final ProductRepository productRepository;
    private final NotificationService notificationService;
    private final AuthenticationService authenticationService;

//    @Override
//    public List<Map<String, Object>> getSupplier() {
//        return supplierRepository.getSuppliers();
//    }
//
//    @Override
//    public List<Map<String, Object>> searchNoActive(int number) {
//        return switch (number) {
//            case 1 -> supplierRepository.getSuppliers();
//            case 2 -> supplierRepository.getAllSuppliers();
//            case 3 -> supplierRepository.searchNoActive();
//            default -> new ArrayList<>();
//        };
//    }
//
//    @Override
//    public List<Map<String, Object>> getAllSuppliers() {
//        return supplierRepository.getAllSuppliers();
//    }
//
//    @Override
//    public String saveSuppliers(SupplierRequest supplierRequest) {
//        User user = authenticationService.getAuthenticatedUser();
//        Supplier supplier = new Supplier();
//        updateSupplierProperties(supplier, supplierRequest);
//
//        String message = user.getFirstName() + " have successfully added supplier " + supplier.getSupplierName();
//        notificationService.saveNotification(message, user);
//        supplierRepository.save(supplier);
//        return SUPPLIER_SAVED_SUCCESSFULLY;
//
//    }
//
//    @Override
//    public String updateSuppliers(Long supplierId, SupplierRequest supplierRequest) {
//        User user = authenticationService.getAuthenticatedUser();
//        Supplier supplier = supplierRepository.findById(supplierId)
//                .orElseThrow(() -> new RuntimeException(SUPPLIER_NOT_FOUND));
//        updateSupplierProperties(supplier, supplierRequest);
//        String message = user.getFirstName() + " have edited product " + supplierRequest.getSupplierName() + " to " + supplier.getSupplierName();
//        notificationService.saveNotification(message, user);
//        supplierRepository.save(supplier);
//        return SUPPLIER_UPDATED_SUCCESSFULLY;
//    }
//
//    private void updateSupplierProperties(Supplier supplier, SupplierRequest supplierRequest) {
//        GroupSupplier existingGroupSupplier = groupSupplierRepository.findById(supplierRequest.getGroupSupplierId())
//                .orElseThrow(() -> new RuntimeException(GROUP_SUPPLIER_NOT_FOUND));
//        Product existingProduct = productRepository.findById(supplierRequest.getProductId())
//                .orElseThrow(() -> new RuntimeException(PRODUCT_NOT_FOUND));
//        supplier.setSupplierName(supplierRequest.getSupplierName());
//        supplier.setPhoneNumber(supplierRequest.getPhoneNumber());
//        supplier.setAddress(supplierRequest.getAddress());
//        supplier.setEmail(supplierRequest.getEmail());
//        supplier.setCompany(supplierRequest.getCompany());
//        supplier.setTaxCode(supplierRequest.getTaxCode());
//        supplier.setStatus(Status.ACTIVE);
//        supplier.setGroupSupplier(existingGroupSupplier);
//        supplier.setProduct(existingProduct);
//    }
//
//    @Override
//    public String deleteSuppliers(Long supplierId) {
//        User user = authenticationService.getAuthenticatedUser();
//        Supplier supplier = supplierRepository.findById(supplierId)
//                .orElseThrow(() -> new RuntimeException(SUPPLIER_NOT_FOUND));
//        supplierRepository.deleteBySupplierId(supplier.getId());
//        String message = user.getFirstName() + " have successfully deleted supplier " + supplier.getSupplierName();
//        notificationService.saveNotification(message, user);
//        return SUPPLIER_DELETED_SUCCESSFULLY;
//    }
//
//    @Override
//    public List<Map<String, Object>> searchByPhoneNumber(String phoneNumber) {
//        return supplierRepository.searchAllByPhoneNumber(phoneNumber);
//    }
//
//    @Override
//    public List<Map<String, Object>> searchByTaxCode(String taxCode) {
//
//        return supplierRepository.searchAllByTaxCode(taxCode);
//    }
//
//    @Override
//    public List<Map<String, Object>> searchBySupplierName(String supplierName) {
//        return supplierRepository.searchAllBySupplierNameLike(supplierName);
//    }
//
//    @Override
//    public List<Map<String, Object>> searchByGroupSupplier(Long groupSupplierId) {
//        return supplierRepository.searchByid(groupSupplierId);
//    }
//
//    @Override
//    public List<GroupSupplier> getAllGroupSupplier() {
//        return groupSupplierRepository.findAll();
//    }
//
//    @Override
//    public String saveGroupSupplier(GroupSupplierRequest groupSupplierRequest) {
//        GroupSupplier groupSupplier = new GroupSupplier();
//        groupSupplier.setGroupSupplierName("Group supplier - " + groupSupplierRequest.getGroupSupplierName());
//        groupSupplier.setNote(groupSupplierRequest.getNote());
//        groupSupplierRepository.save(groupSupplier);
//        return GROUP_SUPPLIER_SAVED_SUCCESSFULLY;
//    }
//
//    @Override
//    public String updateGroupSupplier(GroupSupplierRequest groupSupplierRequest, Long groupSupplierId) {
//        GroupSupplier groupSupplier = groupSupplierRepository.findById(groupSupplierId)
//                .orElseThrow(() -> new RuntimeException(GROUP_SUPPLIER_NOT_FOUND));
//        groupSupplier.setGroupSupplierName(groupSupplierRequest.getGroupSupplierName());
//        groupSupplier.setNote(groupSupplierRequest.getNote());
//        groupSupplierRepository.save(groupSupplier);
//        return GROUP_SUPPLIER_UPDATED_SUCCESSFULLY;
//    }
//
//    @Override
//    public String deleteGroupSupplier(Long groupSupplierId) {
//        GroupSupplier groupSupplier = groupSupplierRepository.findById(groupSupplierId)
//                .orElseThrow(() -> new RuntimeException(GROUP_SUPPLIER_NOT_FOUND));
//        groupSupplierRepository.deleteById(groupSupplier.getId());
//        return GROUP_SUPPLIER_DELETED_SUCCESSFULLY;
//    }
}
