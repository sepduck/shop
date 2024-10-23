package com.qlyshopphone_backend.service.impl;

import static com.qlyshopphone_backend.constant.ErrorMessage.*;

import com.qlyshopphone_backend.dto.request.AuthenticationRequest;
import com.qlyshopphone_backend.dto.request.UserDetailRequest;
import com.qlyshopphone_backend.dto.request.UserRequest;
import com.qlyshopphone_backend.dto.response.LoginResponse;
import com.qlyshopphone_backend.exceptions.ApiRequestException;
import com.qlyshopphone_backend.mapper.BasicMapper;
import com.qlyshopphone_backend.model.*;
import com.qlyshopphone_backend.model.enums.Role;
import com.qlyshopphone_backend.model.enums.Status;
import com.qlyshopphone_backend.repository.*;
import com.qlyshopphone_backend.service.AuthenticationService;
import com.qlyshopphone_backend.config.jwt.JwtProvider;
import com.qlyshopphone_backend.service.util.AddressService;
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
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final BasicMapper basicMapper;
    private final VerificationTokenRepository verificationTokenRepo;
    private final EmailService emailService;
    private final AddressService addressService;

    @Override
    public LoginResponse loginUser(AuthenticationRequest request) {
        Authentication authentication = authManager
                .authenticate(new UsernamePasswordAuthenticationToken
                        (request.getUsername(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Users user = userRepository.getUserByUsername(request.getUsername());
        String jwtToken = jwtProvider.generateToken(authentication);
        UserRequest userRequest = basicMapper.convertToRequest(user, UserRequest.class);
        return new LoginResponse(userRequest, jwtToken);
    }

    @Transactional
    @Override
    public boolean registerUser(UserDetailRequest request) throws MessagingException {
        Users newUser = basicMapper.convertToRequest(request, Users.class);
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setStatus(Status.ACTIVE);
        newUser.setRole(Role.CUSTOMER);
        newUser.setOperatingTime(LocalDateTime.now());

        Address address = new Address();
        addressService.setAddressDetails(
                address,
                request.getWardId(),
                request.getCityId(),
                request.getCountryId(),
                request.getStreet()
        );

        newUser.setAddress(address);
        userRepository.save(newUser);

        String verificationCode = VerificationCodeGenerator.generateCode();
        LocalDateTime tokenExpiry = LocalDateTime.now(ZoneId.systemDefault()).plusHours(2);
        VerificationTokens verificationToken = new VerificationTokens(verificationCode, newUser, tokenExpiry);
        verificationTokenRepo.save(verificationToken);

        emailService.sendVerificationMail(newUser.getEmail(), verificationCode);
        return true;
    }


    @Transactional
    @Override
    public String verifyUserAccount(String token) {
        VerificationTokens verificationToken = verificationTokenRepo.findByToken(token)
                .orElseThrow(() -> new ApiRequestException("Invalid verification code!", HttpStatus.BAD_REQUEST));

        if (verificationToken.getExpiresAt().isBefore(LocalDateTime.now(ZoneId.systemDefault()))) {
            throw new ApiRequestException("Verification code has expired!", HttpStatus.BAD_REQUEST);
        }
        Users user = verificationToken.getUser();
        user.setVerify(true);
        userRepository.save(user);
        verificationTokenRepo.delete(verificationToken);

        return "Account has been successfully activated!";
    }

    @Transactional
    @Override
    public String resendVerificationTokenEmail(String email) throws MessagingException {
        Users user = getUserByEmail(email);
        VerificationTokens verificationToken = verificationTokenRepo.findByUser(user)
                .orElseThrow(() -> new ApiRequestException("Verification token not found!", HttpStatus.BAD_REQUEST));
        if (verificationToken.getExpiresAt().isBefore(LocalDateTime.now(ZoneId.systemDefault()))) {
            verificationTokenRepo.delete(verificationToken);
            String newVerificationCode = VerificationCodeGenerator.generateCode();
            LocalDateTime newExpiryDate = LocalDateTime.now(ZoneId.systemDefault()).plusHours(2);

            VerificationTokens newToken = new VerificationTokens(newVerificationCode, user, newExpiryDate);
            verificationTokenRepo.save(newToken);

            emailService.sendVerificationMail(user.getEmail(), newVerificationCode);
            return "A new verification code has been sent to your email.";
        } else {
            emailService.sendVerificationMail(user.getEmail(), verificationToken.getToken());
            return "Verification code is still valid, resent to your email.";
        }
    }

    @Override
    @Transactional
    public String resetUserPassword(String email) throws MessagingException {
        Users users = getUserByEmail(email);

        String newPassword = UUID.randomUUID().toString().substring(0, 8);
        String encodedPassword = passwordEncoder.encode(newPassword);
        users.setPassword(encodedPassword);
        userRepository.save(users);

        emailService.sendNewPasswordMail(users.getEmail(), newPassword);
        return "A new password has been sent to your email.";
    }

    @Override
    public Users getCurrentAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.getUserByUsername(authentication.getName());
    }

    @Override
    public Users getUserByEmail(String email) {
        return userRepository.getUserByEmail(email)
                .orElseThrow(() -> new ApiRequestException(USER_NOT_FOUND, HttpStatus.BAD_REQUEST));
    }
}
