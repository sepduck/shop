package com.qlyshopphone_backend.controller.user;

import com.qlyshopphone_backend.dto.response.ProductAttributeResponse;
import com.qlyshopphone_backend.repository.projection.ProductAttributeProjection;
import com.qlyshopphone_backend.repository.projection.ProductProjection;
import com.qlyshopphone_backend.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.qlyshopphone_backend.constant.PathConstant.*;

@RestController
@RequestMapping(API_V1_PRODUCT)
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping()
    public ResponseEntity<List<ProductProjection>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping(GROUP_PRODUCT)
    public ResponseEntity<List<ProductAttributeResponse>> getAllGroupProduct() {
        return ResponseEntity.ok(productService.getAllGroupProducts());
    }

    @GetMapping(TRADEMARK)
    public ResponseEntity<List<ProductAttributeResponse>> getAllTrademark() {
        return ResponseEntity.ok(productService.getAllTrademarks());
    }

    @GetMapping(UNIT)
    public ResponseEntity<List<ProductAttributeResponse>> getAllUnits() {
        return ResponseEntity.ok(productService.getAllUnits());
    }

    @GetMapping(CATEGORY)
    public ResponseEntity<List<ProductAttributeProjection>> getAllCategory() {
        return ResponseEntity.ok(productService.getAllCategories());
    }

//    @GetMapping(LOCATION)
//    public ResponseEntity<List<ProductAttributeResponse>> getAllLocation() {
//        return ResponseEntity.ok(productService.getAllLocation());
//    }
//
//    @GetMapping(PROPERTIES)
//    public ResponseEntity<List<ProductAttributeResponse>> getAllProperties() {
//        return ResponseEntity.ok(productService.getAllProperties());
//    }
//
//    @GetMapping(SEARCH_NAME)
//    public ResponseEntity<?> searchAllByProductName(@PathVariable("name") String name) {
//        return ResponseEntity.ok(productService.searchAllByProductName(name));
//    }
//
//    @GetMapping(SEARCH_ID)
//    public ResponseEntity<?> searchAllByProductId(@PathVariable("id") Long id) {
//        return ResponseEntity.ok(productService.searchAllByProductId(id));
//    }
//
//    @GetMapping(SEARCH_GROUP_PRODUCT_ID)
//    public ResponseEntity<?> searchGroupProductId(@PathVariable("id") Long id) {
//        return ResponseEntity.ok(productService.searchGroupProductId(id));
//    }
//
//    @GetMapping(SEARCH_LOCATION_ID)
//    public ResponseEntity<?> searchByLocationId(@PathVariable("id") Long id) {
//        return ResponseEntity.ok(productService.searchByLocationId(id));
//    }
}
