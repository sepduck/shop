package com.qlyshopphone_backend.service.impl;

import com.qlyshopphone_backend.dto.PasswordChangeRequestDTO;
import com.qlyshopphone_backend.dto.UsersDTO;
import com.qlyshopphone_backend.model.Gender;
import com.qlyshopphone_backend.model.Users;
import com.qlyshopphone_backend.repository.GenderRepository;
import com.qlyshopphone_backend.repository.UserRepository;
import com.qlyshopphone_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final GenderRepository genderRepository;

    @Override
    public List<Map<String, Object>> getAllUsers() {
        return userRepository.getAllUsers();
    }

    @Override
    public void saveUser(Users user) {
        userRepository.save(user);
    }

    @Override
    public String updatePassword(String username, PasswordChangeRequestDTO passwordChangeRequestDTO) {
            Users existingUser = userRepository.findByUsername(username);
            if (!checkPasswordPresent(existingUser, passwordChangeRequestDTO.getOldPassword())) {
                throw new RuntimeException("Old passwords do not match");
            }
            if (!passwordChangeRequestDTO.getNewPassword().equals(passwordChangeRequestDTO.getConfirmPassword())) {
                throw new RuntimeException("New password does not match");
            }
            existingUser.setPassword(passwordEncoder.encode(passwordChangeRequestDTO.getNewPassword()));
            userRepository.save(existingUser);
            return "Successfully updated password";


    }

    Boolean checkPasswordPresent(Users users, String oldPassword) {
        return passwordEncoder.matches(oldPassword, users.getPassword());
    }

    @Transactional
    @Override
    public String updateUser(Long userId, UsersDTO usersDTO) throws Exception {
        Users existingUsers = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Gender existingGender = genderRepository.findById(usersDTO.getGenderId())
                .orElseThrow(() -> new RuntimeException("Gender not found"));
        existingUsers.setFullName(usersDTO.getFullName());
        existingUsers.setPhoneNumber(usersDTO.getPhoneNumber());
        existingUsers.setEmail(usersDTO.getEmail());
        existingUsers.setGender(existingGender);
        existingUsers.setBirthday(usersDTO.getBirthday());
        existingUsers.setAddress(usersDTO.getAddress());
        existingUsers.setIdCard(usersDTO.getIdCard());
        existingUsers.setFacebook(usersDTO.getFacebook());
        existingUsers.setFileUser(usersDTO.getFileUser().getBytes());
        userRepository.save(existingUsers);
        return "User successfully updated";
    }

    @Transactional
    @Override
    public String updateUserInfo(String username, UsersDTO usersDTO) {
        Users existingUsers = userRepository.findByUsername(username);
        Gender existingGender = genderRepository.findById(usersDTO.getGenderId())
                .orElseThrow(() -> new RuntimeException("Gender not found"));
        existingUsers.setFullName(usersDTO.getFullName());
        existingUsers.setPhoneNumber(usersDTO.getPhoneNumber());
        existingUsers.setEmail(usersDTO.getEmail());
        existingUsers.setGender(existingGender);
        existingUsers.setBirthday(usersDTO.getBirthday());
        existingUsers.setAddress(usersDTO.getAddress());
        existingUsers.setIdCard(usersDTO.getIdCard());
        existingUsers.setFacebook(usersDTO.getFacebook());
        userRepository.save(existingUsers);
        return "User successfully updated";
    }

    @Override
    public String updateUserInfoFile(String username, UsersDTO usersDTO) throws IOException {
        Users existing = userRepository.findByUsername(username);
        existing.setFileUser(usersDTO.getFileUser().getBytes());
        userRepository.save(existing);
        return "User successfully updated";
    }

    @Override
    public Users findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public String deleteUser(Long userId) {
        Users users = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
            userRepository.deleteById(users.getUserId());
            return "User deleted successfully";
    }

    @Override
    public List<Map<String, Object>> getAllCustomers() {
        return userRepository.getCustomer();
    }

    @Override
    public String deleteCustomerById(Long userId) {
        userRepository.deleteCustomerId(userId);
        return "Delete customer successfully";
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
    public String saveEmployeeRoles(Long userId){
//        userDAO.updateEmployeeStatusAndRole(userId);
        return "Employee Roles successfully saved";
    }

    @Override
    public String deleteEmployeeById(Long userId) {
        Users users = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.deleteEmployeeById(users.getUserId());
        return "Employee deleted successfully";
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
