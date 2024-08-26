package com.qlyshopphone_backend.controller.rest;

import com.qlyshopphone_backend.dto.CustomerInfoDTO;
import com.qlyshopphone_backend.mapper.CustomerInfoMapper;
import com.qlyshopphone_backend.model.Users;
import com.qlyshopphone_backend.service.CustomerInfoService;
import com.qlyshopphone_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping()
@RequiredArgsConstructor
public class RestCustomerInfoController {
    private final UserService userService;
    private final CustomerInfoService customerInfoService;

    @GetMapping("/customer-info")
    public ResponseEntity<?> viewCustomerInfo(Principal principal) {
        Users user = userService.findByUsername(principal.getName());
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        List<CustomerInfoDTO> customerInfoDTOS = user.getCustomerInfo()
                .stream()
                .filter(customerInfo -> !customerInfo.isDeleteCustomerInfo())
                .map(CustomerInfoMapper::infoDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(customerInfoDTOS);
    }

    @PostMapping("/customer-info")
    public ResponseEntity<?> createCustomerInfo(@RequestBody CustomerInfoDTO customerInfoDTO, Principal principal) {
        Users users = userService.findByUsername(principal.getName());
        if (users == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
        }
        int userId = users.getUserId();
        customerInfoDTO.setUserId(userId);

        // Gọi service để tạo và lưu CustomerInfo
        return ResponseEntity.ok(customerInfoService.createCustomerInfo(customerInfoDTO));
    }

    @PutMapping("/customer-info/{customerId}")
    public ResponseEntity<?> updateCustomerInfo(@PathVariable int customerId, @RequestBody CustomerInfoDTO customerInfoDTO) {
        return ResponseEntity.ok(customerInfoService.updateCustomerInfo(customerId, customerInfoDTO));
    }

    @DeleteMapping("/customer-info/{customerId}")
    public ResponseEntity<?> deleteCustomerInfo(@PathVariable int customerId) {
        customerInfoService.deleteCustomerInfo(customerId);
        return ResponseEntity.noContent().build();
    }
}
