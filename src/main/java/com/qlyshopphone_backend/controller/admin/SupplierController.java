package com.qlyshopphone_backend.controller.admin;

import static com.qlyshopphone_backend.constant.PathConstant.*;

import com.qlyshopphone_backend.dto.request.ProductAttributeRequest;
import com.qlyshopphone_backend.dto.request.SupplierRequest;
import com.qlyshopphone_backend.dto.response.ProductAttributeResponse;
import com.qlyshopphone_backend.repository.projection.SupplierProjection;
import com.qlyshopphone_backend.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(API_V1_SUPPLIER)
@RequiredArgsConstructor
public class SupplierController {
    private final SupplierService supplierService;

    @GetMapping()
    public ResponseEntity<List<SupplierProjection>> getAllSuppliers(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(supplierService.getSupplier(pageable));
    }

    @PostMapping()
    public ResponseEntity<?> saveSuppliers(@RequestBody SupplierRequest request) {
        return ResponseEntity.ok(supplierService.createSupplier(request));
    }

    @PutMapping(NAME_ID)
    public ResponseEntity<Boolean> updateSupplierName(@RequestBody SupplierRequest request,
                                                               @PathVariable Long id) {
        return ResponseEntity.ok(supplierService.updateSupplierName(request, id));
    }
    @PutMapping(PHONE_ID)
    public ResponseEntity<Boolean> updateSupplierPhone(@RequestBody SupplierRequest request,
                                                      @PathVariable Long id) {
        return ResponseEntity.ok(supplierService.updateSupplierPhone(request, id));
    }
    @PutMapping(EMAIL_ID)
    public ResponseEntity<Boolean> updateSupplierEmail(@RequestBody SupplierRequest request,
                                                      @PathVariable Long id) {
        return ResponseEntity.ok(supplierService.updateSupplierEmail(request, id));
    }
    @PutMapping(COMPANY)
    public ResponseEntity<Boolean> updateSupplierCompany(@RequestBody SupplierRequest request,
                                                      @PathVariable Long id) {
        return ResponseEntity.ok(supplierService.updateSupplierCompany(request, id));
    }
    @PutMapping(TAX_CODE)
    public ResponseEntity<Boolean> updateSupplierTaxCode(@RequestBody SupplierRequest request,
                                                      @PathVariable Long id) {
        return ResponseEntity.ok(supplierService.updateSupplierTaxCode(request, id));
    }
    @PutMapping(GROUP_IN_SUPPLIER)
    public ResponseEntity<Boolean> updateInfoGroupInSupplier(@RequestBody SupplierRequest request,
                                                      @PathVariable Long id) {
        return ResponseEntity.ok(supplierService.updateInfoGroupInSupplier(request, id));
    }
    @PutMapping(ADDRESS_ID)
    public ResponseEntity<Boolean> updateSupplierAddress(@RequestBody SupplierRequest request,
                                                      @PathVariable Long id) {
        return ResponseEntity.ok(supplierService.updateSupplierAddress(request, id));
    }

    @DeleteMapping(ID)
    public ResponseEntity<?> inactiveSupplier(@PathVariable Long id) {
        return ResponseEntity.ok(supplierService.inactiveSupplier(id));
    }

    @GetMapping(GROUP_SUPPLIER)
    public ResponseEntity<List<ProductAttributeResponse>> getAllGroupSupplier() {
        return ResponseEntity.ok(supplierService.getAllGroupSupplier());
    }


    @PostMapping(GROUP_SUPPLIER)
    public ResponseEntity<ProductAttributeResponse> saveGroupSupplier(@RequestBody ProductAttributeRequest request) {
        return ResponseEntity.ok(supplierService.createGroupSupplier(request));
    }


    @PutMapping(GROUP_SUPPLIER_ID)
    public ResponseEntity<ProductAttributeResponse> updateGroupSupplier(@RequestBody ProductAttributeRequest request,
                                                                        @PathVariable Long id) {
        return ResponseEntity.ok(supplierService.updateGroupSupplier(request, id));
    }


//    @GetMapping(SEARCH_PHONE_NUMBER)
//    public ResponseEntity<?> searchAllByPhoneNumber(@PathVariable("number") String phoneNumber) {
//        return ResponseEntity.ok(supplierService.searchByPhoneNumber(phoneNumber));
//    }
//
//    @GetMapping(SEARCH_TAX_CODE_NUMBER)
//    public ResponseEntity<?> searchAllByTaxCode(@PathVariable("number") String taxCode) {
//        return ResponseEntity.ok(supplierService.searchByTaxCode(taxCode));
//    }

//    @GetMapping(SEARCH_NAME)
//    public ResponseEntity<?> searchSupplierName(@PathVariable("name") String supplierName) {
//        return ResponseEntity.ok(supplierService.searchBySupplierName(supplierName));
//    }

//    @GetMapping(SEARCH_GROUP_SUPPLIER_NUMBER)
//    public ResponseEntity<?> searchByGroupSupplier(@PathVariable Long number) {
//        return ResponseEntity.ok(supplierService.searchByGroupSupplier(number));
//    }

//    @GetMapping(SEARCH_SUPPLIER_ACTIVE_NUMBER)
//    public ResponseEntity<?> searchActive(@PathVariable int number) {
//        return ResponseEntity.ok(supplierService.searchNoActive(number));
//    }
}
