package com.qlyshopphone_backend.repository;

import com.qlyshopphone_backend.model.InventoryHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryHistoryRepository extends JpaRepository<InventoryHistory, Integer> {
}
