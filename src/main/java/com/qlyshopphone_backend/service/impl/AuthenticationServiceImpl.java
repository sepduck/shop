package com.qlyshopphone_backend.service.impl;

import com.qlyshopphone_backend.dto.request.AuthenticationRequest;
import com.qlyshopphone_backend.dto.request.UserRequest;
import com.qlyshopphone_backend.dto.response.LoginResponse;
import com.qlyshopphone_backend.dto.response.UserResponse;
import com.qlyshopphone_backend.mapper.BasicMapper;
import com.qlyshopphone_backend.model.Users;
import com.qlyshopphone_backend.repository.UserRepository;
import com.qlyshopphone_backend.service.AuthenticationService;
import com.qlyshopphone_backend.service.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final JwtProvider provider;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final BasicMapper basicMapper;

    @Override
    public LoginResponse login(AuthenticationRequest request) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken
                        (request.getUsername(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Users user = userRepository.getUserByUsername(request.getUsername());
        String token = provider.generateToken(authentication);
        UserRequest userRequest = basicMapper.convertToRequest(user, UserRequest.class);
        return new LoginResponse(userRequest, token);
    }

    @Override
    public UserResponse register(UserRequest request) {
        Users users = basicMapper.convertToRequest(request, Users.class);
        users.setPassword(passwordEncoder.encode(request.getPassword()));
        users.setStatus(Users.Status.ACTIVE);
        users.setRole(Users.Role.CUSTOMER);
        userRepository.save(users);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        // Thiết lập xác thực trong SecurityContext
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Tạo token
        String token = provider.generateToken(authentication);

        // Chuyển đổi từ Users sang UserResponse và thêm token
        UserResponse response = basicMapper.convertToResponse(users, UserResponse.class);
        response.setToken(token);  // Set token vào response

        return response;    }

    @Override
    public Users getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.getUserByUsername(authentication.getName());
    }
}
