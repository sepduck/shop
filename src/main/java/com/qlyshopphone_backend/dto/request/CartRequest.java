package com.qlyshopphone_backend.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Data
public class CartRequest {
    private Long cartId;
    private Long productId;
    private String name;
    private float price;
    private Long userId;
    private Long quantity;
    private boolean sold;
    private boolean deleteCart;}
