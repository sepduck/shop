package com.qlyshopphone_backend.service.impl;

import com.qlyshopphone_backend.model.Orders;
import com.qlyshopphone_backend.model.enums.OrderStatus;
import com.qlyshopphone_backend.repository.OrderRepository;
import com.qlyshopphone_backend.service.RevenueStatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RevenueStatisticsServiceServiceImpl implements RevenueStatisticsService {
    private final OrderRepository orderRepository;

    @Override
    public Map<String, Object> getReceiveAPurchaseReportToday() {
        LocalDateTime start = LocalDate.now().atStartOfDay(); // Bắt đầu từ 00:00:00
        LocalDateTime end = start.plusDays(1); // Kết thúc trước 00:00:00 ngày hôm sau
        List<Orders> orders = orderRepository.findByToday(start, end, OrderStatus.RECEIVED);
        BigDecimal totalPrice = orders.stream()
                .map(Orders::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Map<String, Object> report = new HashMap<>();
        report.put("orders", orders);
        report.put("totalPrice", totalPrice);
        return report;
    }

    @Override
    public Map<String, Object> generateLast30DaysRevenueStatistics() {
        LocalDateTime startDate = LocalDateTime.now().minusDays(30);
        LocalDateTime endDate = LocalDateTime.now();

        List<Orders> orders = orderRepository.findByToday(startDate, endDate, OrderStatus.RECEIVED);

        BigDecimal totalPrice = orders.stream()
                .map(Orders::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Map<String, Object> report = new HashMap<>();
        report.put("orders", orders); // Đưa vào danh sách đơn hàng
        report.put("totalPrice", totalPrice); // Đưa vào tổng giá trị

        return report;
    }

    @Override
    public List<BigDecimal> calculateDailySalesForLast30Days() {
        LocalDate startDate = LocalDate.now().minusDays(30);
        LocalDate endDate = LocalDate.now();

        List<Orders> orders = orderRepository.findByToday(
                startDate.atStartOfDay(),
                endDate.atTime(23, 59, 59), OrderStatus.RECEIVED);

        Map<LocalDate, BigDecimal> dailySaleMap = orders.stream()
                .collect(Collectors.groupingBy(
                        order -> order.getCreatedAt().toLocalDate(),
                        Collectors.mapping(Orders::getTotalAmount,
                                Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))
                ));
        return startDate.datesUntil(endDate.plusDays(1))
                .map(date -> dailySaleMap.getOrDefault(date, BigDecimal.ZERO))
                .collect(Collectors.toList());
    }

    @Override
    public BigDecimal getPercentageChangeFromYesterdayToToday() {
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);

        // Tính khoảng thời gian cho hôm qua và hôm nay
        LocalDateTime startYesterday = yesterday.atStartOfDay();
        LocalDateTime endYesterday = startYesterday.plusDays(1);
        LocalDateTime startToday = today.atStartOfDay();
        LocalDateTime endToday = startToday.plusDays(1);

        // Lấy danh sách đơn hàng đã nhận cho hôm qua và hôm nay
        List<Orders> ordersYesterday = orderRepository.findByToday(startYesterday, endYesterday, OrderStatus.RECEIVED);
        List<Orders> ordersToday = orderRepository.findByToday(startToday, endToday, OrderStatus.RECEIVED);

        // Tính tổng doanh thu hôm qua và hôm nay
        BigDecimal totalYesterday = ordersYesterday.stream()
                .map(Orders::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalToday = ordersToday.stream()
                .map(Orders::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Tính phần trăm thay đổi
        if (totalYesterday.compareTo(BigDecimal.ZERO) == 0) {
            // Nếu tổng tiền của ngày trước bằng 0, thì phần trăm thay đổi là 100% nếu hôm nay có bán hàng, ngược lại là 0%
            return totalToday.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO : BigDecimal.valueOf(100);
        }

        return totalToday.subtract(totalYesterday)
                .divide(totalYesterday, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));
    }


    @Override
    public BigDecimal getPercentageChangeFromLastMonthToThisMonth() {
        LocalDate today = LocalDate.now();
        LocalDate firstDayOfCurrentMonth = today.withDayOfMonth(1);
        LocalDate firstDayOfLastMonth = firstDayOfCurrentMonth.minusMonths(1);

        // Tính khoảng thời gian cho tháng trước và tháng hiện tại
        LocalDateTime startDateLastMonth = firstDayOfLastMonth.atStartOfDay();
        LocalDateTime endDateLastMonth = firstDayOfCurrentMonth.atStartOfDay();
        LocalDateTime startDateCurrentMonth = firstDayOfCurrentMonth.atStartOfDay();
        LocalDateTime endDateCurrentMonth = startDateCurrentMonth.plusMonths(1);

        // Lấy tổng doanh thu tháng trước
        BigDecimal totalLastMonth = orderRepository.findByToday(startDateLastMonth, endDateLastMonth, OrderStatus.RECEIVED).stream()
                .map(Orders::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Lấy tổng doanh thu tháng hiện tại
        BigDecimal totalThisMonth = orderRepository.findByToday(startDateCurrentMonth, endDateCurrentMonth, OrderStatus.RECEIVED).stream()
                .map(Orders::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Tính phần trăm thay đổi
        if (totalLastMonth.compareTo(BigDecimal.ZERO) == 0) {
            return totalThisMonth.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO : BigDecimal.ONE;
        }

        return totalThisMonth.subtract(totalLastMonth)
                .divide(totalLastMonth, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));
    }
}
