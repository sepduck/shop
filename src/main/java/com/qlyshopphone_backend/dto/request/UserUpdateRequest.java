package com.qlyshopphone_backend.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserUpdateRequest {
    private Long id;
    private String phoneNumber;
    private String gender;
    private String facebook;
    private String address;
    private String firstName;
    private String lastName;
    private LocalDate birthday;
}
