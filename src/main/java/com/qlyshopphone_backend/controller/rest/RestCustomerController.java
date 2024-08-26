package com.qlyshopphone_backend.controller.rest;

import com.qlyshopphone_backend.dto.UsersDTO;
import com.qlyshopphone_backend.service.CustomerService;
import com.qlyshopphone_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping()
@RequiredArgsConstructor
public class RestCustomerController {
    private final CustomerService customerService;
    private final UserService userService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ACCOUNTANT')")
    @GetMapping("/customer")
    public ResponseEntity<?> getCustomer(){
        return customerService.getAllCustomers();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ACCOUNTANT')")
    @PutMapping("/customer/{userId}")
    public ResponseEntity<?> updateCustomer(@PathVariable int userId,
                                            @ModelAttribute UsersDTO usersDTO) throws Exception{
        return userService.updateUser(userId, usersDTO);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ACCOUNTANT')")
    @DeleteMapping("/customer/{customerId}")
    public ResponseEntity<?> deleteCustomer(@PathVariable("customerId") int customerId){
        return customerService.deleteCustomerById(customerId);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ACCOUNTANT')")
    @GetMapping("/customer/findById/{userId}")
    public ResponseEntity<?> getCustomerById(@PathVariable("userId") int userId){
        return customerService.getCustomerById(userId);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ACCOUNTANT')")
    @GetMapping("/customer/search-id/{customerId}")
    public ResponseEntity<?> searchCustomerId(@PathVariable("customerId") int customerId){
        return customerService.searchCustomerById(customerId);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ACCOUNTANT')")
    @GetMapping("/customer/search-name/{customer-name}")
    public ResponseEntity<?> searchCustomerName(@PathVariable("customer-name") String customerName){
        return customerService.searchCustomerByName(customerName);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ACCOUNTANT')")
    @GetMapping("/customer/search-phone-number/{phone-number}")
    public ResponseEntity<?> searchCustomerPhoneNumber(@PathVariable("phone-number") String phoneNumber){
        return customerService.searchCustomerByPhone(phoneNumber);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ACCOUNTANT')")
    @GetMapping("/customer/search-email/{email}")
    public ResponseEntity<?> searchCustomerEmail(@PathVariable("email") String email){
        return customerService.searchCustomerByEmail(email);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ACCOUNTANT')")
    @GetMapping("/customer/search-address/{address}")
    public ResponseEntity<?> searchCustomerAddress(@PathVariable("address") String address){
        return customerService.searchCustomerByAddress(address);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ACCOUNTANT')")
    @GetMapping("/customer/search-active/{active}")
    public ResponseEntity<?> searchCustomerActive(@PathVariable("active") byte active){
        return customerService.searchCustomerByActive(active);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ACCOUNTANT')")
    @GetMapping("/customer/search-gender/{number}")
    public ResponseEntity<?> searchCustomerNumber(@PathVariable("number") int number){
        return customerService.searchCustomerByGender(number);
    }
}
