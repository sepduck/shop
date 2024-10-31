package com.qlyshopphone_backend.repository;

import com.qlyshopphone_backend.model.SizeAttributes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SizeAttributeRepository extends JpaRepository<SizeAttributes, Long> {
}
