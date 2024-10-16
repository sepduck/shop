package com.qlyshopphone_backend.service;

import com.qlyshopphone_backend.dto.request.AuthenticationRequest;
import com.qlyshopphone_backend.dto.request.UserRequest;
import com.qlyshopphone_backend.dto.response.LoginResponse;
import com.qlyshopphone_backend.dto.response.UserResponse;
import com.qlyshopphone_backend.model.Users;

public interface AuthenticationService {
    LoginResponse login(AuthenticationRequest request);

    UserResponse register(UserRequest userRequest) throws Exception;

    Users getAuthenticatedUser();
}
