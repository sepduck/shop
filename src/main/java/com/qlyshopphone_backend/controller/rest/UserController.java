package com.qlyshopphone_backend.controller.rest;

import static com.qlyshopphone_backend.constant.PathConstant.*;

import com.qlyshopphone_backend.dto.request.PasswordChangeRequest;
import com.qlyshopphone_backend.dto.request.UserRequest;
import com.qlyshopphone_backend.service.AuthenticationService;
import com.qlyshopphone_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping()
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @GetMapping(ADMIN_USERS)
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PutMapping(PASSWORD)
    public ResponseEntity<?> updatePassword(@RequestBody PasswordChangeRequest request) {
        return ResponseEntity.ok(userService.updatePassword(request));
    }

    @PutMapping(USERS_INFO)
    public ResponseEntity<?> updateUserInfo(@RequestBody UserRequest userRequest) throws Exception {
        return ResponseEntity.ok(userService.updateUserInfo(userRequest));
    }

    @PutMapping(USERS_INFO_FILE)
    public ResponseEntity<?> updateInfoFile(@RequestBody UserRequest userRequest) throws Exception {
        return ResponseEntity.ok(userService.updateUserInfoFile(userRequest));
    }

    @DeleteMapping(ADMIN_USERS_ID)
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.deleteUser(id));
    }

    @GetMapping(INFO)
    public ResponseEntity<Map<String, Object>> getAccountInfo() {
        return ResponseEntity.ok(userService.getUserInfo());
    }

    @GetMapping(ADMIN_EMPLOYEE)
    public ResponseEntity<?> getEmployee() {
        return ResponseEntity.ok(userService.getAllEmployees());
    }

    @PostMapping(ADMIN_EMPLOYEE)
    public ResponseEntity<?> saveEmployee(@ModelAttribute UserRequest userRequest) throws Exception {
        userRequest.setEmployee(true);

        return ResponseEntity.ok(authenticationService.register(userRequest));
    }

    @PostMapping(ADMIN_EMPLOYEE_ROLE_USER_ID)
    public ResponseEntity<?> saveEmployeeRoles(@PathVariable("userId") Long userId) {
        try {
            userService.saveEmployeeRoles(userId);
            return ResponseEntity.ok(ResponseEntity.ok().body("User saved"));
        } catch (Exception e) {
            return ResponseEntity.ok(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage()));
        }
    }

    @PutMapping(ADMIN_EMPLOYEE_ID)
    public ResponseEntity<?> updateEmployee(@PathVariable("id") Long id,
                                            @ModelAttribute UserRequest userRequest) throws Exception {
        return ResponseEntity.ok(ResponseEntity.ok(userService.updateUser(id, userRequest)));
    }

    @DeleteMapping(ADMIN_EMPLOYEE_ID)
    public ResponseEntity<?> deleteEmployee(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.deleteEmployeeById(id));
    }

    @GetMapping(ADMIN_EMPLOYEE_SEARCH_ID)
    public ResponseEntity<?> searchEmployeeId(@PathVariable("id") Long employeeId) {
        return ResponseEntity.ok(userService.searchEmployeeById(employeeId));
    }

    @GetMapping(ADMIN_EMPLOYEE_SEARCH_NAME)
    public ResponseEntity<?> searchEmployeeName(@PathVariable("name") String employeeName) {
        return ResponseEntity.ok(userService.searchEmployeeByName(employeeName));
    }

    @GetMapping(ADMIN_EMPLOYEE_SEARCH_PHONE_NUMBER)
    public ResponseEntity<?> searchEmployeePhoneNumber(@PathVariable("number") String phoneNumber) {
        return ResponseEntity.ok(userService.searchEmployeeByPhoneNumber(phoneNumber));
    }

    @GetMapping(ADMIN_EMPLOYEE_SEARCH_ACTIVE_NUMBER)
    public ResponseEntity<?> searchEmployeeActive(@PathVariable("number") int number) {
        return ResponseEntity.ok(userService.searchEmployeeByActive(number));
    }

    @GetMapping(ADMIN_CUSTOMER)
    public ResponseEntity<?> getCustomer() {
        return ResponseEntity.ok(userService.getAllCustomers());
    }

    @PutMapping(ADMIN_CUSTOMER_ID)
    public ResponseEntity<?> updateCustomer(@PathVariable Long id,
                                            @ModelAttribute UserRequest userRequest) throws Exception {
        return ResponseEntity.ok(userService.updateUser(id, userRequest));
    }

    @DeleteMapping(ADMIN_CUSTOMER_ID)
    public ResponseEntity<?> deleteCustomer(@PathVariable("id") Long customerId) {
        return ResponseEntity.ok(userService.deleteCustomerById(customerId));
    }

    @GetMapping(ADMIN_CUSTOMER_SEARCH_ID)
    public ResponseEntity<?> searchCustomerId(@PathVariable("id") Long customerId) {
        return ResponseEntity.ok(userService.searchCustomerById(customerId));
    }

    @GetMapping(ADMIN_CUSTOMER_SEARCH_NAME)
    public ResponseEntity<?> searchCustomerName(@PathVariable("name") String customerName) {
        return ResponseEntity.ok(userService.searchCustomerByName(customerName));
    }

    @GetMapping(ADMIN_CUSTOMER_SEARCH_PHONE_NUMBER)
    public ResponseEntity<?> searchCustomerPhoneNumber(@PathVariable("number") String phoneNumber) {
        return ResponseEntity.ok(userService.searchCustomerByPhone(phoneNumber));
    }

    @GetMapping(ADMIN_CUSTOMER_SEARCH_EMAIL)
    public ResponseEntity<?> searchCustomerEmail(@PathVariable("email") String email) {
        return ResponseEntity.ok(userService.searchCustomerByEmail(email));
    }

    @GetMapping(ADMIN_CUSTOMER_SEARCH_ADDRESS)
    public ResponseEntity<?> searchCustomerAddress(@PathVariable("address") String address) {
        return ResponseEntity.ok(userService.searchCustomerByAddress(address));
    }

    @GetMapping(ADMIN_CUSTOMER_SEARCH_ACTIVE_NUMBER)
    public ResponseEntity<?> searchCustomerActive(@PathVariable("number") byte active) {
        return ResponseEntity.ok(userService.searchCustomerByActive(active));
    }

    @GetMapping(ADMIN_CUSTOMER_SEARCH_GENDER_NUMBER)
    public ResponseEntity<?> searchCustomerNumber(@PathVariable("number") int number) {
        return ResponseEntity.ok(userService.searchCustomerByGender(number));
    }
}
