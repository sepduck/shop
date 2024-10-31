package com.qlyshopphone_backend.controller.admin;

import static com.qlyshopphone_backend.constant.PathConstant.*;

import com.qlyshopphone_backend.dto.request.*;
import com.qlyshopphone_backend.dto.response.ImagesResponse;
import com.qlyshopphone_backend.dto.response.ProductAttributeResponse;
import com.qlyshopphone_backend.dto.response.ProductResponse;
import com.qlyshopphone_backend.repository.projection.ProductProjection;
import com.qlyshopphone_backend.service.ProductService;
import com.qlyshopphone_backend.service.impl.FirebaseStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(API_V1_ADMIN)
@RequiredArgsConstructor
@Validated
public class ProductAdminController {
    private final ProductService productService;
    private final FirebaseStorageService firebaseStorageService;

    @PostMapping(UPLOAD)
    public ResponseEntity<List<ImagesResponse>> uploadImages(@RequestParam("files") MultipartFile[] files) {
        try {
            return ResponseEntity.ok(firebaseStorageService.uploadFiles(files));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping(PRODUCT)
    public ResponseEntity<Boolean> createProduct(@RequestBody ProductRequest request) {
        return ResponseEntity.ok(productService.createProduct(request));
    }

    @PutMapping(PRODUCT_ID)
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable("id") Long id,
                                                         @RequestBody ProductRequest productRequest) {
        return ResponseEntity.ok(productService.updateProduct(id, productRequest));
    }

    @DeleteMapping(PRODUCT_ID)
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productService.deleteProduct(id));
    }

    @PostMapping(GROUP_PRODUCT)
    public ResponseEntity<ProductAttributeResponse> createGroupProduct(@RequestBody ProductAttributeRequest request) {
        return ResponseEntity.ok(productService.createGroupProduct(request));
    }

    @PutMapping(GROUP_PRODUCT_ID)
    public ResponseEntity<ProductAttributeResponse> updateGroupProduct(@RequestBody ProductAttributeRequest request,
                                                                       @PathVariable Long id) {
        return ResponseEntity.ok(productService.updateGroupProduct(request, id));
    }

    @DeleteMapping(GROUP_PRODUCT_ID)
    public ResponseEntity<Boolean> deleteGroupProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productService.deleteGroupProduct(id));
    }

    @PostMapping(TRADEMARK)
    public ResponseEntity<ProductAttributeResponse> saveTrademark(@RequestBody ProductAttributeRequest request) {
        return ResponseEntity.ok(productService.createTrademark(request));
    }

    @PutMapping(TRADEMARK_ID)
    public ResponseEntity<ProductAttributeResponse> updateTrademark(@RequestBody ProductAttributeRequest request,
                                                                    @PathVariable Long id) {
        return ResponseEntity.ok(productService.updateTrademark(request, id));
    }

    @DeleteMapping(TRADEMARK_ID)
    public ResponseEntity<Boolean> deleteTrademark(@PathVariable Long id) {
        return ResponseEntity.ok(productService.deleteTrademark(id));
    }

    @PostMapping(UNIT)
    public ResponseEntity<ProductAttributeResponse> saveUnits(@RequestBody ProductAttributeRequest request) {
        return ResponseEntity.ok(productService.createUnit(request));
    }

    @PutMapping(UNIT_ID)
    public ResponseEntity<ProductAttributeResponse> updateUnits(@RequestBody ProductAttributeRequest request,
                                                                @PathVariable Long id) {
        return ResponseEntity.ok(productService.updateUnit(request, id));
    }

    @DeleteMapping(UNIT_ID)
    public ResponseEntity<Boolean> deleteUnits(@PathVariable Long id) {
        return ResponseEntity.ok(productService.deleteUnit(id));
    }

    @PostMapping(CATEGORY)
    public ResponseEntity<ProductAttributeResponse> createCategory(@RequestBody ProductAttributeRequest request) {
        return ResponseEntity.ok(productService.createCategory(request));
    }

    @PutMapping(CATEGORY_ID)
    public ResponseEntity<ProductAttributeResponse> updateCategory(@RequestBody ProductAttributeRequest request,
                                                                   @PathVariable Long id) {
        return ResponseEntity.ok(productService.updateCategory(request, id));
    }

    @DeleteMapping(CATEGORY_ID)
    public ResponseEntity<Boolean> deleteCategory(@PathVariable Long id) {
        return ResponseEntity.ok(productService.deleteCategory(id));
    }

    @PostMapping(LOCATION)
    public ResponseEntity<ProductAttributeResponse> saveLocation(@RequestBody ProductAttributeRequest request) {
        return ResponseEntity.ok(productService.createLocation(request));
    }

    @PutMapping(LOCATION_ID)
    public ResponseEntity<ProductAttributeResponse> updateLocation(@RequestBody ProductAttributeRequest request,
                                                                   @PathVariable Long id) {
        return ResponseEntity.ok(productService.updateLocation(request, id));
    }

    @DeleteMapping(LOCATION_ID)
    public ResponseEntity<Boolean> deleteLocation(@PathVariable Long id) {
        return ResponseEntity.ok(productService.deleteLocation(id));
    }

    @GetMapping(SEARCH_NAME)
    public ResponseEntity<List<ProductProjection>> searchProductByName(@PathVariable String name) {
        return ResponseEntity.ok(productService.searchProductByName(name));
    }

    @GetMapping(SEARCH_ID)
    public ResponseEntity<List<ProductProjection>> searchProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.searchProductById(id));
    }

    @GetMapping(SEARCH_GROUP_PRODUCT_ID)
    public ResponseEntity<List<ProductProjection>> searchProductByGroupProductId(@PathVariable Long id) {
        return ResponseEntity.ok(productService.searchProductByGroupProductId(id));
    }

    @GetMapping(SEARCH_TRADEMARK_ID)
    public ResponseEntity<List<ProductProjection>> searchProductsByTrademarkId(@PathVariable Long id) {
        return ResponseEntity.ok(productService.searchProductsByTrademarkId(id));
    }

    @GetMapping(SEARCH_STATUS)
    public ResponseEntity<List<ProductProjection>> searchProductsByStatus(@PathVariable String status) {
        return ResponseEntity.ok(productService.searchProductsByStatus(status));
    }

    @GetMapping(PRODUCT_ID)
    public ResponseEntity<List<ProductProjection>> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping(SEARCH_CATEGORY)
    public ResponseEntity<List<ProductProjection>> searchProductsByCategoryId(@PathVariable Long id) {
        return ResponseEntity.ok(productService.searchProductsByCategoryId(id));
    }

}

