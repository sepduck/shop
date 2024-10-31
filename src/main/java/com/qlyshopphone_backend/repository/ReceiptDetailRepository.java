package com.qlyshopphone_backend.repository;

import com.qlyshopphone_backend.model.ReceiptDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceiptDetailRepository extends JpaRepository<ReceiptDetails, Long> {
    @Query("""
            SELECT rd
            FROM ReceiptDetails rd
            WHERE rd.product.id = :productId
            AND rd.receipt.id = :receiptId
            """)
    ReceiptDetails findByReceiptAndProduct(@Param("productId") Long productId,
                                           @Param("receiptId") Long receiptId);
}
