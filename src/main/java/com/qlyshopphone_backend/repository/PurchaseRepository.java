package com.qlyshopphone_backend.repository;

import com.qlyshopphone_backend.model.Purchases;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchases, Long> {
    @Query(value = "SELECT p FROM Purchases p WHERE p.purchaseDate >= :startDate AND p.purchaseDate <= :endDate")
    List<Purchases> findAllPurchasesBetweenDates(LocalDateTime startDate, LocalDateTime endDate);

    @Query(value = "SELECT p FROM Purchases p WHERE DATE(p.purchaseDate) = CURRENT_DATE")
    List<Purchases> findByToday();


}
