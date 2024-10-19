package com.qlyshopphone_backend.service;

import com.qlyshopphone_backend.dto.request.AuthenticationRequest;
import com.qlyshopphone_backend.dto.request.UserRequest;
import com.qlyshopphone_backend.dto.response.LoginResponse;
import com.qlyshopphone_backend.model.Users;
import jakarta.mail.MessagingException;

public interface AuthenticationService {
    LoginResponse login(AuthenticationRequest request);

    boolean registerUser(UserRequest request) throws MessagingException;

    String verifyAccount(String token);

    String resendVerificationToken(String email) throws MessagingException;

    String forgotPassword(String email) throws MessagingException;

    Users getAuthenticatedUser();

    Users getUserByEmail(String email);
}
