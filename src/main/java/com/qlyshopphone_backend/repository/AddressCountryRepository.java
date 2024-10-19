package com.qlyshopphone_backend.repository;

import com.qlyshopphone_backend.model.AddressCountries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressCountryRepository extends JpaRepository<AddressCountries, Long> {
}
