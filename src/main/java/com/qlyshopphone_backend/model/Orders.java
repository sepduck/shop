package com.qlyshopphone_backend.model;

import com.qlyshopphone_backend.model.enums.OrderStatus;
import com.qlyshopphone_backend.model.enums.PaymentMethod;
import com.qlyshopphone_backend.model.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;
    @OneToOne
    @JoinColumn(name = "customer_info_id")
    private CustomerInfo customerInfo;
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems;
    @ManyToOne
    @JoinColumn(name = "discount_id")
    private Discount discount;  // Mã giảm giá nếu có
    private BigDecimal totalAmount;  // Tổng số tiền đơn hàng
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
