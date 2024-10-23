package com.qlyshopphone_backend.service.impl;

import static com.qlyshopphone_backend.constant.ErrorMessage.*;

import com.qlyshopphone_backend.dto.request.CartRequest;
import com.qlyshopphone_backend.dto.request.CustomerInfoRequest;
import com.qlyshopphone_backend.dto.request.PayForCartItemsRequest;
import com.qlyshopphone_backend.dto.request.PurchaseRequest;
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
        Users users = authenticationService.getCurrentAuthenticatedUser();
        Products products = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException(PRODUCT_NOT_FOUND));

        Carts existingCartsItem = users.getCarts().stream()
                .filter(item -> item.getProducts().getId().equals(productId) && !item.isSold())
                .findFirst()
                .orElse(null);

        if (existingCartsItem != null) {
            existingCartsItem.setQuantity(existingCartsItem.getQuantity() + 1);
        } else {
            addNewProductToCart(users, products);
        }
        userRepository.save(users);
        return PRODUCT_ADDED_SUCCESSFULLY;
    }

    @Override
    public List<CartRequest> getUserCart() {
        Users users = authenticationService.getCurrentAuthenticatedUser();
        return users.getCarts()
                .stream()
                .filter(carts -> !carts.isSold() && !carts.isDeleteCart())
                .sorted(Comparator.comparing(Carts::getId).reversed())
                .map(CartMapper::toDto)
                .collect(Collectors.toList());
    }

    private void addNewProductToCart(Users users, Products products) {
        Carts carts = new Carts();
        carts.setProducts(products);
        carts.setQuantity(1L);
        carts.setUsers(users);
        users.getCarts().add(carts);
    }

    @Override
    public String deleteCart(Long cartId) {
        Users users = authenticationService.getCurrentAuthenticatedUser();
        cartRepository.deleteByCartId(cartId);
        String message = users.getUsername() + " deleted the product in the cart with code: " + cartId;
        notificationService.saveNotification(message, users);
        return SUCCESSFULLY_DELETED_THE_PRODUCT_FROM_THE_CART;
    }

    @Transactional
    @Override
    public String sellMultipleProducts(PayForCartItemsRequest request) {
        Users users = authenticationService.getCurrentAuthenticatedUser();
        BigDecimal totalPrice = BigDecimal.ZERO;
        long totalItems = 0;
        CustomerInfo customerInfo = customerInfoRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new RuntimeException(CUSTOMER_INFO_NOT_FOUND));
        List<Carts> carts = cartRepository.findAllById(request.getCartIds());
        validateCarts(carts);
        if (carts.size() != request.getCartIds().size()) {
            throw new RuntimeException(CART_NOT_FOUND);
        }
        for (Carts cart : carts) {
            totalPrice = totalPrice.add(processCart(cart, users));
            totalItems += cart.getQuantity();
        }
        savePurchase(users, customerInfo, totalPrice, totalItems);
        updateUserStatistics(users, totalPrice, totalItems);
        return CART_SOLD_SUCCESSFULLY;
    }

    @Override
    public Map<String, Object> getTodayPurchasesReport() {
        List<PurchaseRequest> purchaseRequests = purchaseRepository.findByToday().stream()
                .map(PurchaseMapper::toDto)
                .toList();
        BigDecimal totalPrice = purchaseRequests.stream()
                .map(PurchaseRequest::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Map<String, Object> map = new HashMap<>();
        map.put("purchases", purchaseRequests);
        map.put("totalPrice", totalPrice);
        return map;
    }

    @Override
    public Map<String, Object> generateLast30DaysRevenueStatistics() {
        LocalDateTime startDate = LocalDateTime.now().minusDays(30);
        LocalDateTime endDate = LocalDateTime.now();

        List<Purchases> purchases = purchaseRepository.findAllPurchasesBetweenDates(startDate, endDate);

        List<PurchaseRequest> purchaseRequests = purchases.stream()
                .map(PurchaseMapper::toDto)
                .toList();

        BigDecimal totalPrice = purchaseRequests.stream()
                .map(PurchaseRequest::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Map<String, Object> map = new HashMap<>();
        map.put("purchases", purchaseRequests);
        map.put("totalPrice", totalPrice);
        return map;
    }

    @Override
    public List<BigDecimal> calculateDailySalesForLast30Days() {
        LocalDate startDate = LocalDate.now().minusDays(30);
        LocalDate endDate = LocalDate.now();

        List<Purchases> purchases = purchaseRepository.findAllPurchasesBetweenDates(
                startDate.atStartOfDay(),
                endDate.atTime(23, 59, 59));

//        Map<LocalDate, BigDecimal> dailySaleMap = purchases.stream()
//                .collect(Collectors.groupingBy(
//                        purchase -> purchase.getPurchaseDate().toLocalDate(),
//                        Collectors.mapping(Purchase::getTotalPrice,
//                                Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))
//                ));
//        return startDate.datesUntil(endDate.plusDays(1))
//                .map(date -> dailySaleMap.getOrDefault(date, BigDecimal.ZERO))
//                .collect(Collectors.toList());
        return null;
    }

    @Override
    public List<CustomerInfoRequest> getCustomerInfo() {
        Users users = authenticationService.getCurrentAuthenticatedUser();
        return users.getCustomerInfo()
                .stream()
                .filter(customerInfo -> !customerInfo.isDeleteCustomerInfo())
                .map(CustomerInfoMapper::infoDTO)
                .collect(Collectors.toList());
    }

    @Override
    public String createCustomerInfo(CustomerInfoRequest customerInfoRequest) {
        Users users = authenticationService.getCurrentAuthenticatedUser();
        CustomerInfo customerInfo = new CustomerInfo();
        customerInfo.setName(customerInfoRequest.getName());
        customerInfo.setPhone(customerInfoRequest.getPhone());
        customerInfo.setAddress(customerInfoRequest.getAddress());
        customerInfo.setUsers(users);
        users.getCustomerInfo().add(customerInfo);
        customerInfoRepository.save(customerInfo);
        return SUCCESSFULLY_CREATED_CUSTOMER_INFO;
    }

    @Override
    public String updateCustomerInfo(Long customerId, CustomerInfoRequest customerInfoRequest) {
        CustomerInfo customerInfo = customerInfoRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException(CUSTOMER_INFO_NOT_FOUND));
        customerInfo.setName(customerInfoRequest.getName());
        customerInfo.setPhone(customerInfoRequest.getPhone());
        customerInfo.setAddress(customerInfoRequest.getAddress());
        customerInfoRepository.save(customerInfo);
        return SUCCESSFULLY_UPDATED_CUSTOMER_INFO;
    }

    @Override
    public String deleteCustomerInfo(Long customerId) {
        CustomerInfo customerInfo = customerInfoRepository.findById(customerId)
                        .orElseThrow(() -> new RuntimeException(CUSTOMER_INFO_NOT_FOUND));
        customerInfoRepository.deleteById(customerInfo.getId());
        return SUCCESSFULLY_DELETED_DELIVERY_ADDRESS_INFORMATION;
    }

    private void validateCarts(List<Carts> carts) {
        for (Carts cart : carts) {
            if (cart.isSold()) {
                throw new RuntimeException(PRODUCT_ALREADY_SOLD);
            }
//            if (cart.getProducts().getInventory() < cart.getQuantity()) {
//                throw new RuntimeException(PRODUCT_OUT_OF_STOCK);
//            }
        }
    }

    private BigDecimal processCart(Carts carts, Users users) {
        Products products = carts.getProducts();
//        products.setInventory(products.getInventory() - carts.getQuantity());
        productRepository.save(products);

        carts.setSold(true);
        carts.setUsers(users);
        cartRepository.save(carts);

//        return product.getPrice().multiply(BigDecimal.valueOf(cart.getQuantity()));
        return BigDecimal.ZERO;
    }

    private void savePurchase(Users users, CustomerInfo customerInfo, BigDecimal totalPrice, long totalItems) {
        Purchases purchases = new Purchases();
        purchases.setUsers(users);
        purchases.setCustomerInfo(customerInfo);
        purchases.setTotalAmount(totalItems);
//        purchase.setTotalPrice(totalPrice);
        purchases.setPurchaseDate(LocalDateTime.now());
        purchaseRepository.save(purchases);
    }

    private void updateUserStatistics(Users users, BigDecimal totalPrice, long totalItems) {
//        UserStatistics userStatistics = userStatisticsRepository.findByUserUserId(user.getUserId())
//                .orElse(new UserStatistics(user));
////        BigDecimal currentTotalAmountPaid = userStatistics.getTotalAmountPaid() != null ? userStatistics.getTotalAmountPaid() : BigDecimal.ZERO;
////        userStatistics.setTotalAmountPaid(currentTotalAmountPaid.add(totalPrice));
//        long currentTotalItemsBought = userStatistics.getTotalItemBought() != null ? userStatistics.getTotalItemBought() : 0;
//        userStatistics.setTotalItemBought(currentTotalItemsBought + totalItems);
//        userStatisticsRepository.save(userStatistics);
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
//        LocalDateTime startDate = date.atStartOfDay();
//        LocalDateTime endDate = startDate.plusDays(1);
//        List<Purchase> purchases = purchaseRepository.findAllPurchasesBetweenDates(startDate, endDate);
//
//        return purchases.stream()
//                .map(Purchase::getTotalPrice)
//                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return null;
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
//        LocalDateTime startDate = firstDayOfMonth.atStartOfDay();
//        LocalDateTime endDate = startDate.plusMonths(1);
//
//        List<Purchase> purchases = purchaseRepository.findAllPurchasesBetweenDates(startDate, endDate);
//        return purchases.stream()
//                .map(Purchase::getTotalPrice)
//                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return null;
    }
}
