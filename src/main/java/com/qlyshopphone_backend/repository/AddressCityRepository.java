package com.qlyshopphone_backend.repository;

import com.qlyshopphone_backend.model.AddressCities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressCityRepository extends JpaRepository<AddressCities, Long> {
}
