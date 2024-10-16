package com.qlyshopphone_backend.controller.rest;

import static com.qlyshopphone_backend.constant.PathConstant.*;

import com.qlyshopphone_backend.dto.request.*;
import com.qlyshopphone_backend.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(API_V1)
@RequiredArgsConstructor
@Validated
public class ProductController {
    private final ProductService productService;

    @GetMapping(PRODUCT)
    public ResponseEntity<List<Map<String, Object>>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @PostMapping(ADMIN_PRODUCT)
    public ResponseEntity<String> addProduct(@Validated @ModelAttribute ProductRequest productRequest,
                                        BindingResult result) throws Exception {
        if (result.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            for (FieldError error : result.getFieldErrors()) {
                errors.append(error.getDefaultMessage()).append("\n");
            }
            return ResponseEntity.badRequest().body(errors.toString());
        }
        productRequest.setDeleteProduct(false);
        return ResponseEntity.ok(productService.saveProduct(productRequest));
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
    public ResponseEntity<?> getAllGroupProduct() {
        return ResponseEntity.ok(productService.getAllGroupProduct());
    }

    @PostMapping(ADMIN_GROUP_PRODUCT)
    public ResponseEntity<?> saveGroupProduct(@ModelAttribute GroupProductRequest groupProductRequest) {
        return ResponseEntity.ok(productService.saveGroupProduct(groupProductRequest));
    }

    @PutMapping(ADMIN_GROUP_PRODUCT_ID)
    public ResponseEntity<?> updateGroupProduct(@RequestBody GroupProductRequest groupProductRequest,
                                                @PathVariable Long id) {
        return ResponseEntity.ok(productService.updateGroupProduct(groupProductRequest, id));
    }

    @DeleteMapping(ADMIN_GROUP_PRODUCT_ID)
    public ResponseEntity<?> deleteGroupProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productService.deleteGroupProduct(id));
    }

    @GetMapping(TRADEMARK)
    public ResponseEntity<?> getAllTrademark() {
        return ResponseEntity.ok(productService.getAllTrademarks());
    }

    @PostMapping(ADMIN_TRADEMARK)
    public ResponseEntity<?> saveTrademark(@RequestBody TrademarkRequest trademarkRequest) {
        return ResponseEntity.ok(productService.saveTrademark(trademarkRequest));
    }

    @PutMapping(ADMIN_TRADEMARK_ID)
    public ResponseEntity<?> updateTrademark(@RequestBody TrademarkRequest trademarkRequest,
                                             @PathVariable Long id) {
        return ResponseEntity.ok(productService.updateTrademark(trademarkRequest, id));
    }

    @DeleteMapping(ADMIN_TRADEMARK_ID)
    public ResponseEntity<?> deleteTrademark(@PathVariable Long id) {
        return ResponseEntity.ok(productService.deleteTrademark(id));
    }

    @GetMapping(LOCATION)
    public ResponseEntity<?> getAllLocation() {
        return ResponseEntity.ok(productService.getAllLocation());
    }

    @PostMapping(ADMIN_LOCATION)
    public ResponseEntity<?> saveLocation(@RequestBody LocationRequest locationRequest) {
        return ResponseEntity.ok(productService.saveLocation(locationRequest));
    }

    @PutMapping(ADMIN_LOCATION_ID)
    public ResponseEntity<?> updateLocation(@RequestBody LocationRequest locationRequest,
                                            @PathVariable Long id) {
        return ResponseEntity.ok(productService.updateLocation(locationRequest, id));
    }

    @DeleteMapping(ADMIN_LOCATION_ID)
    public ResponseEntity<?> deleteLocation(@PathVariable Long id) {
        return ResponseEntity.ok(productService.deleteLocation(id));
    }

    @GetMapping(PROPERTIES)
    public ResponseEntity<?> getAllProperties() {
        return ResponseEntity.ok(productService.getAllProperties());
    }

    @PostMapping(ADMIN_PROPERTIES)
    public ResponseEntity<?> saveProperties(@RequestBody PropertiesRequest propertiesRequest) {
        return ResponseEntity.ok(productService.saveProperties(propertiesRequest));
    }

    @PutMapping(ADMIN_PROPERTIES_ID)
    public ResponseEntity<?> updateProperties(@RequestBody PropertiesRequest propertiesRequest,
                                              @PathVariable Long id) {
        return ResponseEntity.ok(productService.updateProperties(propertiesRequest, id));
    }

    @DeleteMapping(ADMIN_PROPERTIES_ID)
    public ResponseEntity<?> deleteProperties(@PathVariable Long id) {
        return ResponseEntity.ok(productService.deleteProperties(id));
    }

    @GetMapping(UNIT)
    public ResponseEntity<?> getAllUnits() {
        return ResponseEntity.ok(productService.getAllUnits());
    }

    @PostMapping(ADMIN_UNIT)
    public ResponseEntity<?> saveUnits(@RequestBody UnitRequest unitRequest) {
        return ResponseEntity.ok(productService.saveUnit(unitRequest));
    }

    @PutMapping(ADMIN_UNIT_ID)
    public ResponseEntity<?> updateUnits(@RequestBody UnitRequest unitRequest,
                                         @PathVariable Long id) {
        return ResponseEntity.ok(productService.updateUnit(unitRequest, id));
    }

    @DeleteMapping(ADMIN_UNIT_ID)
    public ResponseEntity<?> deleteUnits(@PathVariable Long id) {
        return ResponseEntity.ok(productService.deleteUnit(id));
    }

    @GetMapping(CATEGORY)
    public ResponseEntity<?> getAllCategory() {
        return ResponseEntity.ok(productService.getAllCategory());
    }

    @PostMapping(ADMIN_CATEGORY)
    public ResponseEntity<?> saveCategory(@RequestBody CategoryRequest categoryRequest) {
        return ResponseEntity.ok(productService.saveCategory(categoryRequest));
    }

    @PutMapping(ADMIN_CATEGORY_ID)
    public ResponseEntity<?> updateCategory(@RequestBody CategoryRequest categoryRequest,
                                            @PathVariable Long id) {
        return ResponseEntity.ok(productService.updateCategory(categoryRequest, id));
    }

    @DeleteMapping(ADMIN_CATEGORY_ID)
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        return ResponseEntity.ok(productService.deleteCategory(id));
    }
}

