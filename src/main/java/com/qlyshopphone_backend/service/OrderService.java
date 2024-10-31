package com.qlyshopphone_backend.service;

import com.qlyshopphone_backend.dto.request.CheckoutData;

public interface OrderService {
    boolean placeOrder(CheckoutData data);

    boolean confirmOrder(Long orderId);

    boolean dispatchOrder(Long orderId);

    boolean completeDispatchOrder(Long orderId);

    boolean markOrderInTransit(Long orderId);

    boolean markOrderOnDelivery(Long orderId);

    boolean completeOrderDelivery(Long orderId);

    boolean returnOrder(Long orderId);

    boolean cancelledOrder(Long orderId);

    boolean receivedOrder(Long orderId);
}
