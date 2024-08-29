package com.qlyshopphone_backend.service.impl;

import static com.qlyshopphone_backend.constant.ErrorMessage.*;

import com.qlyshopphone_backend.dto.CartDTO;
import com.qlyshopphone_backend.dto.CustomerInfoDTO;
import com.qlyshopphone_backend.dto.PayForCartItemsRequest;
import com.qlyshopphone_backend.dto.PurchaseDTO;
import com.qlyshopphone_backend.mapper.CartMapper;
import com.qlyshopphone_backend.mapper.CustomerInfoMapper;
import com.qlyshopphone_backend.mapper.PurchaseMapper;
import com.qlyshopphone_backend.model.*;
import com.qlyshopphone_backend.repository.*;
import com.qlyshopphone_backend.service.AuthenticationService;
import com.qlyshopphone_backend.service.CartService;
import com.qlyshopphone_backend.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;
    private static final String CART_SESSION_KEY = "cart";
    private final CustomerInfoRepository customerInfoRepository;
    private final PurchaseRepository purchaseRepository;
    private final UserStatisticsRepository userStatisticsRepository;
    private final AuthenticationService authenticationService;

    @Override
    public String addProductToCart(Long productId) {
        Users users = authenticationService.getAuthenticatedUser();
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException(PRODUCT_NOT_FOUND));

        Cart existingCartItem = users.getCart().stream()
                .filter(item -> item.getProduct().getProductId().equals(productId) && !item.isSold())
                .findFirst()
                .orElse(null);

        if (existingCartItem != null) {
            existingCartItem.setQuantity(existingCartItem.getQuantity() + 1);
        } else {
            addNewProductToCart(users, product);
        }
        userRepository.save(users);
        return PRODUCT_ADDED_SUCCESSFULLY;
    }

    @Override
    public List<CartDTO> getUserCart() {
        Users users = authenticationService.getAuthenticatedUser();
        return users.getCart()
                .stream()
                .filter(cart -> !cart.isSold() && !cart.isDeleteCart())
                .sorted(Comparator.comparing(Cart::getCartId).reversed())
                .map(CartMapper::toDto)
                .collect(Collectors.toList());
    }

    private void addNewProductToCart(Users users, Product product) {
        Cart cart = new Cart();
        cart.setProduct(product);
        cart.setQuantity(1L);
        cart.setUser(users);
        users.getCart().add(cart);
    }

    @Override
    public String deleteCart(Long cartId) {
        Users users = authenticationService.getAuthenticatedUser();
        cartRepository.deleteByCartId(cartId);
        String message = users.getUsername() + " deleted the product in the cart with code: " + cartId;
        notificationService.saveNotification(message, users);
        return SUCCESSFULLY_DELETED_THE_PRODUCT_FROM_THE_CART;
    }

    @Transactional
    @Override
    public String sellMultipleProducts(PayForCartItemsRequest request) {
        Users users = authenticationService.getAuthenticatedUser();
        BigDecimal totalPrice = BigDecimal.ZERO;
        long totalItems = 0;
        CustomerInfo customerInfo = customerInfoRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new RuntimeException(CUSTOMER_INFO_NOT_FOUND));
        List<Cart> carts = cartRepository.findAllById(request.getCartIds());
        validateCarts(carts);
        if (carts.size() != request.getCartIds().size()) {
            throw new RuntimeException(CART_NOT_FOUND);
        }
        for (Cart cart : carts) {
            totalPrice = totalPrice.add(processCart(cart, users));
            totalItems += cart.getQuantity();
        }
        savePurchase(users, customerInfo, totalPrice, totalItems);
        updateUserStatistics(users, totalPrice, totalItems);
        return CART_SOLD_SUCCESSFULLY;
    }

    @Override
    public Map<String, Object> getTodayPurchasesReport() {
        List<PurchaseDTO> purchaseDTOS = purchaseRepository.findByToday().stream()
                .map(PurchaseMapper::toDto)
                .toList();
        BigDecimal totalPrice = purchaseDTOS.stream()
                .map(PurchaseDTO::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Map<String, Object> map = new HashMap<>();
        map.put("purchases", purchaseDTOS);
        map.put("totalPrice", totalPrice);
        return map;
    }

    @Override
    public Map<String, Object> generateLast30DaysRevenueStatistics() {
        LocalDateTime startDate = LocalDateTime.now().minusDays(30);
        LocalDateTime endDate = LocalDateTime.now();

        List<Purchase> purchases = purchaseRepository.findAllPurchasesBetweenDates(startDate, endDate);

        List<PurchaseDTO> purchaseDTOS = purchases.stream()
                .map(PurchaseMapper::toDto)
                .toList();

        BigDecimal totalPrice = purchaseDTOS.stream()
                .map(PurchaseDTO::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Map<String, Object> map = new HashMap<>();
        map.put("purchases", purchaseDTOS);
        map.put("totalPrice", totalPrice);
        return map;
    }

    @Override
    public List<BigDecimal> calculateDailySalesForLast30Days() {
        LocalDate startDate = LocalDate.now().minusDays(30);
        LocalDate endDate = LocalDate.now();

        List<Purchase> purchases = purchaseRepository.findAllPurchasesBetweenDates(
                startDate.atStartOfDay(),
                endDate.atTime(23, 59, 59));

        Map<LocalDate, BigDecimal> dailySaleMap = purchases.stream()
                .collect(Collectors.groupingBy(
                        purchase -> purchase.getPurchaseDate().toLocalDate(),
                        Collectors.mapping(Purchase::getTotalPrice,
                                Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))
                ));
        return startDate.datesUntil(endDate.plusDays(1))
                .map(date -> dailySaleMap.getOrDefault(date, BigDecimal.ZERO))
                .collect(Collectors.toList());
    }

    @Override
    public List<CustomerInfoDTO> getCustomerInfo() {
        Users users = authenticationService.getAuthenticatedUser();
        return users.getCustomerInfo()
                .stream()
                .filter(customerInfo -> !customerInfo.isDeleteCustomerInfo())
                .map(CustomerInfoMapper::infoDTO)
                .collect(Collectors.toList());
    }

    @Override
    public String createCustomerInfo(CustomerInfoDTO customerInfoDTO) {
        Users users = authenticationService.getAuthenticatedUser();
        CustomerInfo customerInfo = new CustomerInfo();
        customerInfo.setCustomerName(customerInfoDTO.getCustomerName());
        customerInfo.setPhone(customerInfoDTO.getPhone());
        customerInfo.setAddress(customerInfoDTO.getAddress());
        customerInfo.setUser(users);
        users.getCustomerInfo().add(customerInfo);
        customerInfoRepository.save(customerInfo);
        return SUCCESSFULLY_CREATED_CUSTOMER_INFO;
    }

    @Override
    public String updateCustomerInfo(Long customerId, CustomerInfoDTO customerInfoDTO) {
        CustomerInfo customerInfo = customerInfoRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException(CUSTOMER_INFO_NOT_FOUND));
        customerInfo.setCustomerName(customerInfoDTO.getCustomerName());
        customerInfo.setPhone(customerInfoDTO.getPhone());
        customerInfo.setAddress(customerInfoDTO.getAddress());
        customerInfoRepository.save(customerInfo);
        return SUCCESSFULLY_UPDATED_CUSTOMER_INFO;
    }

    @Override
    public String deleteCustomerInfo(Long customerId) {
        CustomerInfo customerInfo = customerInfoRepository.findById(customerId)
                        .orElseThrow(() -> new RuntimeException(CUSTOMER_INFO_NOT_FOUND));
        customerInfoRepository.deleteById(customerInfo.getCustomerId());
        return SUCCESSFULLY_DELETED_DELIVERY_ADDRESS_INFORMATION;
    }

    private void validateCarts(List<Cart> carts) {
        for (Cart cart : carts) {
            if (cart.isSold()) {
                throw new RuntimeException(PRODUCT_ALREADY_SOLD);
            }
            if (cart.getProduct().getInventory() < cart.getQuantity()) {
                throw new RuntimeException(PRODUCT_OUT_OF_STOCK);
            }
        }
    }

    private BigDecimal processCart(Cart cart, Users users) {
        Product product = cart.getProduct();
        product.setInventory(product.getInventory() - cart.getQuantity());
        productRepository.save(product);

        cart.setSold(true);
        cart.setUser(users);
        cartRepository.save(cart);

        return product.getPrice().multiply(BigDecimal.valueOf(cart.getQuantity()));
    }

    private void savePurchase(Users users, CustomerInfo customerInfo, BigDecimal totalPrice, long totalItems) {
        Purchase purchase = new Purchase();
        purchase.setUser(users);
        purchase.setCustomerInfo(customerInfo);
        purchase.setTotalAmount(totalItems);
        purchase.setTotalPrice(totalPrice);
        purchase.setPurchaseDate(LocalDateTime.now());
        purchaseRepository.save(purchase);
    }

    private void updateUserStatistics(Users user, BigDecimal totalPrice, long totalItems) {
        UserStatistics userStatistics = userStatisticsRepository.findByUserUserId(user.getUserId())
                .orElse(new UserStatistics(user));
        BigDecimal currentTotalAmountPaid = userStatistics.getTotalAmountPaid() != null ? userStatistics.getTotalAmountPaid() : BigDecimal.ZERO;
        userStatistics.setTotalAmountPaid(currentTotalAmountPaid.add(totalPrice));
        long currentTotalItemsBought = userStatistics.getTotalItemBought() != null ? userStatistics.getTotalItemBought() : 0;
        userStatistics.setTotalItemBought(currentTotalItemsBought + totalItems);
        userStatisticsRepository.save(userStatistics);
    }

    @Override
    public BigDecimal getPercentageChangeFromYesterdayToToday() {
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);

        BigDecimal totalYesterday = getTotalSalesDate(yesterday);
        BigDecimal totalToday = getTotalSalesDate(today);

        return calculatePercentageChange(totalYesterday, totalToday);
    }

    private BigDecimal getTotalSalesDate(LocalDate date) {
        LocalDateTime startDate = date.atStartOfDay();
        LocalDateTime endDate = startDate.plusDays(1);
        List<Purchase> purchases = purchaseRepository.findAllPurchasesBetweenDates(startDate, endDate);

        return purchases.stream()
                .map(Purchase::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculatePercentageChange(BigDecimal previousTotal, BigDecimal currentTotal) {
        if (previousTotal.compareTo(BigDecimal.ZERO) == 0) {
            // Nếu tổng tiền của ngày trước bằng 0, thì phần trăm thay đổi là 100% nếu hôm nay có bán hàng, ngược lại là 0%
            return currentTotal.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO : BigDecimal.valueOf(100);
        }
        return currentTotal.subtract(previousTotal)
                .divide(previousTotal, 4, BigDecimal.ROUND_HALF_UP)
                .multiply(BigDecimal.valueOf(100));
    }

    @Override
    public BigDecimal getPercentageChangeFromLastMonthToThisMonth() {
        LocalDate today = LocalDate.now();
        LocalDate firstDayOfCurrentMonth = today.withDayOfMonth(1);
        LocalDate firstDayOfLastMonth = firstDayOfCurrentMonth.minusMonths(1);

        BigDecimal totalLastMonth = getTotalSalesMonth(firstDayOfLastMonth); // Tổng tiền của tháng trước
        BigDecimal totalThisMonth = getTotalSalesMonth(firstDayOfCurrentMonth); // Tổng tiền của tháng hiện tại

        return calculatePercentageChange(totalLastMonth, totalThisMonth);
    }

    public BigDecimal getTotalSalesMonth(LocalDate firstDayOfMonth) {
        LocalDateTime startDate = firstDayOfMonth.atStartOfDay();
        LocalDateTime endDate = startDate.plusMonths(1);

        List<Purchase> purchases = purchaseRepository.findAllPurchasesBetweenDates(startDate, endDate);
        return purchases.stream()
                .map(Purchase::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
