package com.qlyshopphone_backend.service;

import com.qlyshopphone_backend.model.Orders;

import java.math.BigDecimal;

public interface PaymentService {

    String createPaymentUrl(Orders order, BigDecimal totalAmount) throws Exception;
}
