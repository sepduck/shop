package com.qlyshopphone_backend.controller.rest;

import com.qlyshopphone_backend.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping()
@RequiredArgsConstructor
public class SupplierController {
    private final SupplierService supplierService;

//    @GetMapping(ADMIN_SUPPLIER)
//    public ResponseEntity<?> getSupplier() {
//        return ResponseEntity.ok(supplierService.getSupplier());
//    }
//
//    @GetMapping(ADMIN_SUPPLIERS)
//    public ResponseEntity<?> getAllSuppliers() {
//        return ResponseEntity.ok(supplierService.getAllSuppliers());
//    }
//
//    @PostMapping(ADMIN_SUPPLIER)
//    public ResponseEntity<?> saveSuppliers(@RequestBody SupplierRequest supplierRequest) {
//        supplierRequest.setDeleteProduct(false);
//        return ResponseEntity.ok(supplierService.saveSuppliers(supplierRequest));
//    }
//
//    @PutMapping(ADMIN_SUPPLIER_ID)
//    public ResponseEntity<?> updateSupplier(@PathVariable Long id,
//                                            @RequestBody SupplierRequest supplierRequest) throws Exception {
//        return ResponseEntity.ok(ResponseEntity.ok(supplierService.updateSuppliers(id, supplierRequest)));
//    }
//
//    @DeleteMapping(ADMIN_SUPPLIER_ID)
//    public ResponseEntity<?> deleteSuppliers(@PathVariable Long id) {
//        return ResponseEntity.ok(supplierService.deleteSuppliers(id));
//    }
//
//    @GetMapping(ADMIN_SUPPLIERS_SEARCH_PHONE_NUMBER)
//    public ResponseEntity<?> searchAllByPhoneNumber(@PathVariable("number") String phoneNumber) {
//        return ResponseEntity.ok(supplierService.searchByPhoneNumber(phoneNumber));
//    }
//
//    @GetMapping(ADMIN_SUPPLIERS_SEARCH_TAX_CODE_NUMBER)
//    public ResponseEntity<?> searchAllByTaxCode(@PathVariable("number") String taxCode) {
//        return ResponseEntity.ok(supplierService.searchByTaxCode(taxCode));
//    }
//
//    @GetMapping(ADMIN_SUPPLIERS_SEARCH_NAME)
//    public ResponseEntity<?> searchSupplierName(@PathVariable("name") String supplierName) {
//        return ResponseEntity.ok(supplierService.searchBySupplierName(supplierName));
//    }
//
//    @GetMapping(ADMIN_SUPPLIERS_SEARCH_GROUP_SUPPLIER_NUMBER)
//    public ResponseEntity<?> searchByGroupSupplier(@PathVariable Long number){
//        return ResponseEntity.ok(supplierService.searchByGroupSupplier(number));
//    }
//
//    @GetMapping(ADMIN_SUPPLIERS_SEARCH_SUPPLIER_ACTIVE_NUMBER)
//    public ResponseEntity<?> searchActive(@PathVariable int number){
//        return ResponseEntity.ok(supplierService.searchNoActive(number));
//    }
//
//
//    @GetMapping(ADMIN_GROUP_SUPPLIER)
//    public ResponseEntity<?> getAllGroupSupplier() {
//        return ResponseEntity.ok(supplierService.getAllGroupSupplier());
//    }
//
//
//    @PostMapping(ADMIN_GROUP_SUPPLIER)
//    public ResponseEntity<?> saveGroupSupplier(@RequestBody GroupSupplierRequest groupSupplierRequest) {
//        return ResponseEntity.ok(supplierService.saveGroupSupplier(groupSupplierRequest));
//    }
//
//
//    @PutMapping(ADMIN_GROUP_SUPPLIER_ID)
//    public ResponseEntity<?> updateGroupSupplier(@RequestBody GroupSupplierRequest groupSupplierRequest,
//                                                 @PathVariable Long id) {
//        return ResponseEntity.ok(supplierService.updateGroupSupplier(groupSupplierRequest,id));
//    }
//
//    @DeleteMapping(ADMIN_GROUP_SUPPLIER_ID)
//    public ResponseEntity<?> deleteGroupSupplier(@PathVariable Long id) {
//        return ResponseEntity.ok(supplierService.deleteGroupSupplier(id));
//    }

}
