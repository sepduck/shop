package com.qlyshopphone_backend.model;

import com.qlyshopphone_backend.model.enums.DiscountType;
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
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;  // Mã giảm giá
    private Double discountAmount;  // Số tiền giảm hoặc tỷ lệ phần trăm giảm
    @Enumerated(EnumType.STRING)
    private DiscountType discountType;  // Loại giảm giá (PERCENTAGE hoặc FIXED_AMOUNT)
    private BigDecimal minOrderAmount;  // Giá trị đơn hàng tối thiểu để áp dụng mã giảm giá
    private BigDecimal maxDiscountAmount;  // Số tiền giảm tối đa (nếu là phần trăm)
    @CreationTimestamp
    private LocalDateTime createdAt;  // Thời gian tạo mã giảm giá
    private LocalDateTime expiryDate;  // Ngày hết hạn của mã giảm giá
    private boolean isActive;  // Trạng thái mã giảm giá (có còn sử dụng được không)
}