package com.qlyshopphone_backend.repository;

import com.qlyshopphone_backend.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    @Modifying
    @Query("UPDATE Cart c SET c.deleteCart = TRUE WHERE c.cartId = :cartId")
    void deleteByCartId(Long cartId);

}
