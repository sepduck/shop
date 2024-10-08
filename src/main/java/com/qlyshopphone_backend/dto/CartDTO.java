package com.qlyshopphone_backend.dto;
import static com.qlyshopphone_backend.constant.ErrorMessage.*;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Data
public class CartDTO {
    private Long cartId;
    private Long productId;
    private String productName;
    private BigDecimal price;
    private Long userId;
    private Long quantity;
    private boolean sold;
    private boolean deleteCart;
    private MultipartFile file;
    private String fileBase64;
}
