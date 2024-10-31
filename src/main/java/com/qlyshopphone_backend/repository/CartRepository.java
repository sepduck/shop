package com.qlyshopphone_backend.repository;

import com.qlyshopphone_backend.model.Carts;
import com.qlyshopphone_backend.model.enums.Status;
import com.qlyshopphone_backend.repository.projection.CartProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Carts, Long> {
    @Query("""
            SELECT c FROM Carts c
            WHERE c.user.id = :userId
            AND c.productVariant.id = :productVariantId
            AND c.sold = FALSE
            AND c.status = :status
            """)
    Optional<Carts> findByUserIdAndProductVariantId(@Param("userId") Long userId,
                                      @Param("productVariantId") Long productVariantId,
                                      @Param("status") Status status);

    @Query("""
            SELECT c FROM Carts c
            WHERE c.user.id = :userId
            AND c.id = :cartId
            AND c.sold = FALSE
            AND c.status = :status
            """)
    Optional<Carts> findByUserAndId(@Param("userId") Long userId,
                                    @Param("cartId") Long cartId,
                                    @Param("status") Status status);

    @Query("""
            SELECT c FROM Carts c
            WHERE c.user.id = :userId
            AND c.sold = FALSE
            AND c.status = :activeStatus
            ORDER BY c.id DESC
            """)
    List<CartProjection> findUserActiveCarts(@Param("userId") Long userId, @Param("activeStatus") Status activeStatus);

    @Query("SELECT c FROM Carts c WHERE c.user.id = :userId AND c.id IN :cartIds")
    List<Carts> findAllByUserIdAndCartIdIn(@Param("userId") Long userId, @Param("cartIds") List<Long> cartIds);

}
