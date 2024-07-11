package com.qlyshopphone_backend.service;

import com.qlyshopphone_backend.dto.PurchaseDTO;
import com.qlyshopphone_backend.model.Purchase;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface PurchaseService {
    void createPurchase(Purchase purchase);

    List<PurchaseDTO> getPurchaseBetweenDates();

    List<PurchaseDTO> getTodayPurchases();

    List<BigDecimal> getDailySalesTotalPriceLast30Days();

    BigDecimal calculateTotalPrice(List<PurchaseDTO> purchases);

    BigDecimal getPercentageChangeFromYesterdayToToday();

    BigDecimal getPercentageChangeFromLastMonthToThisMonth();
}
