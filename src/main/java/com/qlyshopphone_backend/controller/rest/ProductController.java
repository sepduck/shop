package com.qlyshopphone_backend.controller.rest;

import static com.qlyshopphone_backend.constant.PathConstant.*;

import com.qlyshopphone_backend.dto.request.*;
import com.qlyshopphone_backend.dto.response.ProductAttributeResponse;
import com.qlyshopphone_backend.dto.response.ProductResponse;
import com.qlyshopphone_backend.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(API_V1)
@RequiredArgsConstructor
@Validated
public class ProductController {
    private final ProductService productService;

    @GetMapping(PRODUCT)
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @PostMapping(ADMIN_PRODUCT)
    public ResponseEntity<String> addProduct(@Validated @ModelAttribute ProductRequest request,
                                             BindingResult result) throws Exception {
        if (result.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            for (FieldError error : result.getFieldErrors()) {
                errors.append(error.getDefaultMessage()).append("\n");
            }
            return ResponseEntity.badRequest().body(errors.toString());
        }
//        request.se(false);
        return ResponseEntity.ok(productService.saveProduct(request));
    }

    @PutMapping(ADMIN_PRODUCT_PRODUCT_ID)
    public ResponseEntity<?> updateProduct(@PathVariable("productId") Long productId,
                                           @ModelAttribute ProductRequest productRequest) throws Exception {
        return ResponseEntity.ok(productService.updateProduct(productId, productRequest));
    }

    @DeleteMapping(ADMIN_PRODUCT_PRODUCT_ID)
    public ResponseEntity<?> deleteProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(productService.deleteProduct(productId));
    }

    @GetMapping(PRODUCT_SEARCH_NAME)
    public ResponseEntity<?> searchAllByProductName(@PathVariable("name") String name) {
        return ResponseEntity.ok(productService.searchAllByProductName(name));
    }

    @GetMapping(PRODUCT_SEARCH_ID)
    public ResponseEntity<?> searchAllByProductId(@PathVariable("id") Long id) {
        return ResponseEntity.ok(productService.searchAllByProductId(id));
    }

    @GetMapping(ADMIN_PRODUCT_SEARCH_INVENTORY_NUMBER)
    public ResponseEntity<?> searchInventory(@PathVariable("number") int number) {
        return ResponseEntity.ok(productService.searchInventory(number));
    }

    @GetMapping(ADMIN_PRODUCT_SEARCH_ACTIVE_NUMBER)
    public ResponseEntity<?> searchActive(@PathVariable("number") int number) {
        return ResponseEntity.ok(productService.searchActive(number));
    }

    @GetMapping(PRODUCT_SEARCH_GROUP_PRODUCT_ID)
    public ResponseEntity<?> searchGroupProductId(@PathVariable("id") Long id) {
        return ResponseEntity.ok(productService.searchGroupProductId(id));
    }

    @GetMapping(PRODUCT_SEARCH_LOCATION_ID)
    public ResponseEntity<?> searchByLocationId(@PathVariable("id") Long id) {
        return ResponseEntity.ok(productService.searchByLocationId(id));
    }

    @GetMapping(PRODUCT_SEARCH_CATEGORY_NUMBER)
    public ResponseEntity<?> searchCategory(@PathVariable int number) {
        return ResponseEntity.ok(productService.searchCategory(number));
    }

    @GetMapping(ADMIN_PRODUCT_SEARCH_DIRECT_SALES_NUMBER)
    public ResponseEntity<?> searchDirectSales(@PathVariable int number) {
        return ResponseEntity.ok(productService.searchDirectSales(number));
    }

    @GetMapping(GROUP_PRODUCT)
    public ResponseEntity<List<ProductAttributeResponse>> getAllGroupProduct() {
        return ResponseEntity.ok(productService.getAllGroupProduct());
    }

    @PostMapping(ADMIN_GROUP_PRODUCT)
    public ResponseEntity<ProductAttributeResponse> saveGroupProduct(@ModelAttribute ProductAttributeRequest request) {
        return ResponseEntity.ok(productService.saveGroupProduct(request));
    }

    @PutMapping(ADMIN_GROUP_PRODUCT_ID)
    public ResponseEntity<ProductAttributeResponse> updateGroupProduct(@RequestBody ProductAttributeRequest request,
                                                                       @PathVariable Long id) {
        return ResponseEntity.ok(productService.updateGroupProduct(request, id));
    }

    @DeleteMapping(ADMIN_GROUP_PRODUCT_ID)
    public ResponseEntity<Boolean> deleteGroupProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productService.deleteGroupProduct(id));
    }

    @GetMapping(TRADEMARK)
    public ResponseEntity<List<ProductAttributeResponse>> getAllTrademark() {
        return ResponseEntity.ok(productService.getAllTrademarks());
    }

    @PostMapping(ADMIN_TRADEMARK)
    public ResponseEntity<ProductAttributeResponse> saveTrademark(@RequestBody ProductAttributeRequest request) {
        return ResponseEntity.ok(productService.saveTrademark(request));
    }

    @PutMapping(ADMIN_TRADEMARK_ID)
    public ResponseEntity<ProductAttributeResponse> updateTrademark(@RequestBody ProductAttributeRequest request,
                                                                    @PathVariable Long id) {
        return ResponseEntity.ok(productService.updateTrademark(request, id));
    }

    @DeleteMapping(ADMIN_TRADEMARK_ID)
    public ResponseEntity<Boolean> deleteTrademark(@PathVariable Long id) {
        return ResponseEntity.ok(productService.deleteTrademark(id));
    }

    @GetMapping(LOCATION)
    public ResponseEntity<List<ProductAttributeResponse>> getAllLocation() {
        return ResponseEntity.ok(productService.getAllLocation());
    }

    @PostMapping(ADMIN_LOCATION)
    public ResponseEntity<ProductAttributeResponse> saveLocation(@RequestBody ProductAttributeRequest request) {
        return ResponseEntity.ok(productService.createLocation(request));
    }

    @PutMapping(ADMIN_LOCATION_ID)
    public ResponseEntity<ProductAttributeResponse> updateLocation(@RequestBody ProductAttributeRequest request,
                                                                   @PathVariable Long id) {
        return ResponseEntity.ok(productService.updateLocation(request, id));
    }

    @DeleteMapping(ADMIN_LOCATION_ID)
    public ResponseEntity<Boolean> deleteLocation(@PathVariable Long id) {
        return ResponseEntity.ok(productService.deleteLocation(id));
    }

    @GetMapping(PROPERTIES)
    public ResponseEntity<List<ProductAttributeResponse>> getAllProperties() {
        return ResponseEntity.ok(productService.getAllProperties());
    }

    @PostMapping(ADMIN_PROPERTIES)
    public ResponseEntity<ProductAttributeResponse> saveProperties(@RequestBody ProductAttributeRequest request) {
        return ResponseEntity.ok(productService.saveProperties(request));
    }

    @PutMapping(ADMIN_PROPERTIES_ID)
    public ResponseEntity<ProductAttributeResponse> updateProperties(@RequestBody ProductAttributeRequest request,
                                                                     @PathVariable Long id) {
        return ResponseEntity.ok(productService.updateProperties(request, id));
    }

    @DeleteMapping(ADMIN_PROPERTIES_ID)
    public ResponseEntity<Boolean> deleteProperties(@PathVariable Long id) {
        return ResponseEntity.ok(productService.deleteProperties(id));
    }

    @GetMapping(UNIT)
    public ResponseEntity<List<ProductAttributeResponse>> getAllUnits() {
        return ResponseEntity.ok(productService.getAllUnits());
    }

    @PostMapping(ADMIN_UNIT)
    public ResponseEntity<ProductAttributeResponse> saveUnits(@RequestBody ProductAttributeRequest request) {
        return ResponseEntity.ok(productService.createUnit(request));
    }

    @PutMapping(ADMIN_UNIT_ID)
    public ResponseEntity<ProductAttributeResponse> updateUnits(@RequestBody ProductAttributeRequest request,
                                                                @PathVariable Long id) {
        return ResponseEntity.ok(productService.updateUnit(request, id));
    }

    @DeleteMapping(ADMIN_UNIT_ID)
    public ResponseEntity<Boolean> deleteUnits(@PathVariable Long id) {
        return ResponseEntity.ok(productService.deleteUnit(id));
    }

    @GetMapping(CATEGORY)
    public ResponseEntity<List<ProductAttributeResponse>> getAllCategory() {
        return ResponseEntity.ok(productService.getAllCategories());
    }

    @PostMapping(ADMIN_CATEGORY)
    public ResponseEntity<ProductAttributeResponse> saveCategory(@RequestBody ProductAttributeRequest request) {
        return ResponseEntity.ok(productService.createCategory(request));
    }

    @PutMapping(ADMIN_CATEGORY_ID)
    public ResponseEntity<ProductAttributeResponse> updateCategory(@RequestBody ProductAttributeRequest request,
                                                                   @PathVariable Long id) {
        return ResponseEntity.ok(productService.updateCategory(request, id));
    }

    @DeleteMapping(ADMIN_CATEGORY_ID)
    public ResponseEntity<Boolean> deleteCategory(@PathVariable Long id) {
        return ResponseEntity.ok(productService.deleteCategory(id));
    }
}

