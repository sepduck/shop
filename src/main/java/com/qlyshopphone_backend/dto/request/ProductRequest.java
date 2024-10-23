package com.qlyshopphone_backend.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    @NotBlank(message = "Tên sản phẩm không được để trống")
    private String name;

    @NotNull(message = "Nhóm sản phẩm không được để trống")
    private Long groupProductId;

    @NotNull(message = "Thương hiệu không được để trống")
    private Long trademarkId;

    @NotNull(message = "Danh mục không được để trống")
    private Long categoryId;

    @NotNull(message = "Đơn vị không được để trống")
    private Long unitId;

    private List<Long> imageIds;

}
