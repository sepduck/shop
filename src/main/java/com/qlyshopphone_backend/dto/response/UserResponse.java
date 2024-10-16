package com.qlyshopphone_backend.dto.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserResponse {
    private Long userId;
    private String username;
    private String phoneNumber;
    private LocalDate startDay;
    private String idCard;
    private String gender;
    private String facebook;
    private String email;
    private String address;
    private String fullName;
    private String status;
    private LocalDate birthday;
    private String token;
}
