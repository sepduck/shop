package com.qlyshopphone_backend.repository;

import com.qlyshopphone_backend.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    @Query("SELECT s, s.supplierId AS id, p.productName, gs.groupSupplierName FROM Supplier s INNER JOIN GroupSupplier gs ON s.groupSupplier.groupSupplierId = gs.groupSupplierId INNER JOIN Product p ON s.product.productId = p.productId WHERE s.deleteSupplier = false ORDER BY id DESC")
    List<Map<String, Object>> getSuppliers();

    @Query("SELECT s, s.supplierId AS id, p.productName, gs.groupSupplierName FROM Supplier s INNER JOIN GroupSupplier gs ON s.groupSupplier.groupSupplierId = gs.groupSupplierId INNER JOIN Product p ON s.product.productId = p.productId ORDER BY id DESC")
    List<Map<String, Object>> getAllSuppliers();

    @Query("SELECT s, s.supplierId AS id, p.productName, gs.groupSupplierName FROM Supplier s INNER JOIN GroupSupplier gs ON s.groupSupplier.groupSupplierId = gs.groupSupplierId INNER JOIN Product p ON s.product.productId = p.productId WHERE s.deleteSupplier = false AND s.phoneNumber LIKE %?1% ORDER BY id DESC")
    List<Map<String, Object>> searchAllByPhoneNumber(String phoneNumber);

    @Query("SELECT s, s.supplierId AS id, p.productName, gs.groupSupplierName FROM Supplier s INNER JOIN GroupSupplier gs ON s.groupSupplier.groupSupplierId = gs.groupSupplierId INNER JOIN Product p ON s.product.productId = p.productId WHERE s.deleteSupplier = false AND s.taxCode LIKE %?1% ORDER BY id DESC")
    List<Map<String, Object>> searchAllByTaxCode(String taxCode);

    @Query("SELECT s, s.supplierId AS id, p.productName, gs.groupSupplierName FROM Supplier s INNER JOIN GroupSupplier gs ON s.groupSupplier.groupSupplierId = gs.groupSupplierId INNER JOIN Product p ON s.product.productId = p.productId WHERE s.deleteSupplier = false AND s.supplierName LIKE %?1% ORDER BY id DESC")
    List<Map<String, Object>> searchAllBySupplierNameLike(String supplierName);

    @Query("SELECT s, s.supplierId AS id, p.productName, gs.groupSupplierName FROM Supplier s INNER JOIN GroupSupplier gs ON s.groupSupplier.groupSupplierId = gs.groupSupplierId INNER JOIN Product p ON s.product.productId = p.productId WHERE s.deleteSupplier = false AND s.groupSupplier.groupSupplierId = :groupSupplierId ORDER BY id DESC")
    List<Map<String, Object>> searchByGroupSupplierId(Long groupSupplierId);

    @Query("SELECT s, s.supplierId AS id, p.productName, gs.groupSupplierName FROM Supplier s INNER JOIN GroupSupplier gs ON s.groupSupplier.groupSupplierId = gs.groupSupplierId INNER JOIN Product p ON s.product.productId = p.productId WHERE s.deleteSupplier = true ORDER BY id DESC")
    List<Map<String, Object>> searchNoActive();

    @Modifying
    @Query("UPDATE Supplier s SET s.deleteSupplier = TRUE WHERE s.supplierId = :supplierId")
    void deleteBySupplierId(Long supplierId);
}
