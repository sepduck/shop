package com.qlyshopphone_backend.controller.rest;

import com.qlyshopphone_backend.dto.UsersDTO;
import com.qlyshopphone_backend.service.AuthenticationService;
import com.qlyshopphone_backend.service.CustomerService;
import com.qlyshopphone_backend.service.EmployeeService;
import com.qlyshopphone_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping()
@RequiredArgsConstructor
public class RestEmployeeController {
    private final EmployeeService employeeService;
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ACCOUNTANT')")
    @GetMapping("/employee")
    public ResponseEntity<?> getEmployee() {
        return employeeService.getAllEmployees();
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ACCOUNTANT')")
    @PostMapping("/employee")
    public ResponseEntity<?> saveEmployee(@ModelAttribute UsersDTO usersDTO) throws Exception {
        usersDTO.setEmployee(true);
        usersDTO.setDeleteUser(false);
        usersDTO.setRoleId(3);
        return authenticationService.register(usersDTO);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ACCOUNTANT')")
    @PostMapping("/employee-role/{userId}")
    public ResponseEntity<?> saveEmployeeRoles(@PathVariable("userId") int userId) {
        try {
            employeeService.saveEmployeeRoles(userId);
            return ResponseEntity.ok().body("User saved");
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ACCOUNTANT')")
    @PutMapping("/employee/{userId}")
    public ResponseEntity<?> updateEmployee(@PathVariable("userId") int userId,
                                            @ModelAttribute UsersDTO usersDTO) throws Exception{
        return ResponseEntity.ok(userService.updateUser(userId, usersDTO));
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ACCOUNTANT')")
    @DeleteMapping("/employee/{employeeId}")
    public ResponseEntity<?> deleteEmployee(@PathVariable("employeeId") int employeeId) {
        return employeeService.deleteEmployeeById(employeeId);
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ACCOUNTANT')")
    @GetMapping("/employee/findById/{userId}")
    public ResponseEntity<?> getEmployeeById(@PathVariable("userId") int userId) {
        return employeeService.getEmployeeById(userId);
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ACCOUNTANT')")
    @GetMapping("/employee/search-id/{employeeId}")
    public ResponseEntity<?> searchEmployeeId(@PathVariable("employeeId") int employeeId) {
        return employeeService.searchEmployeeById(employeeId);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ACCOUNTANT')")
    @GetMapping("/employee/search-name/{employee-name}")
    public ResponseEntity<?> searchEmployeeName(@PathVariable("employee-name") String employeeName) {
        return employeeService.searchEmployeeByName(employeeName);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ACCOUNTANT')")
    @GetMapping("/employee/search-phone-number/{phone-number}")
    public ResponseEntity<?> searchEmployeePhoneNumber(@PathVariable("phone-number") String phoneNumber) {
        return employeeService.searchEmployeeByPhoneNumber(phoneNumber);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ACCOUNTANT')")
    @GetMapping("/employee/search-active/{number}")
    public ResponseEntity<?> searchEmployeeActive(@PathVariable("number") int number) {
        return employeeService.searchEmployeeByActive(number);
    }
}
