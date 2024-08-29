package com.qlyshopphone_backend.controller.rest;

import static com.qlyshopphone_backend.constant.PathConstant.*;

import com.qlyshopphone_backend.dto.*;
import com.qlyshopphone_backend.service.ProductService;
import com.qlyshopphone_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping()
@RequiredArgsConstructor
@Validated
public class RestProductController {
    private final ProductService productService;

    @GetMapping(PRODUCT)
    public ResponseEntity<List<Map<String, Object>>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @PostMapping(ADMIN_PRODUCT)
    public ResponseEntity<String> addProduct(@Validated @ModelAttribute ProductDTO productDTO,
                                        BindingResult result) throws Exception {
        if (result.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            for (FieldError error : result.getFieldErrors()) {
                errors.append(error.getDefaultMessage()).append("\n");
            }
            return ResponseEntity.badRequest().body(errors.toString());
        }
        productDTO.setDeleteProduct(false);
        return ResponseEntity.ok(productService.saveProduct(productDTO));
    }

    @PutMapping(ADMIN_PRODUCT_PRODUCT_ID)
    public ResponseEntity<?> updateProduct(@PathVariable("productId") Long productId,
                                           @ModelAttribute ProductDTO productDTO) throws Exception {
        return ResponseEntity.ok(productService.updateProduct(productId, productDTO));
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
    public ResponseEntity<?> saveGroupProduct(@ModelAttribute GroupProductDTO groupProductDTO) {
        return ResponseEntity.ok(productService.saveGroupProduct(groupProductDTO));
    }

    @PutMapping(ADMIN_GROUP_PRODUCT_ID)
    public ResponseEntity<?> updateGroupProduct(@RequestBody GroupProductDTO groupProductDTO,
                                                @PathVariable Long id) {
        return ResponseEntity.ok(productService.updateGroupProduct(groupProductDTO, id));
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
    public ResponseEntity<?> saveTrademark(@RequestBody TrademarkDTO trademarkDTO) {
        return ResponseEntity.ok(productService.saveTrademark(trademarkDTO));
    }

    @PutMapping(ADMIN_TRADEMARK_ID)
    public ResponseEntity<?> updateTrademark(@RequestBody TrademarkDTO trademarkDTO,
                                             @PathVariable Long id) {
        return ResponseEntity.ok(productService.updateTrademark(trademarkDTO, id));
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
    public ResponseEntity<?> saveLocation(@RequestBody LocationDTO locationDTO) {
        return ResponseEntity.ok(productService.saveLocation(locationDTO));
    }

    @PutMapping(ADMIN_LOCATION_ID)
    public ResponseEntity<?> updateLocation(@RequestBody LocationDTO locationDTO,
                                            @PathVariable Long id) {
        return ResponseEntity.ok(productService.updateLocation(locationDTO, id));
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
    public ResponseEntity<?> saveProperties(@RequestBody PropertiesDTO propertiesDTO) {
        return ResponseEntity.ok(productService.saveProperties(propertiesDTO));
    }

    @PutMapping(ADMIN_PROPERTIES_ID)
    public ResponseEntity<?> updateProperties(@RequestBody PropertiesDTO propertiesDTO,
                                              @PathVariable Long id) {
        return ResponseEntity.ok(productService.updateProperties(propertiesDTO, id));
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
    public ResponseEntity<?> saveUnits(@RequestBody UnitDTO unitDTO) {
        return ResponseEntity.ok(productService.saveUnit(unitDTO));
    }

    @PutMapping(ADMIN_UNIT_ID)
    public ResponseEntity<?> updateUnits(@RequestBody UnitDTO unitDTO,
                                         @PathVariable Long id) {
        return ResponseEntity.ok(productService.updateUnit(unitDTO, id));
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
    public ResponseEntity<?> saveCategory(@RequestBody CategoryDTO categoryDTO) {
        return ResponseEntity.ok(productService.saveCategory(categoryDTO));
    }

    @PutMapping(ADMIN_CATEGORY_ID)
    public ResponseEntity<?> updateCategory(@RequestBody CategoryDTO categoryDTO,
                                            @PathVariable Long id) {
        return ResponseEntity.ok(productService.updateCategory(categoryDTO, id));
    }

    @DeleteMapping(ADMIN_CATEGORY_ID)
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        return ResponseEntity.ok(productService.deleteCategory(id));
    }
}

