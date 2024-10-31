package com.qlyshopphone_backend.model;

import com.qlyshopphone_backend.model.enums.Gender;
import com.qlyshopphone_backend.model.enums.Role;
import com.qlyshopphone_backend.model.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Entity
public class Users implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String username;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String phoneNumber;
    private String password;
    private String idCard;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String facebook;
    private String firstName;
    private String lastName;
    @Enumerated(EnumType.STRING)
    private Status status;
    private LocalDate birthday;
    @Enumerated(EnumType.STRING)
    private Role role;
    private boolean verify;
    private String avatar;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Carts> carts = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CustomerInfo> customerInfo = new ArrayList<>();
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    private LocalDateTime operatingTime;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }
}
