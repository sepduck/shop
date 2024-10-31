package com.qlyshopphone_backend.service.impl;

import static com.qlyshopphone_backend.constant.ErrorMessage.*;

import com.qlyshopphone_backend.dto.request.ChangePasswordRequest;
import com.qlyshopphone_backend.dto.request.UserDetailRequest;
import com.qlyshopphone_backend.dto.request.UserUpdateRequest;
import com.qlyshopphone_backend.dto.response.UserProjectResponse;
import com.qlyshopphone_backend.dto.response.UserRolesResponse;
import com.qlyshopphone_backend.exceptions.ApiRequestException;
import com.qlyshopphone_backend.mapper.BasicMapper;
import com.qlyshopphone_backend.model.*;
import com.qlyshopphone_backend.model.enums.Gender;
import com.qlyshopphone_backend.model.enums.Role;
import com.qlyshopphone_backend.model.enums.Status;
import com.qlyshopphone_backend.repository.*;
import com.qlyshopphone_backend.service.AuthenticationService;
import com.qlyshopphone_backend.service.UserService;
import com.qlyshopphone_backend.service.util.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationService authenticationService;
    private final FirebaseStorageService firebaseStorageService;
    private final BasicMapper basicMapper;
    private final AddressRepository addressRepository;
    private final AddressService addressService;

    @Transactional
    @Override
    public boolean updateAvatar(MultipartFile file) throws IOException {
        Users authUser = authenticationService.getCurrentAuthenticatedUser();
        String avatarUrl = firebaseStorageService.uploadFile(file);
        authUser.setAvatar(avatarUrl);
        userRepository.save(authUser);
        return true;
    }

    @Transactional
    @Override
    public boolean updatePhoneNumber(UserUpdateRequest request) {
        Users authUser = authenticationService.getCurrentAuthenticatedUser();
        authUser.setPhoneNumber(request.getPhoneNumber());
        userRepository.save(authUser);
        return true;
    }

    @Transactional
    @Override
    public boolean updateGender(UserUpdateRequest request) {
        Users authUser = authenticationService.getCurrentAuthenticatedUser();
        authUser.setGender(Gender.valueOf(request.getGender()));
        userRepository.save(authUser);
        return true;
    }

    @Transactional
    @Override
    public boolean updateFacebook(UserUpdateRequest request) {
        Users authUser = authenticationService.getCurrentAuthenticatedUser();
        authUser.setFacebook(request.getFacebook());
        userRepository.save(authUser);
        return true;
    }

    @Transactional
    @Override
    public boolean updateAddress(UserDetailRequest request) {
        Users authUser = authenticationService.getCurrentAuthenticatedUser();
        Address address = addressRepository.findById(authUser.getAddress().getId())
                        .orElseThrow(() -> new ApiRequestException(ADDRESS_NOT_FOUND, HttpStatus.BAD_REQUEST));
        addressService.setAddressDetails(
                address,
                request.getWardId(),
                request.getCityId(),
                request.getCountryId(),
                request.getStreet()
        );
        addressRepository.save(address);
        return true;
    }

    @Transactional
    @Override
    public boolean updateFirstName(UserUpdateRequest request) {
        Users authUser = authenticationService.getCurrentAuthenticatedUser();
        authUser.setFirstName(request.getFirstName());
        userRepository.save(authUser);
        return true;
    }

    @Transactional
    @Override
    public boolean updateLastName(UserUpdateRequest request) {
        Users authUser = authenticationService.getCurrentAuthenticatedUser();
        authUser.setLastName(request.getLastName());
        userRepository.save(authUser);
        return true;
    }

    @Transactional
    @Override
    public boolean updateBirthday(UserUpdateRequest request) {
        Users authUser = authenticationService.getCurrentAuthenticatedUser();
        authUser.setBirthday(request.getBirthday());
        userRepository.save(authUser);
        return true;
    }

    @Transactional
    @Override
    public String changePassword(ChangePasswordRequest request) {
        Users authUser = authenticationService.getCurrentAuthenticatedUser();
        if (!passwordEncoder.matches(request.getOldPassword(), authUser.getPassword())) {
            throw new ApiRequestException(OLD_PASSWORD_DOSE_NOT_MATCH, HttpStatus.BAD_REQUEST);
        }
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new ApiRequestException(NEW_PASSWORD_DOSE_NOT_MATCH, HttpStatus.BAD_REQUEST);
        }
        String encodedPassword = passwordEncoder.encode(request.getNewPassword());
        authUser.setPassword(encodedPassword);
        userRepository.save(authUser);
        return PASSWORD_CHANGED_SUCCESSFULLY;
    }

    @Override
    public UserProjectResponse getUserInfo() {
        Users authUser = authenticationService.getCurrentAuthenticatedUser();
        return basicMapper.convertToResponse(authUser, UserProjectResponse.class);
    }

    @Override
    public List<UserRolesResponse> getAllCustomers() {
        return userRepository.findUsersByRole(Role.CUSTOMER);
    }

    @Override
    public List<UserRolesResponse> searchCustomerByName(String fullName) {
        return userRepository.searchEmployeeName(Role.CUSTOMER, fullName);
    }

    @Override
    public List<UserRolesResponse> searchCustomerByEmail(String email) {
        return userRepository.searchUserByEmail(Role.CUSTOMER, email);
    }

    @Override
    public List<UserRolesResponse> searchCustomerByPhone(String phone) {
        return userRepository.searchPhoneNumber(Role.CUSTOMER, phone);
    }

    @Override
    public List<UserRolesResponse> searchCustomerById(Long userId) {
        return userRepository.searchEmployeeId(Role.CUSTOMER, userId);
    }

    @Override
    public List<UserRolesResponse> searchCustomerByStatus(String status) {
        return userRepository.searchUserByStatus(Role.CUSTOMER, Status.valueOf(status));
    }

    @Override
    public List<UserRolesResponse> searchCustomerByGender(String gender) {
        return userRepository.searchUserByGender(Role.CUSTOMER, Gender.valueOf(gender));
    }

    @Override
    public List<UserRolesResponse> findAllEmployees() {
        return userRepository.findUsersByRole(Role.EMPLOYEE);
    }

    @Override
    public boolean assignEmployeeRole(Long userId) {
        Users authUser = authenticationService.getCurrentAuthenticatedUser();
        if (authUser.getRole() != Role.ADMIN) {
            throw new ApiRequestException("Ban khong co quyen", HttpStatus.BAD_REQUEST);
        }
        Users userToAsign = userRepository.findById(userId)
                .orElseThrow(() -> new ApiRequestException(USER_NOT_FOUND, HttpStatus.NOT_FOUND));
        userToAsign.setRole(Role.EMPLOYEE);
        userRepository.save(userToAsign);
        return true;
    }

    @Override
    public List<UserRolesResponse> searchEmployeeById(Long id) {
        return userRepository.searchEmployeeId(Role.EMPLOYEE, id);
    }

    @Override
    public List<UserRolesResponse> searchEmployeeByName(String employeeName) {
        return userRepository.searchEmployeeName(Role.EMPLOYEE, employeeName);
    }

    @Override
    public List<UserRolesResponse> searchEmployeeByPhoneNumber(String phoneNumber) {
        return userRepository.searchPhoneNumber(Role.EMPLOYEE, phoneNumber);
    }

    @Override
    public List<UserRolesResponse> searchEmployeeByStatus(String status) {
        return userRepository.searchUserByStatus(Role.EMPLOYEE, Status.valueOf(status));
    }

}
