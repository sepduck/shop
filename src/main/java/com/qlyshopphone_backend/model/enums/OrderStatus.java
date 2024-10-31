package com.qlyshopphone_backend.model.enums;

public enum OrderStatus {
    PENDING_CONFIRMATION,  // Chờ xác nhận
    CONFIRMED,             // Đã xác nhận
    DISPATCHING,           // Đang bàn giao cho đơn vị vận chuyển
    DISPATCHED,            // Đã bàn giao cho đơn vị vận chuyển
    IN_TRANSIT,            // Đang vận chuyển
    ON_DELIVERY,           // Đang giao hàng
    DELIVERED,             // Đã giao hàng
    RETURNED,              // Hoàn trả
    RECEIVED,              // Đã nhận hàng (hoặc Hoàn tất)
    CANCELLED              // Hủy
}
