package com.qlyshopphone_backend.repository;

import com.qlyshopphone_backend.model.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Long> {
}
