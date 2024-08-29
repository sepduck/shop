package com.qlyshopphone_backend.service;

import com.qlyshopphone_backend.dto.*;
import jakarta.servlet.http.HttpSession;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface CartService {
    String addProductToCart(Long productId);

    List<CartDTO> getUserCart();

    String deleteCart(Long cartId);

    String sellMultipleProducts(PayForCartItemsRequest request);

    Map<String, Object> getTodayPurchasesReport();

    Map<String, Object> generateLast30DaysRevenueStatistics();

    List<BigDecimal> calculateDailySalesForLast30Days();

    List<CustomerInfoDTO> getCustomerInfo();

    String createCustomerInfo(CustomerInfoDTO customerInfoDTO);

    String updateCustomerInfo(Long customerId, CustomerInfoDTO customerInfoDTO);

    String deleteCustomerInfo(Long customerId);

    BigDecimal getPercentageChangeFromYesterdayToToday();

    BigDecimal getPercentageChangeFromLastMonthToThisMonth();
}
