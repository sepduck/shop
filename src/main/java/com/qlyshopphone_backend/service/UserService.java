package com.qlyshopphone_backend.service;

import com.qlyshopphone_backend.dto.PasswordChangeRequestDTO;
import com.qlyshopphone_backend.dto.UsersDTO;
import com.qlyshopphone_backend.model.Gender;
import com.qlyshopphone_backend.model.Users;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface UserService {
    List<Map<String, Object>> getAllUsers();

    String updatePassword(PasswordChangeRequestDTO passwordChangeRequestDTO);

    String updateUser(Long userId, UsersDTO usersDTO);

    String updateUserInfo(UsersDTO usersDTO) throws Exception;

    String updateUserInfoFile(UsersDTO usersDTO) throws Exception;

    String deleteUser(Long id);

    Map<String, Object> getUserInfo();

    List<Map<String, Object>> getAllCustomers();

    String deleteCustomerById(Long userId);

    List<Map<String, Object>> searchCustomerByName(String fullName);

    List<Map<String, Object>> searchCustomerByEmail(String customerEmail);

    List<Map<String, Object>> searchCustomerByPhone(String customerPhone);

    List<Map<String, Object>> searchCustomerById(Long userId);

    List<Map<String, Object>> searchCustomerByAddress(String address);

    List<Map<String, Object>> searchCustomerByActive(byte active);

    List<Map<String, Object>> searchCustomerByGender(int number);

    List<Map<String, Object>> getAllEmployees();

    String saveEmployeeRoles(Long userId) throws SQLException;

    String deleteEmployeeById(Long id);

    List<Map<String, Object>> searchEmployeeById(Long id);

    List<Map<String, Object>> searchEmployeeByName(String employeeName);

    List<Map<String, Object>> searchEmployeeByPhoneNumber(String phoneNumber);

    List<Map<String, Object>> searchEmployeeByActive(int number);

    List<Gender> getAllGender();
}
