package com.qlyshopphone_backend.service;

import com.qlyshopphone_backend.dto.CartDTO;
import com.qlyshopphone_backend.dto.CustomerInfoDTO;
import com.qlyshopphone_backend.dto.PurchaseDTO;
import com.qlyshopphone_backend.model.*;
import jakarta.servlet.http.HttpSession;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface CartService {
    void addToCart(HttpSession session, CartDTO cartDTO);

    String saveCart(CartDTO cartDTO);

    String deleteCart(Long cartId, Users users);

    List<CartDTO> getCart(HttpSession session);

    Cart findCartById(Long cartId);

    String createCustomerInfo(CustomerInfoDTO customerInfoDTO);

    CustomerInfoDTO updateCustomerInfo(Long customerId, CustomerInfoDTO customerInfoDTO);

    String deleteCustomerInfo(Long customerId);

    CustomerInfo findById(Long customerId);

    void createPurchase(Purchase purchase);

    List<PurchaseDTO> getPurchaseBetweenDates();

    List<PurchaseDTO> getTodayPurchases();

    List<BigDecimal> getDailySalesTotalPriceLast30Days();

    BigDecimal calculateTotalPrice(List<PurchaseDTO> purchases);

    BigDecimal getPercentageChangeFromYesterdayToToday();

    BigDecimal getPercentageChangeFromLastMonthToThisMonth();

    Optional<UserStatistics> findUserStatisticsByUserId(Long userId);

    void saveUserStatistics(UserStatistics userStatistics);
}
