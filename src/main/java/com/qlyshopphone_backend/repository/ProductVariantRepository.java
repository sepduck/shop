package com.qlyshopphone_backend.repository;

import com.qlyshopphone_backend.model.ProductVariants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariants, Long> {
    @Query("""
            SELECT pv FROM ProductVariants pv
            WHERE pv.product.id = :productId
            AND pv.size.id = :sizeId
            AND pv.color.id = :colorId
            """)
    Optional<ProductVariants> findByPidAndSidAndCid(@Param("productId") Long productId,
                                   @Param("sizeId") Long sizeId,
                                   @Param("colorId") Long colorId);
}
