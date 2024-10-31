package com.qlyshopphone_backend.controller.admin;

import static com.qlyshopphone_backend.constant.PathConstant.*;

import com.qlyshopphone_backend.dto.request.ProductVariantRequest;
import com.qlyshopphone_backend.service.ProductVariantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(API_V1_PRODUCT_VARIANT)
@RequiredArgsConstructor
public class ProductVariantController {
    private final ProductVariantService productVariantService;

    @PostMapping()
    public ResponseEntity<Boolean> classifyProductVariant(@RequestBody ProductVariantRequest request){
        return ResponseEntity.ok(productVariantService.classifyProductVariant(request));
    }
}
