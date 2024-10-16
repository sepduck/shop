package com.qlyshopphone_backend.service;

import com.qlyshopphone_backend.dto.request.CartRequest;
import com.qlyshopphone_backend.dto.request.CustomerInfoRequest;
import com.qlyshopphone_backend.dto.request.PayForCartItemsRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface CartService {
    String addProductToCart(Long productId);

    List<CartRequest> getUserCart();

    String deleteCart(Long cartId);

    String sellMultipleProducts(PayForCartItemsRequest request);

    Map<String, Object> getTodayPurchasesReport();

    Map<String, Object> generateLast30DaysRevenueStatistics();

    List<BigDecimal> calculateDailySalesForLast30Days();

    List<CustomerInfoRequest> getCustomerInfo();

    String createCustomerInfo(CustomerInfoRequest customerInfoRequest);

    String updateCustomerInfo(Long customerId, CustomerInfoRequest customerInfoRequest);

    String deleteCustomerInfo(Long customerId);

    BigDecimal getPercentageChangeFromYesterdayToToday();

    BigDecimal getPercentageChangeFromLastMonthToThisMonth();
}
