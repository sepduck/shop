package com.qlyshopphone_backend.repository;


import com.qlyshopphone_backend.model.Suppliers;
import com.qlyshopphone_backend.repository.projection.SupplierProjection;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<Suppliers, Long> {

    @Query("SELECT s FROM Suppliers s ORDER BY s.id DESC")
    List<SupplierProjection> getAllSuppliers(Pageable pageable);

//    @Query("SELECT s, p.name, gs.name FROM Suppliers s JOIN GroupSuppliers gs ON s.groupSupplier.id = gs.id JOIN Products p ON s.product.id = p.id WHERE s.status = 'ACTIVE' AND s.phoneNumber LIKE %:phoneNumber% ORDER BY s.id DESC")
//    List<Suppliers> searchAllByPhoneNumber(@Param("phoneNumber") String phoneNumber);
//
//    @Query("SELECT s, p.name, gs.name FROM Suppliers s JOIN GroupSuppliers gs ON s.groupSupplier.id = gs.id JOIN Products p ON s.product.id = p.id WHERE s.status = 'ACTIVE' AND s.taxCode LIKE %:taxCode% ORDER BY s.id DESC")
//    List<Suppliers> searchAllByTaxCode(@Param("taxCode") String taxCode);

//    @Query("SELECT s, p.name, gs.name FROM Suppliers s JOIN GroupSuppliers gs ON s.groupSupplier.id = gs.id JOIN Products p ON s.product.id = p.id WHERE s.status = 'ACTIVE' AND s.name LIKE %:name% ORDER BY s.id DESC")
//    List<Suppliers> searchAllBySupplierNameLike(@Param("name") String name);

//    @Query("SELECT s, p.name, gs.name FROM Suppliers s JOIN GroupSuppliers gs ON s.groupSupplier.id = gs.id JOIN Products p ON s.product.id = p.id WHERE s.status = 'ACTIVE' AND s.groupSupplier.id = :id ORDER BY s.id DESC")
//    List<Suppliers> searchByid(@Param("id") Long id);

}
