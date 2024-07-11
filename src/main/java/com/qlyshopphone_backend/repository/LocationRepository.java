package com.qlyshopphone_backend.repository;

import com.qlyshopphone_backend.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {
    boolean existsByLocationName(String locationName);
}
