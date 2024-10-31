package com.qlyshopphone_backend.repository;

import com.qlyshopphone_backend.model.GroupProducts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupProductRepository extends JpaRepository<GroupProducts, Long> {
}
