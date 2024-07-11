package com.qlyshopphone_backend.repository;

import com.qlyshopphone_backend.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Integer> {
    @Query(value = "SELECT *\n" +
            "FROM purchase\n" +
            "WHERE purchase_date >= :startDate\n" +
            "  AND purchase_date <= :endDate", nativeQuery = true)
    List<Purchase> findAllPurchasesBetweenDates(LocalDateTime startDate, LocalDateTime endDate);

    @Query(value = "SELECT *\n" +
            "FROM purchase\n" +
            "WHERE DATE(purchase_date) = CURRENT_DATE", nativeQuery = true)
    List<Purchase> findByToday();


}
