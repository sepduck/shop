package com.qlyshopphone_backend.model;

import com.qlyshopphone_backend.model.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class CustomerInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String phone;
    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;
    @Enumerated(EnumType.STRING)
    private Status status;

    public CustomerInfo(String name, String phone, Address address, Users users, Status status) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.users = users;
        this.status = status;
    }
}
