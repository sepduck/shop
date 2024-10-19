package com.qlyshopphone_backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class CustomerInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String phone;

    private String address;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;

    private boolean deleteCustomerInfo;
}
