package com.qlyshopphone_backend.controller.rest;

import com.qlyshopphone_backend.dto.PurchaseDTO;
import com.qlyshopphone_backend.model.Purchase;
import com.qlyshopphone_backend.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
public class RestPurchaseController {
    @Autowired
    private PurchaseService purchaseService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ACCOUNTANT')")

    // Thống kê doanh thu bán trong ngày
    @GetMapping("/today-purchases")
    public ResponseEntity<?> getTodayPurchases() {
        List<PurchaseDTO> todayPurchases = purchaseService.getTodayPurchases();
        if (todayPurchases.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        BigDecimal totalPrice = purchaseService.calculateTotalPrice(todayPurchases);

        Map<String, Object> response = new HashMap<>();
        response.put("purchases", todayPurchases);
        response.put("totalPrice", totalPrice);

        return ResponseEntity.ok(response);
    }
    // Thống kê doanh thu trong 30 ngày tính từ thời điểm hiện tại
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ACCOUNTANT')")
    @GetMapping("/last-30-days-purchases")
    public ResponseEntity<?> getLast30DaysPurchases() {
        List<PurchaseDTO> getPurchaseLast30Days = purchaseService.getPurchaseBetweenDates();
        if (getPurchaseLast30Days.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        BigDecimal totalPrice = purchaseService.calculateTotalPrice(getPurchaseLast30Days);

        Map<String, Object> response = new HashMap<>();
        response.put("purchases", getPurchaseLast30Days);
        response.put("totalPrice", totalPrice);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ACCOUNTANT')")
    @GetMapping("/daily-sales-total-price-last-30-days")
    public ResponseEntity<?> getDailySalesTotalPriceLast30Days() {
        List<BigDecimal> dailySaleTotalPrice = purchaseService.getDailySalesTotalPriceLast30Days();
        if (dailySaleTotalPrice.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(dailySaleTotalPrice);
    }



    // Phần trăm thay đổi tổng tiền bán hàng từ ngày hôm qua đến hôm nay
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ACCOUNTANT')")
    @GetMapping("/sales-percentage-change")
    public ResponseEntity<?> getSalesPercentageChange() {
        BigDecimal percentageChange = purchaseService.getPercentageChangeFromYesterdayToToday();
        return ResponseEntity.ok(percentageChange);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ACCOUNTANT')")
    @GetMapping("/sales-month-percentage-change")
    public ResponseEntity<?> getSalesMonthPercentageChange() {
        BigDecimal percentageChange = purchaseService.getPercentageChangeFromLastMonthToThisMonth();
        return ResponseEntity.ok(percentageChange);
    }
}
