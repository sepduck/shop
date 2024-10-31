package com.qlyshopphone_backend.dto.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserResponse {
    private Long id;
    private String username;
    private String phoneNumber;
    private LocalDate startDay;
    private String idCard;
    private String gender;
    private String facebook;
    private String email;
    private String address;
    private String firstName;
    private String lastName;
    private String status;
    private LocalDate birthday;
}
