package com.qlyshopphone_backend.controller.admin;

import static com.qlyshopphone_backend.constant.PathConstant.*;

import com.qlyshopphone_backend.dto.request.CheckoutData;
import com.qlyshopphone_backend.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(API_V1_ORDER)
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping(PLACE)
    public ResponseEntity<Boolean> placeAnOrder(@RequestBody CheckoutData data) {
        return ResponseEntity.ok(orderService.placeOrder(data));
    }

    @PostMapping(CONFIRM)
    public ResponseEntity<Boolean> confirmOrder(@RequestParam Long orderId) {
        return ResponseEntity.ok(orderService.confirmOrder(orderId));
    }

    @PostMapping(DISPATCHING)
    public ResponseEntity<Boolean> dispatchingOrder(@RequestParam Long orderId) {
        return ResponseEntity.ok(orderService.dispatchOrder(orderId));
    }

    @PostMapping(DISPATCHED)
    public ResponseEntity<Boolean> dispatchedOrder(@RequestParam Long orderId) {
        return ResponseEntity.ok(orderService.completeDispatchOrder(orderId));
    }

    @PostMapping(IN_TRANSIT)
    public ResponseEntity<Boolean> inTransitOrder(@RequestParam Long orderId) {
        return ResponseEntity.ok(orderService.markOrderInTransit(orderId));
    }

    @PostMapping(ON_DELIVERY)
    public ResponseEntity<Boolean> onDeliveryOrder(@RequestParam Long orderId) {
        return ResponseEntity.ok(orderService.markOrderOnDelivery(orderId));
    }

    @PostMapping(DELIVERED)
    public ResponseEntity<Boolean> deliveredOrder(@RequestParam Long orderId) {
        return ResponseEntity.ok(orderService.completeOrderDelivery(orderId));
    }

    @PostMapping(RETURNED)
    public ResponseEntity<Boolean> returnOrder(@RequestParam Long orderId) {
        return ResponseEntity.ok(orderService.returnOrder(orderId));
    }

    @PostMapping(CANCELLED)
    public ResponseEntity<Boolean> cancelledOrder(@RequestParam Long orderId) {
        return ResponseEntity.ok(orderService.cancelledOrder(orderId));
    }

    @PostMapping(RECEIVED)
    public ResponseEntity<Boolean> receivedOrder(@RequestParam Long orderId) {
        return ResponseEntity.ok(orderService.receivedOrder(orderId));
    }
}