package com.qlyshopphone_backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user_address")
public class UserAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "address")
    private String address;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private AddressCities city;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private AddressCountries country;

    @ManyToOne
    @JoinColumn(name = "ward_id")
    private AddressWards ward;

    public UserAddress(String address, AddressWards ward, AddressCities city, AddressCountries country) {
        this.address = address;
        this.ward = ward;
        this.city = city;
        this.country = country;
    }
}
