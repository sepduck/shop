package com.qlyshopphone_backend.controller.rest;

import com.qlyshopphone_backend.dto.PasswordChangeRequestDTO;
import com.qlyshopphone_backend.dto.UsersDTO;
import com.qlyshopphone_backend.model.Roles;
import com.qlyshopphone_backend.model.Users;
import com.qlyshopphone_backend.service.AuthenticationService;
import com.qlyshopphone_backend.service.GenderService;
import com.qlyshopphone_backend.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping()
public class RestUserController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private GenderService genderService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ACCOUNTANT')")
    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        return userService.getAllUsers();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ACCOUNTANT', 'ROLE_USER')")
    @GetMapping("/gender")
    public ResponseEntity<?> getAllGender() {
        return genderService.getAllGender();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ACCOUNTANT', 'ROLE_USER')")
    @PutMapping("/password")
    public ResponseEntity<?> updatePassword(@RequestBody PasswordChangeRequestDTO passwordChangeRequestDTO,
                                            Principal principal) {
        String username = principal.getName();
        return userService.updatePassword(username, passwordChangeRequestDTO);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ACCOUNTANT', 'ROLE_USER')")
    @PutMapping("/users-info")
    public ResponseEntity<?> updateUserInfo(@RequestBody UsersDTO usersDTO,
                                            Principal principal) throws Exception {
        String username = principal.getName();
        return userService.updateUserInfo(username, usersDTO);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ACCOUNTANT', 'ROLE_USER')")
    @PutMapping("/users-info-file")
    public ResponseEntity<?> updateInfoFile(@RequestBody UsersDTO usersDTO,
                                            Principal principal) throws Exception {
        String username = principal.getName();
        return userService.updateUserInfoFile(username, usersDTO);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ACCOUNTANT')")
    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id) {
        return userService.deleteUser(id);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ACCOUNTANT', 'ROLE_USER')")
    @GetMapping("/info")
    public ResponseEntity<?> getAccountInfo(HttpServletRequest request) {
        String username = request.getUserPrincipal().getName();
        Users user = userService.findByUsername(username);

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
        return ResponseEntity.ok(userInfo);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ACCOUNTANT', 'ROLE_USER')")
    @PostMapping("/log-out")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        try {
            String authorizationHeader = request.getHeader("Authorization");
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Thiếu hoặc sai định dạng Authorization header");
            }

            String token = authorizationHeader.replace("Bearer ", "");
            authenticationService.invalidateToken(token);
            return ResponseEntity.ok("Đăng xuất thành công");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Có lỗi xảy ra trong quá trình đăng xuất");
        }
    }

}
