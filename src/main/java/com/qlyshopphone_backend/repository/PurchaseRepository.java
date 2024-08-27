package com.qlyshopphone_backend.repository;

import com.qlyshopphone_backend.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    @Query(value = "SELECT p FROM Purchase p WHERE p.purchaseDate >= :startDate AND p.purchaseDate <= :endDate")
    List<Purchase> findAllPurchasesBetweenDates(LocalDateTime startDate, LocalDateTime endDate);

    @Query(value = "SELECT p FROM Purchase p WHERE DATE(p.purchaseDate) = CURRENT_DATE")
    List<Purchase> findByToday();


}
