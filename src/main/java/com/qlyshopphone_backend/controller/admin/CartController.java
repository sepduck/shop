package com.qlyshopphone_backend.controller.admin;

import static com.qlyshopphone_backend.constant.PathConstant.*;

import com.qlyshopphone_backend.dto.request.AddToCartRequest;
import com.qlyshopphone_backend.dto.request.CartIdsRequest;
import com.qlyshopphone_backend.repository.projection.CartProjection;
import com.qlyshopphone_backend.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(API_V1_CART)
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping()
    public ResponseEntity<Boolean> addCartItem(@RequestBody AddToCartRequest request) {
        return ResponseEntity.ok(cartService.addProductToCart(request));
    }

    @GetMapping()
    public ResponseEntity<List<CartProjection>> getUserActiveCarts() {
        return ResponseEntity.ok(cartService.getUserActiveCarts());
    }

    @DeleteMapping(DELETE_CART)
    public ResponseEntity<Boolean> removeProductFromCart(@RequestBody CartIdsRequest request) {
        return ResponseEntity.ok(cartService.removeProductVariantFromCart(request));
    }


}

