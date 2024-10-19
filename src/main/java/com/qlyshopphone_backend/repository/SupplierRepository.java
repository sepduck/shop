package com.qlyshopphone_backend.repository;

import com.qlyshopphone_backend.model.Suppliers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends JpaRepository<Suppliers, Long> {
//    @Query("SELECT s, p.productName, gs.groupSupplierName FROM Supplier s INNER JOIN GroupSupplier gs ON s.groupSupplier.id = gs.id INNER JOIN Product p ON s.product.id = p.id WHERE s.status = 'ACTIVE' ORDER BY s.id DESC")
//    List<Map<String, Object>> getSuppliers();
//
//    @Query("SELECT s, p.productName, gs.groupSupplierName FROM Supplier s INNER JOIN GroupSupplier gs ON s.groupSupplier.id = gs.id INNER JOIN Product p ON s.product.id = p.id ORDER BY s.id DESC")
//    List<Map<String, Object>> getAllSuppliers();
//
//    @Query("SELECT s, p.productName, gs.groupSupplierName FROM Supplier s INNER JOIN GroupSupplier gs ON s.groupSupplier.id = gs.id INNER JOIN Product p ON s.product.id = p.id WHERE s.status = 'ACTIVE' AND s.phoneNumber LIKE %?1% ORDER BY s.id DESC")
//    List<Map<String, Object>> searchAllByPhoneNumber(String phoneNumber);
//
//    @Query("SELECT s, p.productName, gs.groupSupplierName FROM Supplier s INNER JOIN GroupSupplier gs ON s.groupSupplier.id = gs.id INNER JOIN Product p ON s.product.id = p.id WHERE s.status = 'ACTIVE' AND s.taxCode LIKE %?1% ORDER BY s.id DESC")
//    List<Map<String, Object>> searchAllByTaxCode(String taxCode);
//
//    @Query("SELECT s, p.productName, gs.groupSupplierName FROM Supplier s INNER JOIN GroupSupplier gs ON s.groupSupplier.id = gs.id INNER JOIN Product p ON s.product.id = p.id WHERE s.status = 'ACTIVE' AND s.supplierName LIKE %?1% ORDER BY s.id DESC")
//    List<Map<String, Object>> searchAllBySupplierNameLike(String supplierName);
//
//    @Query("SELECT s, p.productName, gs.groupSupplierName FROM Supplier s INNER JOIN GroupSupplier gs ON s.groupSupplier.id = gs.id INNER JOIN Product p ON s.product.id = p.id WHERE s.status = 'ACTIVE' AND s.groupSupplier.id = :id ORDER BY s.id DESC")
//    List<Map<String, Object>> searchByid(Long id);
//
//    @Query("SELECT s, p.productName, gs.groupSupplierName FROM Supplier s INNER JOIN GroupSupplier gs ON s.groupSupplier.id = gs.id INNER JOIN Product p ON s.product.id = p.id WHERE s.status = 'ACTIVE' ORDER BY s.id DESC")
//    List<Map<String, Object>> searchNoActive();
//
//    @Modifying
//    @Query("UPDATE Supplier s SET s.status = 'ACTIVE' WHERE s.id = :supplierId")
//    void deleteBySupplierId(Long supplierId);
}
