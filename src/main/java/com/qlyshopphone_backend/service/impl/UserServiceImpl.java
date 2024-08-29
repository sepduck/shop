package com.qlyshopphone_backend.service.impl;

import static com.qlyshopphone_backend.constant.ErrorMessage.*;

import com.qlyshopphone_backend.dto.PasswordChangeRequestDTO;
import com.qlyshopphone_backend.dto.UsersDTO;
import com.qlyshopphone_backend.model.Gender;
import com.qlyshopphone_backend.model.Users;
import com.qlyshopphone_backend.repository.GenderRepository;
import com.qlyshopphone_backend.repository.UserRepository;
import com.qlyshopphone_backend.service.AuthenticationService;
import com.qlyshopphone_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final GenderRepository genderRepository;
    private final AuthenticationService authenticationService;

    @Override
    public List<Map<String, Object>> getAllUsers() {
        return userRepository.getAllUsers();
    }

    @Override
    public String updatePassword(PasswordChangeRequestDTO passwordChangeRequestDTO) {
        Users users = authenticationService.getAuthenticatedUser();
        if (!checkPasswordPresent(users, passwordChangeRequestDTO.getOldPassword())) {
            throw new RuntimeException(OLD_PASSWORD_DOSE_NOT_MATCH);
        }
        if (!passwordChangeRequestDTO.getNewPassword().equals(passwordChangeRequestDTO.getConfirmPassword())) {
            throw new RuntimeException(NEW_PASSWORD_DOSE_NOT_MATCH);
        }
        users.setPassword(passwordEncoder.encode(passwordChangeRequestDTO.getNewPassword()));
        userRepository.save(users);
        return SUCCESSFULLY_UPDATED_PASSWORD;


    }

    Boolean checkPasswordPresent(Users users, String oldPassword) {
        return passwordEncoder.matches(oldPassword, users.getPassword());
    }

    @Transactional
    @Override
    public String updateUser(Long userId, UsersDTO usersDTO) {
        Users users = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException(USER_NOT_FOUND));
        updateUserProperties(users, usersDTO);
        userRepository.save(users);
        return USER_UPDATED_SUCCESSFULLY;
    }

    @Transactional
    @Override
    public String updateUserInfo(UsersDTO usersDTO) {
        Users users = authenticationService.getAuthenticatedUser();
        updateUserProperties(users, usersDTO);
        userRepository.save(users);
        return UNIT_UPDATED_SUCCESSFULLY;
    }

    private void updateUserProperties(Users users, UsersDTO usersDTO) {
        Gender gender = genderRepository.findById(usersDTO.getGenderId())
                .orElseThrow(() -> new RuntimeException(GENDER_NOT_FOUND));
        users.setFullName(usersDTO.getFullName());
        users.setPhoneNumber(usersDTO.getPhoneNumber());
        users.setEmail(usersDTO.getEmail());
        users.setGender(gender);
        users.setBirthday(usersDTO.getBirthday());
        users.setAddress(usersDTO.getAddress());
        users.setIdCard(usersDTO.getIdCard());
        users.setFacebook(usersDTO.getFacebook());
    }

    @Override
    public String updateUserInfoFile(UsersDTO usersDTO) throws IOException {
        Users users = authenticationService.getAuthenticatedUser();
        users.setFileUser(usersDTO.getFileUser().getBytes());
        userRepository.save(users);
        return USER_UPDATED_SUCCESSFULLY;
    }

    @Override
    public String deleteUser(Long userId) {
        Users users = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException(USER_NOT_FOUND));
        userRepository.deleteById(users.getUserId());
        return USER_DELETED_SUCCESSFULLY;
    }

    @Override
    public Map<String, Object> getUserInfo() {
        Users user = authenticationService.getAuthenticatedUser();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedStartDay = user.getStartDay().format(formatter);

        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("userId", user.getUserId());
        userInfo.put("username", user.getUsername());
        userInfo.put("password", user.getPassword());
        userInfo.put("phoneNumber", user.getPhoneNumber());
        userInfo.put("idCard", user.getIdCard());
        userInfo.put("genderName", user.getGender().getGenderName());
        userInfo.put("facebook", user.getFacebook());
        userInfo.put("email", user.getEmail());
        userInfo.put("address", user.getAddress());
        userInfo.put("fullName", user.getFullName());
        userInfo.put("birthday", user.getBirthday());
        userInfo.put("startDay", formattedStartDay);
        userInfo.put("fileUser", user.getFileUser());
        return userInfo;
    }

    @Override
    public List<Map<String, Object>> getAllCustomers() {
        return userRepository.getCustomer();
    }

    @Override
    public String deleteCustomerById(Long userId) {
        userRepository.deleteCustomerId(userId);
        return DELETE_CUSTOMER_SUCCESSFULLY;
    }

    @Override
    public List<Map<String, Object>> searchCustomerByName(String fullName) {
        return userRepository.searchCustomerName(fullName);
    }

    @Override
    public List<Map<String, Object>> searchCustomerByEmail(String customerEmail) {
        return userRepository.searchByEmail(customerEmail);
    }

    @Override
    public List<Map<String, Object>> searchCustomerByPhone(String customerPhone) {
        return userRepository.searchByPhoneNumber(customerPhone);
    }

    @Override
    public List<Map<String, Object>> searchCustomerById(Long userId) {
        return userRepository.searchCustomerId(userId);
    }

    @Override
    public List<Map<String, Object>> searchCustomerByAddress(String address) {
        return userRepository.searchByAddress(address);
    }

    @Override
    public List<Map<String, Object>> searchCustomerByActive(byte active) {
        return userRepository.searchByActive(active);
    }

    @Override
    public List<Map<String, Object>> searchCustomerByGender(int number) {
        return switch (number) {
            case 1 -> userRepository.getCustomer();
            case 2 -> userRepository.searchByGenderId1();
            case 3 -> userRepository.searchByGenderId2();
            case 4 -> userRepository.searchByGenderId3();
            default -> new ArrayList<>();
        };
    }

    @Override
    public List<Map<String, Object>> getAllEmployees() {
        return userRepository.getEmployeeList();
    }

    @Override
    public String saveEmployeeRoles(Long userId) {
//        userDAO.updateEmployeeStatusAndRole(userId);
        return EMPLOYEE_ROLES_SUCCESSFULLY_SAVED;
    }

    @Override
    public String deleteEmployeeById(Long userId) {
        Users users = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException(USER_NOT_FOUND));
        userRepository.deleteEmployeeById(users.getUserId());
        return EMPLOYEE_DELETED_SUCCESSFULLY;
    }

    @Override
    public List<Map<String, Object>> searchEmployeeById(Long id) {
        return userRepository.searchEmployeeId(id);
    }

    @Override
    public List<Map<String, Object>> searchEmployeeByName(String employeeName) {
        return userRepository.searchEmployeeName(employeeName);
    }

    @Override
    public List<Map<String, Object>> searchEmployeeByPhoneNumber(String phoneNumber) {
        return userRepository.searchPhoneNumber(phoneNumber);
    }

    @Override
    public List<Map<String, Object>> searchEmployeeByActive(int number) {
        return switch (number) {
            case 1 -> userRepository.getEmployeeList();
            case 2 -> userRepository.searchByAllActive();
            case 3 -> userRepository.searchByNoActive();
            default -> new ArrayList<>();
        };
    }

    @Override
    public List<Gender> getAllGender() {
        return genderRepository.findAll();
    }


}
