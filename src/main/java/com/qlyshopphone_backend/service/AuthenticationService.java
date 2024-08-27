package com.qlyshopphone_backend.service;

import com.qlyshopphone_backend.dto.UsersDTO;
import com.qlyshopphone_backend.model.Users;

public interface AuthenticationService {
    String login(Users users);

    String register(UsersDTO usersDTO) throws Exception;

    void invalidateToken(String token);

    boolean isTokenInvalidated(String token);
}
