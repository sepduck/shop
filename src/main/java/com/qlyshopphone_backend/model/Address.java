package com.qlyshopphone_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String street;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private AddressCities city;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private AddressCountries country;

    @ManyToOne
    @JoinColumn(name = "ward_id")
    private AddressWards ward;

    public Address(String address, AddressWards ward, AddressCities city, AddressCountries country) {
        this.street = address;
        this.ward = ward;
        this.city = city;
        this.country = country;
    }
}
