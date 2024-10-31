package com.qlyshopphone_backend.repository;

import com.qlyshopphone_backend.model.ColorAttributes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColorAttributeRepository extends JpaRepository<ColorAttributes, Long> {
}
