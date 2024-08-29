package com.qlyshopphone_backend.controller.rest;

import static com.qlyshopphone_backend.constant.PathConstant.*;

import static com.qlyshopphone_backend.constant.ErrorMessage.*;

import com.qlyshopphone_backend.dto.CartDTO;
import com.qlyshopphone_backend.dto.CustomerInfoDTO;
import com.qlyshopphone_backend.dto.PayForCartItemsRequest;
import com.qlyshopphone_backend.dto.PurchaseDTO;
import com.qlyshopphone_backend.mapper.CartMapper;
import com.qlyshopphone_backend.mapper.CustomerInfoMapper;
import com.qlyshopphone_backend.model.*;
import com.qlyshopphone_backend.repository.CartRepository;
import com.qlyshopphone_backend.repository.CustomerInfoRepository;
import com.qlyshopphone_backend.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping(API_V1)
@RequiredArgsConstructor
public class RestCartController {
    private final CartService cartService;

    @PostMapping(CART_ADD_PRODUCT_ID)
    public ResponseEntity<String> addCartItem(@PathVariable("productId") Long productId) {
        return ResponseEntity.ok(cartService.addProductToCart(productId));
    }

    @GetMapping(LIST_CART)
    public ResponseEntity<?> viewCart() {
        return ResponseEntity.ok(cartService.getUserCart());
    }

    @DeleteMapping(DELETE_CART_CART_ID)
    public ResponseEntity<?> deleteCartItem(@PathVariable("cartId") Long cartId) {
        return ResponseEntity.ok(cartService.deleteCart(cartId));
    }

    @PostMapping(CART_SELLS)
    public ResponseEntity<?> sellCart(@RequestBody PayForCartItemsRequest request) {
        try {
            String responseMessage = cartService.sellMultipleProducts(request);
            return ResponseEntity.ok().body(responseMessage);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping(ADMIN_TODAY_PURCHASES)
    public ResponseEntity<?> getTodayPurchases() {
        Map<String, Object> response = cartService.getTodayPurchasesReport();
        if (((List<?>) response.get("purchases")).isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping(ADMIN_LAST_30_DAYS_PURCHASES)
    public ResponseEntity<?> getLast30DaysPurchases() {
        Map<String, Object> response = cartService.generateLast30DaysRevenueStatistics();
        if (((List<?>) response.get("purchases")).isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping(ADMIN_DAILY_SALES_TOTAL_PRICE_LAST_30_DAYS)
    public ResponseEntity<?> getDailySalesTotalPriceLast30Days() {
        List<BigDecimal> dailySaleTotalPrice = cartService.calculateDailySalesForLast30Days();
        if (dailySaleTotalPrice.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(dailySaleTotalPrice);
    }

    @GetMapping(ADMIN_SALES_PERCENTAGE_CHANGE)
    public ResponseEntity<?> getSalesPercentageChange() {
        BigDecimal percentageChange = cartService.getPercentageChangeFromYesterdayToToday();
        return ResponseEntity.ok(percentageChange);
    }

    @GetMapping(ADMIN_SALES_MONTH_PERCENTAGE_CHANGE)
    public ResponseEntity<?> getSalesMonthPercentageChange() {
        BigDecimal percentageChange = cartService.getPercentageChangeFromLastMonthToThisMonth();
        return ResponseEntity.ok(percentageChange);
    }

    @GetMapping(CUSTOMER_INFO)
    public ResponseEntity<List<CustomerInfoDTO>> viewCustomerInfo() {
        return ResponseEntity.ok(cartService.getCustomerInfo());
    }

    @PostMapping(CUSTOMER_INFO)
    public ResponseEntity<?> createCustomerInfo(@RequestBody CustomerInfoDTO customerInfoDTO) {
        return ResponseEntity.ok(cartService.createCustomerInfo(customerInfoDTO));
    }

    @PutMapping(CUSTOMER_INFO_ID)
    public ResponseEntity<?> updateCustomerInfo(@PathVariable("customerInfoId") Long customerInfoId, @RequestBody CustomerInfoDTO customerInfoDTO) {
        return ResponseEntity.ok(cartService.updateCustomerInfo(customerInfoId, customerInfoDTO));
    }

    @DeleteMapping(CUSTOMER_INFO_ID)
    public ResponseEntity<?> deleteCustomerInfo(@PathVariable("customerInfoId") Long customerInfoId) {
        return ResponseEntity.ok(cartService.deleteCustomerInfo(customerInfoId));
    }
}

