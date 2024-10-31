package com.qlyshopphone_backend.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface RevenueStatisticsService {
    Map<String, Object> getReceiveAPurchaseReportToday();

    Map<String, Object> generateLast30DaysRevenueStatistics();

    List<BigDecimal> calculateDailySalesForLast30Days();


    BigDecimal getPercentageChangeFromYesterdayToToday();

    BigDecimal getPercentageChangeFromLastMonthToThisMonth();
}
