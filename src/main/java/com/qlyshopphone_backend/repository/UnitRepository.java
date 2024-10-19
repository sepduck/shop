package com.qlyshopphone_backend.repository;

import com.qlyshopphone_backend.model.Units;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnitRepository extends JpaRepository<Units, Long> {
}
