package com.qlyshopphone_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class VnPayTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Orders order;  // Tham chiếu đến đơn hàng
    private String transactionId;  // Mã giao dịch VNPAY
    private BigDecimal amount;  // Số tiền đã thanh toán
    private String vnpResponseCode;  // Mã phản hồi từ VNPAY (00 là thành công)
    private String paymentStatus;  // Trạng thái thanh toán (ví dụ: SUCCESS, PENDING, FAILED)
    @CreationTimestamp
    private LocalDateTime transactionDate;  // Thời gian tạo giao dịch
}