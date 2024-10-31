package com.qlyshopphone_backend.controller.admin;

import com.qlyshopphone_backend.service.RevenueStatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static com.qlyshopphone_backend.constant.PathConstant.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class RevenueStatisticsController {
    private final RevenueStatisticsService revenueStatisticsService;

    @GetMapping(TODAY_PURCHASES)
    public ResponseEntity<Map<String, Object>> getTodayPurchases() {
        return ResponseEntity.ok(revenueStatisticsService.getReceiveAPurchaseReportToday());
    }

    @GetMapping(ADMIN_LAST_30_DAYS_PURCHASES)
    public ResponseEntity<Map<String, Object>> getLast30DaysPurchases() {
        return ResponseEntity.ok(revenueStatisticsService.generateLast30DaysRevenueStatistics());
    }

    @GetMapping(ADMIN_DAILY_SALES_TOTAL_PRICE_LAST_30_DAYS)
    public ResponseEntity<List<BigDecimal>> getDailySalesTotalPriceLast30Days() {
        return ResponseEntity.ok(revenueStatisticsService.calculateDailySalesForLast30Days());
    }

    @GetMapping(ADMIN_SALES_PERCENTAGE_CHANGE)
    public ResponseEntity<BigDecimal> getSalesPercentageChange() {
        return ResponseEntity.ok(revenueStatisticsService.getPercentageChangeFromYesterdayToToday());
    }

    @GetMapping(ADMIN_SALES_MONTH_PERCENTAGE_CHANGE)
    public ResponseEntity<BigDecimal> getSalesMonthPercentageChange() {
        return ResponseEntity.ok(revenueStatisticsService.getPercentageChangeFromLastMonthToThisMonth());
    }
}
