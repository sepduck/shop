package com.qlyshopphone_backend.repository;

import com.qlyshopphone_backend.model.AddressWards;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressWardRepository extends JpaRepository<AddressWards, Long> {
}
