package com.qlyshopphone_backend.dto;

import com.qlyshopphone_backend.model.GroupProduct;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ProductDTO {
    @NotBlank(message = "Tên sản phẩm không được để trống")
    private String productName;

    @NotNull(message = "Giá sản phẩm không được để trống")
    @DecimalMin(value = "0.0", inclusive = false, message = "Giá sản phẩm phải lớn hơn 0")
    private BigDecimal price;

    @NotNull(message = "Giá vốn không được để trống")
    @DecimalMin(value = "0.0", inclusive = false, message = "Giá vốn phải lớn hơn 0")
    private BigDecimal capitalPrice;

    @NotNull(message = "Số lượng tồn kho không được để trống")
    @DecimalMin(value = "0.0", inclusive = false, message = "Số lượng tồn kho phải lớn hơn 0")
    private Integer inventory;

    @NotNull(message = "Nhóm sản phẩm không được để trống")
    private Integer groupProductId;

    @NotNull(message = "Thương hiệu không được để trống")
    private Integer trademarkId;

    @NotNull(message = "Vị trí không được để trống")
    private Integer locationId;

    @NotNull(message = "Trọng lượng không được để trống")
    @DecimalMin(value = "0.0", inclusive = false, message = "Trọng lượng phải lớn hơn 0")
    private BigDecimal weight;

    @NotNull(message = "Thuộc tính không được để trống")
    private Integer propertiesId;

    @NotNull(message = "Danh mục không được để trống")
    private Integer categoryId;

    @NotNull(message = "Đơn vị không được để trống")
    private Integer unitId;

    private boolean directSales;
    private boolean deleteProduct;
    private String thumbnail;

    @NotNull(message = "Ảnh sản phẩm không được để trống")
    private MultipartFile file;
}