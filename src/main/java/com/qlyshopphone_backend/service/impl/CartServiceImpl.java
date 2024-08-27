package com.qlyshopphone_backend.service.impl;

import com.qlyshopphone_backend.dto.CartDTO;
import com.qlyshopphone_backend.dto.CustomerInfoDTO;
import com.qlyshopphone_backend.dto.PurchaseDTO;
import com.qlyshopphone_backend.mapper.CartMapper;
import com.qlyshopphone_backend.mapper.CustomerInfoMapper;
import com.qlyshopphone_backend.mapper.PurchaseMapper;
import com.qlyshopphone_backend.model.*;
import com.qlyshopphone_backend.repository.*;
import com.qlyshopphone_backend.service.CartService;
import com.qlyshopphone_backend.service.NotificationService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    @Override
    public void addToCart(HttpSession session, CartDTO cartDTO) {
        List<CartDTO> cart = getCart(session);
        cart.add(cartDTO);
        session.setAttribute(CART_SESSION_KEY, cart);
    }

    @Override
    public String saveCart(CartDTO cartDTO) {
        Optional<Product> productOptional = productRepository.findById(cartDTO.getProductId());
        Optional<Users> usersOptional = userRepository.findById(cartDTO.getUserId());
        if (!productOptional.isPresent() || !usersOptional.isPresent()) { // Sửa || thành &&
            return "Invalid product or user information";
        }
        Cart cart = CartMapper.toEntity(cartDTO, productOptional.get(), usersOptional.get());
        ResponseEntity.ok(cartRepository.save(cart));
        return "Successfully added to cart";
    }

    @Override
    public String deleteCart(Long cartId, Users users) {
            cartRepository.deleteByCartId(cartId);
            String message = users.getUsername() + " deleted the product in the cart with code: " + cartId;
            notificationService.saveNotification(message, users);
            return "Successfully deleted the product from the cart";
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<CartDTO> getCart(HttpSession session) {
        List<CartDTO> cart = (List<CartDTO>) session.getAttribute(CART_SESSION_KEY);
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute(CART_SESSION_KEY, cart);
        }
        return cart;
    }

    @Override
    public Cart findCartById(Long cartId) {
        return cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));
    }

    @Override
    public String createCustomerInfo(CustomerInfoDTO customerInfoDTO) {
        Users users = userRepository.findById(customerInfoDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        CustomerInfo customerInfo = CustomerInfoMapper.infoEntity(customerInfoDTO, users);

        customerInfo.setUser(users);
        users.getCustomerInfo().add(customerInfo);
        customerInfoRepository.save(customerInfo);
        return "Successfully created customer info";
    }

    @Override
    public CustomerInfoDTO updateCustomerInfo(Long customerId, CustomerInfoDTO customerInfoDTO) {
        CustomerInfo customerInfo = customerInfoRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer Info not found"));
        customerInfo.setCustomerName(customerInfoDTO.getCustomerName());
        customerInfo.setPhone(customerInfoDTO.getPhone());
        customerInfo.setAddress(customerInfoDTO.getAddress());
        customerInfo.setDeleteCustomerInfo(customerInfoDTO.isDeleteCustomerInfo());
        CustomerInfo updatedCustomerInfo = customerInfoRepository.save(customerInfo);
        return CustomerInfoMapper.infoDTO(updatedCustomerInfo);
    }

    @Override
    public String deleteCustomerInfo(Long customerId) {
        customerInfoRepository.deleteById(customerId);
        return "Successfully deleted delivery address information";
    }

    @Override
    public CustomerInfo findById(Long customerInfoId) {
        return customerInfoRepository.findById(customerInfoId)
                .orElseThrow(() -> new RuntimeException("Customer Info not found"));
    }

    @Override
    public void createPurchase(Purchase purchase) {
        purchaseRepository.save(purchase);
    }

    @Override
    public List<PurchaseDTO> getPurchaseBetweenDates() {
        LocalDateTime startDate = LocalDateTime.now().minusDays(30);
        LocalDateTime endDate = LocalDateTime.now();
        List<Purchase> purchases = purchaseRepository.findAllPurchasesBetweenDates(startDate, endDate);
        return purchases.stream()
                .map(PurchaseMapper:: toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PurchaseDTO> getTodayPurchases() {
        List<Purchase> purchases = purchaseRepository.findByToday();
        return purchases.stream()
                .map(PurchaseMapper :: toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<BigDecimal> getDailySalesTotalPriceLast30Days() {
        LocalDateTime startDate = LocalDateTime.now().withDayOfMonth(1).toLocalDate().atStartOfDay();
        LocalDateTime endDate = LocalDateTime.now();
        List<Purchase> purchases = purchaseRepository.findAllPurchasesBetweenDates(startDate, endDate);

        // Tạo một bản đồ để lưu tổng giá trị bán hàng cho mỗi ngày
        Map<LocalDate, BigDecimal> dailySaleMap = new HashMap<>();
        for (Purchase purchase : purchases) {
            LocalDate date = purchase.getPurchaseDate().toLocalDate();
            BigDecimal totalPrice = purchase.getTotalPrice();
            dailySaleMap.put(date, dailySaleMap.getOrDefault(date, BigDecimal.ZERO).add(totalPrice));
        }

        // Tạo danh sách kết quả từ đầu tháng đến ngày hôm nay
        long daysBetween = ChronoUnit.DAYS.between(startDate.toLocalDate(), endDate.toLocalDate());
        return IntStream.rangeClosed(0, (int) daysBetween)
                .mapToObj(i -> startDate.toLocalDate().plusDays(i))
                .map(date -> dailySaleMap.getOrDefault(date, BigDecimal.ZERO))
                .collect(Collectors.toList());
    }

    @Override
    public BigDecimal calculateTotalPrice(List<PurchaseDTO> purchases) {
        return purchases.stream()
                .map(PurchaseDTO::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // Phương thức lấy phần trăm thay đổi tổng tiền bán hàng từ ngày hôm qua đến hôm nay
    @Override
    public BigDecimal getPercentageChangeFromYesterdayToToday() {
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);

        BigDecimal totalYesterday = getTotalSalesDate(yesterday); // Tổng tiền của ngày hôm qua
        BigDecimal totalToday = getTotalSalesDate(today);  // Tổng tiền của ngày hôm nay

        return calculatePercentageChange(totalYesterday, totalToday);
    }
    // Phương thức lấy tổng tiền bán hàng trong một ngày cụ thể
    public BigDecimal getTotalSalesDate(LocalDate date) {
        LocalDateTime startDate = date.atStartOfDay();
        LocalDateTime endDate = startDate.plusDays(1);
        List<Purchase> purchases = purchaseRepository.findAllPurchasesBetweenDates(startDate, endDate);

        // Tính tổng tiền bằng cách cộng tất cả giá trị totalPrice của Purchase
        return purchases.stream()
                .map(Purchase::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // Phương thức tính phần trăm thay đổi giữa hai giá trị totalPrice
    public BigDecimal calculatePercentageChange(BigDecimal previousTotal, BigDecimal currentTotal) {
        if (previousTotal.compareTo(BigDecimal.ZERO) == 0) {
            // Nếu tổng tiền của ngày trước bằng 0, thì phần trăm thay đổi là 100% nếu hôm nay có bán hàng, ngược lại là 0%
            return currentTotal.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO : BigDecimal.valueOf(100);
        }
        // Tính phần trăm thay đổi
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

    // Phương thức lấy tổng tiền bán hàng trong một tháng cụ thể
    public BigDecimal getTotalSalesMonth(LocalDate firstDayOfMonth){
        LocalDateTime startDate = firstDayOfMonth.atStartOfDay();
        LocalDateTime endDate = startDate.plusMonths(1);

        List<Purchase> purchases = purchaseRepository.findAllPurchasesBetweenDates(startDate, endDate);

        // Tính tổng tiền bằng cách cộng tất cả giá trị totalPrice của Purchase
        return purchases.stream()
                .map(Purchase::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    @Override
    public Optional<UserStatistics> findUserStatisticsByUserId(Long userId) {
        return userStatisticsRepository.findByUserUserId(userId);
    }

    @Override
    public void saveUserStatistics(UserStatistics userStatistics) {
        userStatisticsRepository.save(userStatistics);
    }
}
