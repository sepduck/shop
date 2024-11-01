package com.qlyshopphone_backend.controller.auth;

import com.qlyshopphone_backend.dto.request.CustomerInfoRequest;
import com.qlyshopphone_backend.repository.projection.CustomerInfoProjection;
import com.qlyshopphone_backend.service.CustomerInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.qlyshopphone_backend.constant.PathConstant.*;

@RestController
@RequestMapping(API_V1_CUSTOMER_INFO)
@RequiredArgsConstructor
public class CustomerInfoController {
    private final CustomerInfoService customerInfoService;

    @GetMapping
    public ResponseEntity<List<CustomerInfoProjection>> getCustomerInfoFromUser() {
        return ResponseEntity.ok(customerInfoService.getCustomerInfo());
    }

    @GetMapping(ID)
    public ResponseEntity<CustomerInfoProjection> getCustomerInfoByIdFromUser(@PathVariable("id") Long id) {
        return ResponseEntity.ok(customerInfoService.getCustomerInfoByIdFromUser(id));
    }

    @PostMapping()
    public ResponseEntity<Boolean> createCustomerInfo(@RequestBody CustomerInfoRequest customerInfoRequest) {
        return ResponseEntity.ok(customerInfoService.createCustomerInfo(customerInfoRequest));
    }

    @PutMapping(ID)
    public ResponseEntity<?> updateCustomerInfo(@PathVariable("id") Long id, @RequestBody CustomerInfoRequest customerInfoRequest) {
        return ResponseEntity.ok(customerInfoService.updateCustomerInfo(id, customerInfoRequest));
    }

    @DeleteMapping(ID)
    public ResponseEntity<?> deleteCustomerInfo(@PathVariable("id") Long id) {
        return ResponseEntity.ok(customerInfoService.deleteCustomerInfo(id));
    }
}
