package com.qlyshopphone_backend.service.impl;

import com.qlyshopphone_backend.dto.PasswordChangeRequestDTO;
import com.qlyshopphone_backend.dto.UsersDTO;
import com.qlyshopphone_backend.model.BaseReponse;
import com.qlyshopphone_backend.model.Gender;
import com.qlyshopphone_backend.model.Users;
import com.qlyshopphone_backend.repository.GenderRepository;
import com.qlyshopphone_backend.repository.UserRepository;
import com.qlyshopphone_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends BaseReponse implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final GenderRepository genderRepository;

    @Override
    public ResponseEntity<?> getAllUsers() {
        return getResponseEntity(userRepository.getAllUsers());
    }

    @Override
    public void saveUser(Users user) {
        userRepository.save(user);
    }

    @Override
    public ResponseEntity<?> updatePassword(String username, PasswordChangeRequestDTO passwordChangeRequestDTO) {
        try {
            Users existingUser = userRepository.findByUsername(username);
            if (!checkPasswordPresent(existingUser, passwordChangeRequestDTO.getOldPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Mật khẩu cũ không khớp");
            }
            if (!passwordChangeRequestDTO.getNewPassword().equals(passwordChangeRequestDTO.getConfirmPassword())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Mật khẩu mới không khớp");
            }
            existingUser.setPassword(passwordEncoder.encode(passwordChangeRequestDTO.getNewPassword()));
            userRepository.save(existingUser);
            return getResponseEntity("Cập nhật mật khẩu thành công");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi khi cập nhật mật khẩu");
        }

    }

    Boolean checkPasswordPresent(Users users, String oldPassword) {
        return passwordEncoder.matches(oldPassword, users.getPassword());
    }

    @Transactional
    @Override
    public ResponseEntity<?> updateUser(int userId, UsersDTO usersDTO) throws Exception {
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
        return getResponseEntity(userRepository.save(existingUsers));
    }

    @Transactional
    @Override
    public ResponseEntity<?> updateUserInfo(String username, UsersDTO usersDTO) throws Exception {
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
        return getResponseEntity(userRepository.save(existingUsers));
    }

    @Override
    public ResponseEntity<?> updateUserInfoFile(String username, UsersDTO usersDTO) throws Exception {
        Users existing = userRepository.findByUsername(username);
        existing.setFileUser(usersDTO.getFileUser().getBytes());
        return getResponseEntity(userRepository.save(existing));
    }

    @Override
    public ResponseEntity<?> findByUserId(int id) {
        return getResponseEntity(userRepository.findById(id));
    }

    @Override
    public Users findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public ResponseEntity<?> deleteUser(int id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return getResponseEntity("User deleted successfully");
        } else {
            return getResponseEntity("User not found");
        }
    }
}
