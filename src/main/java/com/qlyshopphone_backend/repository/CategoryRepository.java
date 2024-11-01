package com.qlyshopphone_backend.repository;

import com.qlyshopphone_backend.model.Categories;
import com.qlyshopphone_backend.repository.projection.ProductAttributeProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Categories, Long> {
    @Query("""
            SELECT c
            FROM Categories c
            """)
    List<ProductAttributeProjection> findAllCategories();
}
