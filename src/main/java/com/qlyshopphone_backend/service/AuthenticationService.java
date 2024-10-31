package com.qlyshopphone_backend.service;

import com.qlyshopphone_backend.dto.request.AuthenticationRequest;
import com.qlyshopphone_backend.dto.request.UserDetailRequest;
import com.qlyshopphone_backend.dto.response.LoginResponse;
import com.qlyshopphone_backend.model.Users;
import jakarta.mail.MessagingException;

public interface AuthenticationService {
    LoginResponse loginUser(AuthenticationRequest request);

    boolean registerUser(UserDetailRequest request) throws MessagingException;

    String verifyUserAccount(String token);

    String resendVerificationTokenEmail(String email) throws MessagingException;

    String resetUserPassword(String email) throws MessagingException;

    Users getCurrentAuthenticatedUser();

    Users getUserByEmail(String email);
}
