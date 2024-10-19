package com.qlyshopphone_backend.repository;

import com.qlyshopphone_backend.dto.response.ProductResponse;
import com.qlyshopphone_backend.model.Products;
import com.qlyshopphone_backend.model.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ProductRepository extends JpaRepository<Products, Long> {
    @Query("""
            SELECT p, gp.name, tm.name, l.name, pp.name, u.name, c.name
            FROM Products p
                JOIN GroupProducts gp ON p.groupProducts.id = gp.id
                JOIN Trademarks tm ON p.trademarks.id = tm.id
                JOIN Locations l ON p.locations.id = l.id
                JOIN Properties pp ON p.properties.id = pp.id
                JOIN Units u ON p.units.id = u.id
                JOIN Categories c ON p.categories.id = c.id
            WHERE p.status = :status
            ORDER BY p.id DESC
            """)
    List<ProductResponse> getAllProducts(@Param("status") Status status);

    @Query("SELECT p, gp.name, tm.name, l.name, pp.name, u.name, c.name FROM Products p INNER JOIN GroupProducts gp ON p.groupProducts.id = gp.id INNER JOIN Trademarks tm ON p.trademarks.id = tm.id INNER JOIN Locations l ON p.locations.id = l.id INNER JOIN Properties pp ON p.properties.id = pp.id INNER JOIN Units u ON p.units.id = u.id INNER JOIN Categories c ON p.categories.id = c.id WHERE p.status = 'ACTIVE' AND p.name LIKE %?1% ORDER BY p.id DESC")
    List<Map<String, Object>> searchAllByProductName(String productName);

    @Query("SELECT p, gp.name, tm.name, l.name, pp.name, u.name, c.name FROM Products p INNER JOIN GroupProducts gp ON p.groupProducts.id = gp.id INNER JOIN Trademarks tm ON p.trademarks.id = tm.id INNER JOIN Locations l ON p.locations.id = l.id INNER JOIN Properties pp ON p.properties.id = pp.id INNER JOIN Units u ON p.units.id = u.id INNER JOIN Categories c ON p.categories.id = c.id WHERE p.status = 'ACTIVE' AND p.id = :productId ORDER BY p.id DESC")
    List<Map<String, Object>> searchAllByProductId(Long productId);

    @Query("SELECT p, gp.name, tm.name, l.name, pp.name, u.name, c.name FROM Products p INNER JOIN GroupProducts gp ON p.groupProducts.id = gp.id INNER JOIN Trademarks tm ON p.trademarks.id = tm.id INNER JOIN Locations l ON p.locations.id = l.id INNER JOIN Properties pp ON p.properties.id = pp.id INNER JOIN Units u ON p.units.id = u.id INNER JOIN Categories c ON p.categories.id = c.id WHERE p.status = 'ACTIVE' AND p.groupProducts.id = :id ORDER BY p.id DESC")
    List<Map<String, Object>> searchid(Long id);

    @Query("SELECT p, gp.name, tm.name, l.name, pp.name, u.name, c.name FROM Products p INNER JOIN GroupProducts gp ON p.groupProducts.id = gp.id INNER JOIN Trademarks tm ON p.trademarks.id = tm.id INNER JOIN Locations l ON p.locations.id = l.id INNER JOIN Properties pp ON p.properties.id = pp.id INNER JOIN Units u ON p.units.id = u.id INNER JOIN Categories c ON p.categories.id = c.id WHERE p.status = 'ACTIVE' AND p.trademarks.id = :id ORDER BY p.id DESC")
    List<Map<String, Object>> searchid(int id);

    @Query("SELECT p, gp.name, tm.name, l.name, pp.name, u.name, c.name FROM Products p INNER JOIN GroupProducts gp ON p.groupProducts.id = gp.id INNER JOIN Trademarks tm ON p.trademarks.id = tm.id INNER JOIN Locations l ON p.locations.id = l.id INNER JOIN Properties pp ON p.properties.id = pp.id INNER JOIN Units u ON p.units.id = u.id INNER JOIN Categories c ON p.categories.id = c.id WHERE p.status = 'ACTIVE' AND p.inventory < 0 ORDER BY p.id DESC")
    List<Map<String, Object>> searchBelowInventoryThreshold();

    @Query("SELECT p, gp.name, tm.name, l.name, pp.name, u.name, c.name FROM Products p INNER JOIN GroupProducts gp ON p.groupProducts.id = gp.id INNER JOIN Trademarks tm ON p.trademarks.id = tm.id INNER JOIN Locations l ON p.locations.id = l.id INNER JOIN Properties pp ON p.properties.id = pp.id INNER JOIN Units u ON p.units.id = u.id INNER JOIN Categories c ON p.categories.id = c.id WHERE p.status = 'ACTIVE' AND p.inventory > 200 ORDER BY p.id DESC")
    List<Map<String, Object>> searchExceedingInventoryLimit();

    @Query("SELECT p, gp.name, tm.name, l.name, pp.name, u.name, c.name FROM Products p INNER JOIN GroupProducts gp ON p.groupProducts.id = gp.id INNER JOIN Trademarks tm ON p.trademarks.id = tm.id INNER JOIN Locations l ON p.locations.id = l.id INNER JOIN Properties pp ON p.properties.id = pp.id INNER JOIN Units u ON p.units.id = u.id INNER JOIN Categories c ON p.categories.id = c.id WHERE p.status = 'ACTIVE' AND p.inventory > 0 ORDER BY p.id DESC")
    List<Map<String, Object>> searchStockAvailable();

    @Query("SELECT p, gp.name, tm.name, l.name, pp.name, u.name, c.name FROM Products p INNER JOIN GroupProducts gp ON p.groupProducts.id = gp.id INNER JOIN Trademarks tm ON p.trademarks.id = tm.id INNER JOIN Locations l ON p.locations.id = l.id INNER JOIN Properties pp ON p.properties.id = pp.id INNER JOIN Units u ON p.units.id = u.id INNER JOIN Categories c ON p.categories.id = c.id WHERE p.status = 'ACTIVE' AND p.inventory = 0 ORDER BY p.id DESC")
    List<Map<String, Object>> searchNoInventoryAvailable();

    @Query("SELECT p, gp.name, tm.name, l.name, pp.name, u.name, c.name FROM Products p INNER JOIN GroupProducts gp ON p.groupProducts.id = gp.id INNER JOIN Trademarks tm ON p.trademarks.id = tm.id INNER JOIN Locations l ON p.locations.id = l.id INNER JOIN Properties pp ON p.properties.id = pp.id INNER JOIN Units u ON p.units.id = u.id INNER JOIN Categories c ON p.categories.id = c.id WHERE p.status = 'ACTIVE' ORDER BY p.id DESC")
    List<Map<String, Object>> searchNoActive();

    @Query("SELECT p, gp.name, tm.name, l.name, pp.name, u.name, c.name FROM Products p INNER JOIN GroupProducts gp ON p.groupProducts.id = gp.id INNER JOIN Trademarks tm ON p.trademarks.id = tm.id INNER JOIN Locations l ON p.locations.id = l.id INNER JOIN Properties pp ON p.properties.id = pp.id INNER JOIN Units u ON p.units.id = u.id INNER JOIN Categories c ON p.categories.id = c.id ORDER BY p.id DESC")
    List<Map<String, Object>> searchActive();

    @Query("SELECT p, gp.name, tm.name, l.name, pp.name, u.name, c.name FROM Products p INNER JOIN GroupProducts gp ON p.groupProducts.id = gp.id INNER JOIN Trademarks tm ON p.trademarks.id = tm.id INNER JOIN Locations l ON p.locations.id = l.id INNER JOIN Properties pp ON p.properties.id = pp.id INNER JOIN Units u ON p.units.id = u.id INNER JOIN Categories c ON p.categories.id = c.id WHERE p.status = 'ACTIVE' AND p.id = :productId ORDER BY p.id DESC")
    Map<String, Object> getDetailProductId(Long productId);

    @Query("SELECT p, gp.name, tm.name, l.name, pp.name, u.name, c.name FROM Products p INNER JOIN GroupProducts gp ON p.groupProducts.id = gp.id INNER JOIN Trademarks tm ON p.trademarks.id = tm.id INNER JOIN Locations l ON p.locations.id = l.id INNER JOIN Properties pp ON p.properties.id = pp.id INNER JOIN Units u ON p.units.id = u.id INNER JOIN Categories c ON p.categories.id = c.id WHERE p.status = 'ACTIVE' AND p.locations.id = :id ORDER BY p.id DESC")
    List<Map<String, Object>> searchByid(Long id);

    @Query("SELECT p, gp.name, tm.name, l.name, pp.name, u.name, c.name FROM Products p INNER JOIN GroupProducts gp ON p.groupProducts.id = gp.id INNER JOIN Trademarks tm ON p.trademarks.id = tm.id INNER JOIN Locations l ON p.locations.id = l.id INNER JOIN Properties pp ON p.properties.id = pp.id INNER JOIN Units u ON p.units.id = u.id INNER JOIN Categories c ON p.categories.id = c.id WHERE p.status = 'ACTIVE' AND p.categories.id = 1 ORDER BY p.id DESC")
    List<Map<String, Object>> searchCategory1();

    @Query("SELECT p, gp.name, tm.name, l.name, pp.name, u.name, c.name FROM Products p INNER JOIN GroupProducts gp ON p.groupProducts.id = gp.id INNER JOIN Trademarks tm ON p.trademarks.id = tm.id INNER JOIN Locations l ON p.locations.id = l.id INNER JOIN Properties pp ON p.properties.id = pp.id INNER JOIN Units u ON p.units.id = u.id INNER JOIN Categories c ON p.categories.id = c.id WHERE p.status = 'ACTIVE' AND p.categories.id = 2 ORDER BY p.id DESC")
    List<Map<String, Object>> searchCategory2();

    @Query("SELECT p, gp.name, tm.name, l.name, pp.name, u.name, c.name FROM Products p INNER JOIN GroupProducts gp ON p.groupProducts.id = gp.id INNER JOIN Trademarks tm ON p.trademarks.id = tm.id INNER JOIN Locations l ON p.locations.id = l.id INNER JOIN Properties pp ON p.properties.id = pp.id INNER JOIN Units u ON p.units.id = u.id INNER JOIN Categories c ON p.categories.id = c.id WHERE p.status = 'ACTIVE' AND p.categories.id = 3 ORDER BY p.id DESC")
    List<Map<String, Object>> searchCategory3();

    @Query("SELECT p, gp.name, tm.name, l.name, pp.name, u.name, c.name FROM Products p INNER JOIN GroupProducts gp ON p.groupProducts.id = gp.id INNER JOIN Trademarks tm ON p.trademarks.id = tm.id INNER JOIN Locations l ON p.locations.id = l.id INNER JOIN Properties pp ON p.properties.id = pp.id INNER JOIN Units u ON p.units.id = u.id INNER JOIN Categories c ON p.categories.id = c.id WHERE p.status = 'ACTIVE' AND p.directSales = true ORDER BY p.id DESC")
    List<Map<String, Object>> searchDirectSales();

    @Query("SELECT p, gp.name, tm.name, l.name, pp.name, u.name, c.name FROM Products p INNER JOIN GroupProducts gp ON p.groupProducts.id = gp.id INNER JOIN Trademarks tm ON p.trademarks.id = tm.id INNER JOIN Locations l ON p.locations.id = l.id INNER JOIN Properties pp ON p.properties.id = pp.id INNER JOIN Units u ON p.units.id = u.id INNER JOIN Categories c ON p.categories.id = c.id WHERE p.status = 'ACTIVE' AND p.directSales ORDER BY p.id DESC")
    List<Map<String, Object>> searchNoDirectSales();

    @Modifying
    @Query("UPDATE Products p SET p.status = 'ACTIVE' WHERE p.id = :productId")
    void deleteProductById(Long productId);
}
