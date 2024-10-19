package com.qlyshopphone_backend.repository;

import com.qlyshopphone_backend.model.Carts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Carts, Long> {
    @Modifying
    @Query("UPDATE Carts c SET c.deleteCart = TRUE WHERE c.id = :cartId")
    void deleteByCartId(Long cartId);

}
