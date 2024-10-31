package com.qlyshopphone_backend.service;

import com.qlyshopphone_backend.dto.request.ChangePasswordRequest;
import com.qlyshopphone_backend.dto.request.UserDetailRequest;
import com.qlyshopphone_backend.dto.request.UserUpdateRequest;
import com.qlyshopphone_backend.dto.response.UserProjectResponse;
import com.qlyshopphone_backend.dto.response.UserRolesResponse;
import com.qlyshopphone_backend.model.Users;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {
    boolean updateAvatar(MultipartFile file) throws IOException;

    boolean updateGender(UserUpdateRequest request);

    boolean updateFacebook(UserUpdateRequest request);

    boolean updateAddress(UserDetailRequest request);

    boolean updateFirstName(UserUpdateRequest request);

    boolean updateLastName(UserUpdateRequest request);

    boolean updateBirthday(UserUpdateRequest request);

    String changePassword(ChangePasswordRequest request);

    boolean updatePhoneNumber(UserUpdateRequest request);

    UserProjectResponse getUserInfo();

    List<UserRolesResponse> getAllCustomers();

    List<UserRolesResponse> searchCustomerByName(String fullName);

    List<UserRolesResponse> searchCustomerByEmail(String email);

    List<UserRolesResponse> searchCustomerByPhone(String phone);

    List<UserRolesResponse> searchCustomerById(Long userId);

    List<UserRolesResponse> searchCustomerByStatus(String status);

    List<UserRolesResponse> searchCustomerByGender(String gender);

    List<UserRolesResponse> findAllEmployees();

    boolean assignEmployeeRole(Long userId);

    List<UserRolesResponse> searchEmployeeById(Long id);

    List<UserRolesResponse> searchEmployeeByName(String employeeName);

    List<UserRolesResponse> searchEmployeeByPhoneNumber(String phoneNumber);

    List<UserRolesResponse> searchEmployeeByStatus(String status);
}
