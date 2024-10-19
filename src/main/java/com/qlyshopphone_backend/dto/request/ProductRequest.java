package com.qlyshopphone_backend.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ProductRequest {
    @NotBlank(message = "Tên sản phẩm không được để trống")
    private String name;

    @NotNull(message = "Giá sản phẩm không được để trống")
    private float price;

    @NotNull(message = "Giá vốn không được để trống")
    private float capitalPrice;

    @NotNull(message = "Số lượng tồn kho không được để trống")
    @DecimalMin(value = "0.0", inclusive = false, message = "Số lượng tồn kho phải lớn hơn 0")
    private Long inventory;

    @NotNull(message = "Nhóm sản phẩm không được để trống")
    private Long groupProductId;

    @NotNull(message = "Thương hiệu không được để trống")
    private Long trademarkId;

    @NotNull(message = "Vị trí không được để trống")
    private Long locationId;

    @NotNull(message = "Trọng lượng không được để trống")
    private float weight;

    @NotNull(message = "Thuộc tính không được để trống")
    private Long propertiesId;

    @NotNull(message = "Danh mục không được để trống")
    private Long categoryId;

    @NotNull(message = "Đơn vị không được để trống")
    private Long unitId;

    private boolean directSales;

}
