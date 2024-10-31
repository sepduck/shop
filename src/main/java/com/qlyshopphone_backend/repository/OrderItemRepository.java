package com.qlyshopphone_backend.repository;

import com.qlyshopphone_backend.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    @Query("""
            SELECT oi
            FROM OrderItem oi
            WHERE oi.order.id = :orderId
            """)
    List<OrderItem> findByOrderId(Long orderId);
}
