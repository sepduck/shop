package com.qlyshopphone_backend.repository;


import com.qlyshopphone_backend.model.Suppliers;
import com.qlyshopphone_backend.model.enums.Status;
import com.qlyshopphone_backend.repository.projection.SupplierProjection;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<Suppliers, Long> {

    @Query("SELECT s FROM Suppliers s ORDER BY s.id DESC")
    List<SupplierProjection> getAllSuppliers(Pageable pageable);

    @Query("""
            SELECT s
            FROM Suppliers s
            WHERE s.status = :status
            AND s.phoneNumber LIKE %:phoneNumber%
            ORDER BY s.id DESC
            """)
    List<SupplierProjection> searchSuppliersByPhone(@Param("status") Status status,
                                                    @Param("phone") String phone);

    @Query("""
            SELECT s
            FROM Suppliers s
            WHERE s.status = :status
            AND s.taxCode LIKE %:taxCode%
            ORDER BY s.id DESC
            """)
    List<SupplierProjection> searchSuppliersByTaxCode(@Param("status") Status status,
                                                      @Param("taxCode") String taxCode);

    @Query("""
            SELECT s
            FROM Suppliers s
            WHERE s.status = :status
            AND s.name LIKE %:name%
            ORDER BY s.id DESC
            """)
    List<SupplierProjection> searchSuppliersByName(@Param("status") Status status,
                                                   @Param("name") String name);

    @Query("""
            SELECT s
            FROM Suppliers s
            WHERE s.status = :status
            AND s.groupSupplier.id = :id
            ORDER BY s.id DESC
            """)
    List<SupplierProjection> searchSupplierByGroupSupplierId(@Param("status") Status status,
                                                    @Param("id") Long id);

    @Query("""
            SELECT s
            FROM Suppliers s
            WHERE s.status = :status
            ORDER BY s.id DESC
            """)
    List<SupplierProjection> searchSupplierByStatus(@Param("status") Status status);
}
