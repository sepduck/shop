package com.qlyshopphone_backend.dto.response;

import com.qlyshopphone_backend.dto.request.UserRequest;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private UserRequest user;
    private String token;
}
