package com.qlyshopphone_backend.controller.rest;

import static com.qlyshopphone_backend.constant.PathConstant.*;

import com.qlyshopphone_backend.dto.request.ChangePasswordRequest;
import com.qlyshopphone_backend.dto.request.UserUpdateRequest;
import com.qlyshopphone_backend.dto.response.UserProjectResponse;
import com.qlyshopphone_backend.dto.response.UserRolesResponse;
import com.qlyshopphone_backend.service.UserService;
import com.qlyshopphone_backend.service.impl.FirebaseStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(API_V1)
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final FirebaseStorageService firebaseStorageService;

    @PutMapping(UPDATE_AVATAR)
    public ResponseEntity<Boolean> updateAvatar(@RequestParam("file") MultipartFile file) throws IOException {
        return ResponseEntity.ok(userService.updateAvatar(file));
    }

    @PutMapping(UPDATE_FIRSTNAME)
    public ResponseEntity<Boolean> updateFirstName(@RequestBody UserUpdateRequest request) {
        return ResponseEntity.ok(userService.updateFirstName(request));
    }

    @PutMapping(UPDATE_LASTNAME)
    public ResponseEntity<Boolean> updateLastName(@RequestBody UserUpdateRequest request)   {
        return ResponseEntity.ok(userService.updateLastName(request));
    }

    @PutMapping(UPDATE_ADDRESS)
    public ResponseEntity<Boolean> updateAddress(@RequestBody UserUpdateRequest request)   {
        return ResponseEntity.ok(userService.updateAddress(request));
    }

    @PutMapping(UPDATE_BIRTHDAY)
    public ResponseEntity<Boolean> updateBirthday(@RequestBody UserUpdateRequest request)   {
        return ResponseEntity.ok(userService.updateBirthday(request));
    }

    @PutMapping(UPDATE_FACEBOOK)
    public ResponseEntity<Boolean> updateFacebook(@RequestBody UserUpdateRequest request)   {
        return ResponseEntity.ok(userService.updateFacebook(request));
    }

    @PutMapping(UPDATE_GENDER)
    public ResponseEntity<Boolean> updateGender(@RequestBody UserUpdateRequest request)   {
        return ResponseEntity.ok(userService.updateGender(request));
    }

    @PutMapping(UPDATE_PHONE)
    public ResponseEntity<Boolean> updatePhoneNumber(@RequestBody UserUpdateRequest request)   {
        return ResponseEntity.ok(userService.updatePhoneNumber(request));
    }

    @PutMapping(CHANGE_PASSWORD)
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequest request) {
        return ResponseEntity.ok(userService.changePassword(request));
    }

    @GetMapping(INFO)
    public ResponseEntity<UserProjectResponse> getAccountInfo() {
        return ResponseEntity.ok(userService.getUserInfo());
    }

    @PostMapping(ADMIN_ASSIGN_EMPLOYEE)
    public ResponseEntity<Boolean> assignEmployeeRole(@PathVariable("userId") Long userId) {
            return ResponseEntity.ok(userService.assignEmployeeRole(userId));
    }

    @GetMapping(ADMIN_EMPLOYEE_SEARCH_ID)
    public ResponseEntity<List<UserRolesResponse>> searchEmployeeId(@PathVariable("id") Long employeeId) {
        return ResponseEntity.ok(userService.searchEmployeeById(employeeId));
    }

    @GetMapping(ADMIN_EMPLOYEE_SEARCH_NAME)
    public ResponseEntity<List<UserRolesResponse>> searchEmployeeName(@PathVariable("name") String employeeName) {
        return ResponseEntity.ok(userService.searchEmployeeByName(employeeName));
    }

    @GetMapping(ADMIN_EMPLOYEE_SEARCH_PHONE_NUMBER)
    public ResponseEntity<List<UserRolesResponse>> searchEmployeePhoneNumber(@PathVariable("number") String phoneNumber) {
        return ResponseEntity.ok(userService.searchEmployeeByPhoneNumber(phoneNumber));
    }

    @GetMapping(ADMIN_EMPLOYEE_SEARCH_STATUS)
    public ResponseEntity<List<UserRolesResponse>> searchEmployeeStatus(@PathVariable("status") String status) {
        return ResponseEntity.ok(userService.searchEmployeeByStatus(status));
    }

    @GetMapping(ADMIN_EMPLOYEE)
    public ResponseEntity<List<UserRolesResponse>> getEmployee() {
        return ResponseEntity.ok(userService.findAllByRoleEmployee());
    }

    @GetMapping(ADMIN_CUSTOMER)
    public ResponseEntity<List<UserRolesResponse>> getCustomer() {
        return ResponseEntity.ok(userService.getAllCustomers());
    }

    @GetMapping(ADMIN_CUSTOMER_SEARCH_ID)
    public ResponseEntity<List<UserRolesResponse>> searchCustomerId(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.searchCustomerById(id));
    }

    @GetMapping(ADMIN_CUSTOMER_SEARCH_NAME)
    public ResponseEntity<List<UserRolesResponse>> searchCustomerName(@PathVariable("name") String customerName) {
        return ResponseEntity.ok(userService.searchCustomerByName(customerName));
    }

    @GetMapping(ADMIN_CUSTOMER_SEARCH_PHONE_NUMBER)
    public ResponseEntity<List<UserRolesResponse>> searchCustomerPhoneNumber(@PathVariable("number") String phoneNumber) {
        return ResponseEntity.ok(userService.searchCustomerByPhone(phoneNumber));
    }

    @GetMapping(ADMIN_CUSTOMER_SEARCH_EMAIL)
    public ResponseEntity<List<UserRolesResponse>> searchCustomerEmail(@PathVariable("email") String email) {
        return ResponseEntity.ok(userService.searchCustomerByEmail(email));
    }

    @GetMapping(ADMIN_CUSTOMER_SEARCH_STATUS)
    public ResponseEntity<List<UserRolesResponse>> searchCustomerActive(@PathVariable("status") String status) {
        return ResponseEntity.ok(userService.searchCustomerByStatus(status));
    }

    @GetMapping(ADMIN_CUSTOMER_SEARCH_GENDER)
    public ResponseEntity<List<UserRolesResponse>> searchCustomerNumber(@PathVariable("gender") String gender) {
        return ResponseEntity.ok(userService.searchCustomerByGender(gender));
    }

    @PostMapping(UPLOAD)
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
        return ResponseEntity.ok(firebaseStorageService.uploadFile(file));
    }
}
