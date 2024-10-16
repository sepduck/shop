package com.qlyshopphone_backend.dto.response;

import com.qlyshopphone_backend.dto.request.UserRequest;
import com.qlyshopphone_backend.model.Users;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private UserRequest user;
    private String token;
}
