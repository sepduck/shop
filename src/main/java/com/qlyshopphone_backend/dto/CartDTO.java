package com.qlyshopphone_backend.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Data
public class CartDTO {
    private Integer cartId;
    private Integer productId;
    private String productName;
    private BigDecimal price;
    private Integer userId;
    private Integer quantity;
    private boolean sold;
    private boolean deleteCart;
    private MultipartFile file;
    private String fileBase64;
}
