package com.qlyshopphone_backend.repository;

import com.qlyshopphone_backend.model.Orders;
import com.qlyshopphone_backend.model.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
    @Query("""
            SELECT o FROM Orders o
            WHERE o.status = :status
            AND o.id = :orderId
            """)
    Optional<Orders> findOrderByIdAnhStatus(@Param("orderId") Long orderId,
                                    @Param("status") OrderStatus status);

    @Query(value = """
            SELECT o
            FROM Orders o
            WHERE o.createdAt >= :start
            AND o.createdAt < :end
            AND o.status = :status
            """)
    List<Orders> findByToday(@Param("start") LocalDateTime start,
                             @Param("end") LocalDateTime end,
                             @Param("status") OrderStatus status);



}
