package com.qlyshopphone_backend.controller.rest;
import static com.qlyshopphone_backend.constant.PathConstant.*;

import com.github.javafaker.Faker;
import com.qlyshopphone_backend.dto.GroupSupplierDTO;
import com.qlyshopphone_backend.dto.SupplierDTO;
import com.qlyshopphone_backend.exceptions.DataNotFoundException;
import com.qlyshopphone_backend.model.Users;
import com.qlyshopphone_backend.service.SupplierService;
import com.qlyshopphone_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping()
@RequiredArgsConstructor
public class RestSupplierController {
    private final SupplierService supplierService;
    private final UserService userService;

    @GetMapping(ADMIN_SUPPLIER)
    public ResponseEntity<?> getSupplier() {
        return ResponseEntity.ok(supplierService.getSupplier());
    }
    
    @GetMapping(ADMIN_SUPPLIERS)
    public ResponseEntity<?> getAllSuppliers() {
        return ResponseEntity.ok(supplierService.getAllSuppliers());
    }
    
    @PostMapping(ADMIN_SUPPLIER)
    public ResponseEntity<?> saveSuppliers(@RequestBody SupplierDTO supplierDTO,
                                           Principal principal) {
        Users users = userService.findByUsername(principal.getName());
        supplierDTO.setDeleteProduct(false);
        return ResponseEntity.ok(supplierService.saveSuppliers(supplierDTO, users));
    }

    @PutMapping(ADMIN_SUPPLIER_ID)
    public ResponseEntity<?> updateSupplier(@PathVariable Long id,
                                            @RequestBody SupplierDTO supplierDTO,
                                            Principal principal) throws Exception {
        Users users = userService.findByUsername(principal.getName());
        return ResponseEntity.ok(ResponseEntity.ok(supplierService.updateSuppliers(id, supplierDTO, users)));
    }

    @DeleteMapping(ADMIN_SUPPLIER_ID)
    public ResponseEntity<?> deleteSuppliers(@PathVariable Long id, Principal principal) {
        Users users = userService.findByUsername(principal.getName());
        return ResponseEntity.ok(supplierService.deleteSuppliers(id, users));
    }

    @GetMapping(ADMIN_SUPPLIERS_SEARCH_PHONE_NUMBER)
    public ResponseEntity<?> searchAllByPhoneNumber(@PathVariable("number") String phoneNumber) {
        return ResponseEntity.ok(supplierService.searchByPhoneNumber(phoneNumber));
    }

    @GetMapping(ADMIN_SUPPLIERS_SEARCH_TAX_CODE_NUMBER)
    public ResponseEntity<?> searchAllByTaxCode(@PathVariable("number") String taxCode) {
        return ResponseEntity.ok(supplierService.searchByTaxCode(taxCode));
    }

    @GetMapping(ADMIN_SUPPLIERS_SEARCH_NAME)
    public ResponseEntity<?> searchSupplierName(@PathVariable("name") String supplierName) {
        return ResponseEntity.ok(supplierService.searchBySupplierName(supplierName));
    }

    @GetMapping(ADMIN_SUPPLIERS_SEARCH_GROUP_SUPPLIER_NUMBER)
    public ResponseEntity<?> searchByGroupSupplier(@PathVariable Long number){
        return ResponseEntity.ok(supplierService.searchByGroupSupplier(number));
    }

    @GetMapping(ADMIN_SUPPLIERS_SEARCH_SUPPLIER_ACTIVE_NUMBER)
    public ResponseEntity<?> searchActive(@PathVariable int number){
        return ResponseEntity.ok(supplierService.searchNoActive(number));
    }
    
    
    @GetMapping(ADMIN_GROUP_SUPPLIER)
    public ResponseEntity<?> getAllGroupSupplier() {
        return ResponseEntity.ok(supplierService.getAllGroupSupplier());
    }

    
    @PostMapping(ADMIN_GROUP_SUPPLIER)
    public ResponseEntity<?> saveGroupSupplier(@RequestBody GroupSupplierDTO groupSupplierDTO) {
        return ResponseEntity.ok(supplierService.saveGroupSupplier(groupSupplierDTO));
    }

    
    @PutMapping(ADMIN_GROUP_SUPPLIER_ID)
    public ResponseEntity<?> updateGroupSupplier(@RequestBody GroupSupplierDTO groupSupplierDTO,
                                                 @PathVariable Long id) {
        return ResponseEntity.ok(supplierService.updateGroupSupplier(groupSupplierDTO,id));
    }

    @DeleteMapping(ADMIN_GROUP_SUPPLIER_ID)
    public ResponseEntity<?> deleteGroupSupplier(@PathVariable Long id) {
        return ResponseEntity.ok(supplierService.deleteGroupSupplier(id));
    }

}
