package com.qlyshopphone_backend.dto.request;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CheckoutData {
    private Long customerInfoId;
    private Long discountId;
    private BigDecimal totalAmount;
    private List<OrderItemRequest> items;
    private String paymentMethod;  // Chọn phương thức thanh toán
    private Long shippingMethodId;  // Phương thức vận chuyển (nếu có)
}