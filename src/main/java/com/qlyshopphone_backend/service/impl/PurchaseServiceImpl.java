package com.qlyshopphone_backend.service.impl;

import com.qlyshopphone_backend.dto.PurchaseDTO;
import com.qlyshopphone_backend.mapper.PurchaseMapper;
import com.qlyshopphone_backend.model.BaseReponse;
import com.qlyshopphone_backend.model.Purchase;
import com.qlyshopphone_backend.repository.PurchaseRepository;
import com.qlyshopphone_backend.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class PurchaseServiceImpl extends BaseReponse implements PurchaseService {
    private final PurchaseRepository purchaseRepository;

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
                .map(PurchaseMapper :: toDto)
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


}
