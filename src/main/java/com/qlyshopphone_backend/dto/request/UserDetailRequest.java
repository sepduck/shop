package com.qlyshopphone_backend.dto.request;

import lombok.*;

import java.time.LocalDate;
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDetailRequest {
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

    private String street;
    private Long cityId;
    private Long countryId;
    private Long wardId;
}
