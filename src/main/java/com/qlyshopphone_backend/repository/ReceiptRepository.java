package com.qlyshopphone_backend.repository;

import com.qlyshopphone_backend.model.Receipts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipts, Long> {
}
