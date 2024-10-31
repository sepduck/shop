package com.qlyshopphone_backend.repository;

import com.qlyshopphone_backend.dto.response.ProductResponse;
import com.qlyshopphone_backend.model.Products;
import com.qlyshopphone_backend.model.enums.Status;
import com.qlyshopphone_backend.repository.projection.ProductProjection;
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
            SELECT p
            FROM Products p
            WHERE p.status = :status
            ORDER BY p.id DESC
            """)
    List<ProductProjection> getAllProducts(@Param("status") Status status);

    @Query("""
            SELECT p
            FROM Products p
            WHERE p.status = :status AND p.name LIKE %:productName%
            ORDER BY p.id DESC
            """)
    List<ProductProjection> searchProductsByName(@Param("status") Status status,
                                                 @Param("productName") String productName);

    @Query("""
            SELECT p FROM Products p
            WHERE p.status = :status AND p.id = :id
            ORDER BY p.id DESC
            """)
    List<ProductProjection> searchProductsById(@Param("status") Status status,
                                               @Param("id") Long id);

    @Query(""" 
            SELECT p
            FROM Products p
            WHERE p.status = :status AND p.groupProduct.id = :id
            ORDER BY p.id DESC
            """)
    List<ProductProjection> searchProductsByGroupProductId(@Param("status") Status status,
                                                           @Param("id") Long id);

    @Query("""
            SELECT p
            FROM Products p
            WHERE p.status = :status
            AND p.trademark.id = :id
            ORDER BY p.id DESC
            """)
    List<ProductProjection> searchProductsByTrademarkId(@Param("status") Status status,
                                                        @Param("id") Long id);

    @Query("""
            SELECT p
            FROM Products p
            WHERE p.status = :status
            ORDER BY p.id DESC
            """)
    List<ProductProjection> searchProductsByStatus(@Param("status") Status status);

    @Query("""
            SELECT p FROM Products p
            WHERE p.status = :status
            AND p.id = :id
            ORDER BY p.id DESC
            """)
    List<ProductProjection> findProductById(@Param("status") Status status,
                                        @Param("id") Long id);

    @Query("""
            SELECT p
            FROM Products p
            WHERE p.status = :status
            AND p.category.id = :id
            ORDER BY p.id DESC
            """)
    List<ProductProjection> searchProductsByCategoryId(@Param("status") Status status,
                                                         @Param("id") Long id);

}
