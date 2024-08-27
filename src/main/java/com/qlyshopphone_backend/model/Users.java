package com.qlyshopphone_backend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "username", unique = true, length = 50, nullable = false)
    private String username;

    @Column(name = "password", length = 200, nullable = false)
    private String password;

    @Column(name = "phone_number", length = 11, nullable = false)
    private String phoneNumber;

    @Column(name = "start_day", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate startDay;

    @Column(name = "id_card", length = 12, nullable = false)
    private String idCard;

    @ManyToOne
    @JoinColumn(name = "gender_id", nullable = false)
    private Gender gender;

    @Column(name = "facebook", length = 250)
    private String facebook;

    @Column(name = "email", length = 100, nullable = false)
    private String email;

    @Column(name = "address", length = 250, nullable = false)
    private String address;

    @Column(name = "full_name", length = 50, nullable = false)
    private String fullName;

    @Column(name = "delete_user", columnDefinition = "boolean default false")
    private boolean delete_user = false;

    @Column(name = "employee", columnDefinition = "boolean default false")
    private boolean employee = false;

    @Column(name = "birthday", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "dd-MM-yyyy")
    @Temporal(TemporalType.DATE)
    private Date birthday;

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnoreProperties("users")
    @JoinTable(name = "users_roles", joinColumns = {@JoinColumn(name = "user_id", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "role_id", nullable = false)})
    private List<Roles> roles;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cart> cart = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CustomerInfo> customerInfo = new ArrayList<>();

    @Lob
    @Column(name = "file_user", columnDefinition = "MEDIUMBLOB")
    private byte[] fileUser;
}
