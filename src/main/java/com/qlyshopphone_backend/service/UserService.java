package com.qlyshopphone_backend.service;

import com.qlyshopphone_backend.dto.PasswordChangeRequestDTO;
import com.qlyshopphone_backend.dto.UsersDTO;
import com.qlyshopphone_backend.exceptions.DataNotFoundException;
import com.qlyshopphone_backend.model.Users;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;

public interface UserService {
    ResponseEntity<?> getAllUsers();

    void saveUser(Users user);

    ResponseEntity<?> updatePassword(String username, PasswordChangeRequestDTO passwordChangeRequestDTO);

    ResponseEntity<?> updateUser(int userId, UsersDTO usersDTO) throws Exception;

    ResponseEntity<?> updateUserInfo(String username, UsersDTO usersDTO) throws Exception;

    ResponseEntity<?> updateUserInfoFile(String username, UsersDTO usersDTO) throws Exception;

    ResponseEntity<?> findByUserId(int id);

    Users findByUsername(String username);

    ResponseEntity<?> deleteUser(int id);
}
