package com.qlyshopphone_backend.dto.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserRolesResponse {
    private Long id;
    private String username;
    private String phoneNumber;
    private LocalDate startDay;
    private String gender;
    private String firstName;
    private String lastName;
    private String status;
    private LocalDate birthday;
}
