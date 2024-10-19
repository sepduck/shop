package com.qlyshopphone_backend.service.impl;

import static com.qlyshopphone_backend.constant.ErrorMessage.*;

import com.qlyshopphone_backend.dto.request.AuthenticationRequest;
import com.qlyshopphone_backend.dto.request.UserRequest;
import com.qlyshopphone_backend.dto.response.LoginResponse;
import com.qlyshopphone_backend.exceptions.ApiRequestException;
import com.qlyshopphone_backend.mapper.BasicMapper;
import com.qlyshopphone_backend.model.*;
import com.qlyshopphone_backend.model.enums.Role;
import com.qlyshopphone_backend.model.enums.Status;
import com.qlyshopphone_backend.repository.*;
import com.qlyshopphone_backend.service.AuthenticationService;
import com.qlyshopphone_backend.service.jwt.JwtProvider;
import com.qlyshopphone_backend.service.util.VerificationCodeGenerator;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final JwtProvider provider;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final BasicMapper basicMapper;
    private final VerificationTokenRepository verificationTokenRepository;
    private final EmailService emailService;
    private final AddressCountryRepository countryRepository;
    private final AddressCityRepository cityRepository;
    private final AddressWardRepository wardRepository;

    @Override
    public LoginResponse login(AuthenticationRequest request) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken
                        (request.getUsername(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Users users = userRepository.getUserByUsername(request.getUsername());
        String token = provider.generateToken(authentication);
        UserRequest userRequest = basicMapper.convertToRequest(users, UserRequest.class);
        return new LoginResponse(userRequest, token);
    }

    @Transactional
    @Override
    public boolean registerUser(UserRequest request) throws MessagingException {
        Users newUsers = basicMapper.convertToRequest(request, Users.class);
        newUsers.setPassword(passwordEncoder.encode(request.getPassword()));
        newUsers.setStatus(Status.ACTIVE);
        newUsers.setRole(Role.CUSTOMER);

        AddressCities addressCities = cityRepository.findById(request.getCityId())
                .orElseThrow(() -> new ApiRequestException("City not found", HttpStatus.BAD_REQUEST));
        AddressCountries addressCountries = countryRepository.findById(request.getCountryId())
                .orElseThrow(() -> new ApiRequestException("Country not found", HttpStatus.BAD_REQUEST));
        AddressWards addressWards = wardRepository.findById(request.getWardId())
                .orElseThrow(() -> new ApiRequestException("Ward not found", HttpStatus.BAD_REQUEST));

        UserAddress userAddress = new UserAddress(request.getAddress(), addressWards, addressCities, addressCountries);
        newUsers.setAddress(userAddress);
        userRepository.save(newUsers);

        String verificationCode = VerificationCodeGenerator.generateCode();
        LocalDateTime tokenExpiration = LocalDateTime.now(ZoneId.systemDefault()).plusHours(2);
        VerificationTokens verificationTokens = new VerificationTokens(verificationCode, newUsers, tokenExpiration);
        verificationTokenRepository.save(verificationTokens);

        emailService.sendVerificationMail(newUsers.getEmail(), verificationCode);
        return true;
    }


    @Transactional
    @Override
    public String verifyAccount(String token) {
        VerificationTokens verificationTokens = verificationTokenRepository.findByToken(token)
                .orElseThrow(() -> new ApiRequestException("Invalid verification code!", HttpStatus.BAD_REQUEST));

        if (verificationTokens.getExpiryDate().isBefore(LocalDateTime.now(ZoneId.systemDefault()))) {
            throw new ApiRequestException("Verification code has expired!", HttpStatus.BAD_REQUEST);
        }
        Users users = verificationTokens.getUsers();
        users.setVerify(true);
        userRepository.save(users);

        verificationTokenRepository.delete(verificationTokens);

        return "Account has been successfully activated!";
    }

    @Transactional
    @Override
    public String resendVerificationToken(String email) throws MessagingException {
        Users users = getUserByEmail(email);
        VerificationTokens verificationTokens = verificationTokenRepository.findByUsers(users)
                .orElseThrow(() -> new RuntimeException("Verification token not found!"));
        if (verificationTokens.getExpiryDate().isBefore(LocalDateTime.now(ZoneId.systemDefault()))) {
            verificationTokenRepository.delete(verificationTokens);
            String newVerifyCode = VerificationCodeGenerator.generateCode();
            LocalDateTime newExpiryDate = LocalDateTime.now(ZoneId.systemDefault()).plusHours(2);

            VerificationTokens newToken = new VerificationTokens(newVerifyCode, users, newExpiryDate);
            verificationTokenRepository.save(newToken);

            emailService.sendVerificationMail(users.getEmail(), newVerifyCode);
            return "A new verification code has been sent to your email.";
        } else {
            emailService.sendVerificationMail(users.getEmail(), verificationTokens.getToken());
            return "Verification code is still valid, resent to your email.";
        }
    }

    @Override
    @Transactional
    public String forgotPassword(String email) throws MessagingException {
        Users users = getUserByEmail(email);

        String newPassword = UUID.randomUUID().toString().substring(0, 8);
        String encodedPassword = passwordEncoder.encode(newPassword);
        users.setPassword(encodedPassword);
        userRepository.save(users);

        emailService.sendNewPasswordMail(users.getEmail(), newPassword);
        return "A new password has been sent to your email.";
    }

    @Override
    public Users getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.getUserByUsername(authentication.getName());
    }

    @Override
    public Users getUserByEmail(String email) {
        return userRepository.getUserByEmail(email)
                .orElseThrow(() -> new ApiRequestException(USER_NOT_FOUND, HttpStatus.BAD_REQUEST));
    }
}
