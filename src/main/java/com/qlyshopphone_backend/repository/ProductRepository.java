package com.qlyshopphone_backend.repository;

import com.qlyshopphone_backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p, p.productId AS id, gp.groupProductName, tm.trademarkName, l.locationName, pp.propertiesName, u.unitName, c.categoryName FROM Product p INNER JOIN GroupProduct gp ON p.groupProduct.groupProductId = gp.groupProductId INNER JOIN Trademark tm ON p.trademark.trademarkId = tm.trademarkId INNER JOIN Location l ON p.location.locationId = l.locationId INNER JOIN Properties pp ON p.properties.propertiesId = pp.propertiesId INNER JOIN Unit u ON p.unit.unitId = u.unitId INNER JOIN Category c ON p.category.categoryId = c.categoryId WHERE p.deleteProduct = false ORDER BY id DESC")
    List<Map<String, Object>> getAllProducts();

    @Query("SELECT p, p.productId AS id, gp.groupProductName, tm.trademarkName, l.locationName, pp.propertiesName, u.unitName, c.categoryName FROM Product p INNER JOIN GroupProduct gp ON p.groupProduct.groupProductId = gp.groupProductId INNER JOIN Trademark tm ON p.trademark.trademarkId = tm.trademarkId INNER JOIN Location l ON p.location.locationId = l.locationId INNER JOIN Properties pp ON p.properties.propertiesId = pp.propertiesId INNER JOIN Unit u ON p.unit.unitId = u.unitId INNER JOIN Category c ON p.category.categoryId = c.categoryId WHERE p.deleteProduct = false AND p.productName LIKE %?1% ORDER BY id DESC")
    List<Map<String, Object>> searchAllByProductName(String productName);

    @Query("SELECT p, p.productId AS id, gp.groupProductName, tm.trademarkName, l.locationName, pp.propertiesName, u.unitName, c.categoryName FROM Product p INNER JOIN GroupProduct gp ON p.groupProduct.groupProductId = gp.groupProductId INNER JOIN Trademark tm ON p.trademark.trademarkId = tm.trademarkId INNER JOIN Location l ON p.location.locationId = l.locationId INNER JOIN Properties pp ON p.properties.propertiesId = pp.propertiesId INNER JOIN Unit u ON p.unit.unitId = u.unitId INNER JOIN Category c ON p.category.categoryId = c.categoryId WHERE p.deleteProduct = false AND p.productId = :productId ORDER BY id DESC")
    List<Map<String, Object>> searchAllByProductId(Long productId);

    @Query("SELECT p, p.productId AS id, gp.groupProductName, tm.trademarkName, l.locationName, pp.propertiesName, u.unitName, c.categoryName FROM Product p INNER JOIN GroupProduct gp ON p.groupProduct.groupProductId = gp.groupProductId INNER JOIN Trademark tm ON p.trademark.trademarkId = tm.trademarkId INNER JOIN Location l ON p.location.locationId = l.locationId INNER JOIN Properties pp ON p.properties.propertiesId = pp.propertiesId INNER JOIN Unit u ON p.unit.unitId = u.unitId INNER JOIN Category c ON p.category.categoryId = c.categoryId WHERE p.deleteProduct = false AND p.groupProduct.groupProductId = :groupProductId ORDER BY p.productId DESC")
    List<Map<String, Object>> searchGroupProductId(Long groupProductId);

    @Query("SELECT p, p.productId AS id, gp.groupProductName, tm.trademarkName, l.locationName, pp.propertiesName, u.unitName, c.categoryName FROM Product p INNER JOIN GroupProduct gp ON p.groupProduct.groupProductId = gp.groupProductId INNER JOIN Trademark tm ON p.trademark.trademarkId = tm.trademarkId INNER JOIN Location l ON p.location.locationId = l.locationId INNER JOIN Properties pp ON p.properties.propertiesId = pp.propertiesId INNER JOIN Unit u ON p.unit.unitId = u.unitId INNER JOIN Category c ON p.category.categoryId = c.categoryId WHERE p.deleteProduct = false AND p.trademark.trademarkId = :trademarkId ORDER BY p.productId DESC")
    List<Map<String, Object>> searchTrademarkId(int trademarkId);

    @Query("SELECT p, p.productId AS id, gp.groupProductName, tm.trademarkName, l.locationName, pp.propertiesName, u.unitName, c.categoryName FROM Product p INNER JOIN GroupProduct gp ON p.groupProduct.groupProductId = gp.groupProductId INNER JOIN Trademark tm ON p.trademark.trademarkId = tm.trademarkId INNER JOIN Location l ON p.location.locationId = l.locationId INNER JOIN Properties pp ON p.properties.propertiesId = pp.propertiesId INNER JOIN Unit u ON p.unit.unitId = u.unitId INNER JOIN Category c ON p.category.categoryId = c.categoryId WHERE p.deleteProduct = false AND p.inventory < 0 ORDER BY id DESC")
    List<Map<String, Object>> searchBelowInventoryThreshold();

    @Query("SELECT p, p.productId AS id, gp.groupProductName, tm.trademarkName, l.locationName, pp.propertiesName, u.unitName, c.categoryName FROM Product p INNER JOIN GroupProduct gp ON p.groupProduct.groupProductId = gp.groupProductId INNER JOIN Trademark tm ON p.trademark.trademarkId = tm.trademarkId INNER JOIN Location l ON p.location.locationId = l.locationId INNER JOIN Properties pp ON p.properties.propertiesId = pp.propertiesId INNER JOIN Unit u ON p.unit.unitId = u.unitId INNER JOIN Category c ON p.category.categoryId = c.categoryId WHERE p.deleteProduct = false AND p.inventory > 200 ORDER BY id DESC")
    List<Map<String, Object>> searchExceedingInventoryLimit();

    @Query("SELECT p, p.productId AS id, gp.groupProductName, tm.trademarkName, l.locationName, pp.propertiesName, u.unitName, c.categoryName FROM Product p INNER JOIN GroupProduct gp ON p.groupProduct.groupProductId = gp.groupProductId INNER JOIN Trademark tm ON p.trademark.trademarkId = tm.trademarkId INNER JOIN Location l ON p.location.locationId = l.locationId INNER JOIN Properties pp ON p.properties.propertiesId = pp.propertiesId INNER JOIN Unit u ON p.unit.unitId = u.unitId INNER JOIN Category c ON p.category.categoryId = c.categoryId WHERE p.deleteProduct = false AND p.inventory > 0 ORDER BY id DESC")
    List<Map<String, Object>> searchStockAvailable();

    @Query("SELECT p, p.productId AS id, gp.groupProductName, tm.trademarkName, l.locationName, pp.propertiesName, u.unitName, c.categoryName FROM Product p INNER JOIN GroupProduct gp ON p.groupProduct.groupProductId = gp.groupProductId INNER JOIN Trademark tm ON p.trademark.trademarkId = tm.trademarkId INNER JOIN Location l ON p.location.locationId = l.locationId INNER JOIN Properties pp ON p.properties.propertiesId = pp.propertiesId INNER JOIN Unit u ON p.unit.unitId = u.unitId INNER JOIN Category c ON p.category.categoryId = c.categoryId WHERE p.deleteProduct = false AND p.inventory = 0 ORDER BY id DESC")
    List<Map<String, Object>> searchNoInventoryAvailable();

    @Query("SELECT p, p.productId AS id, gp.groupProductName, tm.trademarkName, l.locationName, pp.propertiesName, u.unitName, c.categoryName FROM Product p INNER JOIN GroupProduct gp ON p.groupProduct.groupProductId = gp.groupProductId INNER JOIN Trademark tm ON p.trademark.trademarkId = tm.trademarkId INNER JOIN Location l ON p.location.locationId = l.locationId INNER JOIN Properties pp ON p.properties.propertiesId = pp.propertiesId INNER JOIN Unit u ON p.unit.unitId = u.unitId INNER JOIN Category c ON p.category.categoryId = c.categoryId WHERE p.deleteProduct = true ORDER BY id DESC")
    List<Map<String, Object>> searchNoActive();

    @Query("SELECT p, p.productId AS id, gp.groupProductName, tm.trademarkName, l.locationName, pp.propertiesName, u.unitName, c.categoryName FROM Product p INNER JOIN GroupProduct gp ON p.groupProduct.groupProductId = gp.groupProductId INNER JOIN Trademark tm ON p.trademark.trademarkId = tm.trademarkId INNER JOIN Location l ON p.location.locationId = l.locationId INNER JOIN Properties pp ON p.properties.propertiesId = pp.propertiesId INNER JOIN Unit u ON p.unit.unitId = u.unitId INNER JOIN Category c ON p.category.categoryId = c.categoryId ORDER BY id DESC")
    List<Map<String, Object>> searchActive();

    @Query("SELECT p, p.productId AS id, gp.groupProductName, tm.trademarkName, l.locationName, pp.propertiesName, u.unitName, c.categoryName FROM Product p INNER JOIN GroupProduct gp ON p.groupProduct.groupProductId = gp.groupProductId INNER JOIN Trademark tm ON p.trademark.trademarkId = tm.trademarkId INNER JOIN Location l ON p.location.locationId = l.locationId INNER JOIN Properties pp ON p.properties.propertiesId = pp.propertiesId INNER JOIN Unit u ON p.unit.unitId = u.unitId INNER JOIN Category c ON p.category.categoryId = c.categoryId WHERE p.deleteProduct = false AND p.productId = :productId ORDER BY id DESC")
    Map<String, Object> getDetailProductId(Long productId);

    @Query("SELECT p, p.productId AS id, gp.groupProductName, tm.trademarkName, l.locationName, pp.propertiesName, u.unitName, c.categoryName FROM Product p INNER JOIN GroupProduct gp ON p.groupProduct.groupProductId = gp.groupProductId INNER JOIN Trademark tm ON p.trademark.trademarkId = tm.trademarkId INNER JOIN Location l ON p.location.locationId = l.locationId INNER JOIN Properties pp ON p.properties.propertiesId = pp.propertiesId INNER JOIN Unit u ON p.unit.unitId = u.unitId INNER JOIN Category c ON p.category.categoryId = c.categoryId WHERE p.deleteProduct = false AND p.location.locationId = :locationId ORDER BY id DESC")
    List<Map<String, Object>> searchByLocationId(Long locationId);

    @Query("SELECT p, p.productId AS id, gp.groupProductName, tm.trademarkName, l.locationName, pp.propertiesName, u.unitName, c.categoryName FROM Product p INNER JOIN GroupProduct gp ON p.groupProduct.groupProductId = gp.groupProductId INNER JOIN Trademark tm ON p.trademark.trademarkId = tm.trademarkId INNER JOIN Location l ON p.location.locationId = l.locationId INNER JOIN Properties pp ON p.properties.propertiesId = pp.propertiesId INNER JOIN Unit u ON p.unit.unitId = u.unitId INNER JOIN Category c ON p.category.categoryId = c.categoryId WHERE p.deleteProduct = false AND p.category.categoryId = 1 ORDER BY id DESC")
    List<Map<String, Object>> searchCategory1();

    @Query("SELECT p, p.productId AS id, gp.groupProductName, tm.trademarkName, l.locationName, pp.propertiesName, u.unitName, c.categoryName FROM Product p INNER JOIN GroupProduct gp ON p.groupProduct.groupProductId = gp.groupProductId INNER JOIN Trademark tm ON p.trademark.trademarkId = tm.trademarkId INNER JOIN Location l ON p.location.locationId = l.locationId INNER JOIN Properties pp ON p.properties.propertiesId = pp.propertiesId INNER JOIN Unit u ON p.unit.unitId = u.unitId INNER JOIN Category c ON p.category.categoryId = c.categoryId WHERE p.deleteProduct = false AND p.category.categoryId = 2 ORDER BY id DESC")
    List<Map<String, Object>> searchCategory2();

    @Query("SELECT p, p.productId AS id, gp.groupProductName, tm.trademarkName, l.locationName, pp.propertiesName, u.unitName, c.categoryName FROM Product p INNER JOIN GroupProduct gp ON p.groupProduct.groupProductId = gp.groupProductId INNER JOIN Trademark tm ON p.trademark.trademarkId = tm.trademarkId INNER JOIN Location l ON p.location.locationId = l.locationId INNER JOIN Properties pp ON p.properties.propertiesId = pp.propertiesId INNER JOIN Unit u ON p.unit.unitId = u.unitId INNER JOIN Category c ON p.category.categoryId = c.categoryId WHERE p.deleteProduct = false AND p.category.categoryId = 3 ORDER BY id DESC")
    List<Map<String, Object>> searchCategory3();

    @Query("SELECT p, p.productId AS id, gp.groupProductName, tm.trademarkName, l.locationName, pp.propertiesName, u.unitName, c.categoryName FROM Product p INNER JOIN GroupProduct gp ON p.groupProduct.groupProductId = gp.groupProductId INNER JOIN Trademark tm ON p.trademark.trademarkId = tm.trademarkId INNER JOIN Location l ON p.location.locationId = l.locationId INNER JOIN Properties pp ON p.properties.propertiesId = pp.propertiesId INNER JOIN Unit u ON p.unit.unitId = u.unitId INNER JOIN Category c ON p.category.categoryId = c.categoryId WHERE p.deleteProduct = false AND p.directSales = true ORDER BY id DESC")
    List<Map<String, Object>> searchDirectSales();

    @Query("SELECT p, p.productId AS id, gp.groupProductName, tm.trademarkName, l.locationName, pp.propertiesName, u.unitName, c.categoryName FROM Product p INNER JOIN GroupProduct gp ON p.groupProduct.groupProductId = gp.groupProductId INNER JOIN Trademark tm ON p.trademark.trademarkId = tm.trademarkId INNER JOIN Location l ON p.location.locationId = l.locationId INNER JOIN Properties pp ON p.properties.propertiesId = pp.propertiesId INNER JOIN Unit u ON p.unit.unitId = u.unitId INNER JOIN Category c ON p.category.categoryId = c.categoryId WHERE p.deleteProduct = false AND p.directSales = false ORDER BY id DESC")
    List<Map<String, Object>> searchNoDirectSales();

    @Modifying
    @Query("UPDATE Product p SET p.deleteProduct = TRUE WHERE p.productId = :productId")
    void deleteProductById(Long productId);
}
