package com.qlyshopphone_backend.dto.request;

import lombok.*;

import java.time.LocalDate;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    private String username;
    private String password;
    private String phoneNumber;
    private String idCard;
    private String gender;
    private String facebook;
    private String email;
    private Long addressId;
    private String firstName;
    private String lastName;
    private LocalDate birthday;
}
