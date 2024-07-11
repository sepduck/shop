package com.qlyshopphone_backend.dto;

import jakarta.persistence.Column;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsersDTO {
    private Integer userId;
    @Column(name = "username", unique = true)
    private String username;
    private String password;
    private String phoneNumber;
    private String idCard;
    private Integer genderId;
    private String genderName;
    private String facebook;
    private String email;
    private String address;
    private String fullName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    private boolean deleteUser;
    private boolean employee;
    private Integer roleId;
    private String thumbnail;
    private MultipartFile fileUser;
}
