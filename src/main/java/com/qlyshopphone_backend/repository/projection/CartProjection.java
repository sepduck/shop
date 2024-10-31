package com.qlyshopphone_backend.repository.projection;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface CartProjection {
    Long getId();

    ProductVariantInfo getProductVariant();

    UserInfo getUser();

    Long getQuantity();

    boolean getSold();

    LocalDateTime getCreatedAt();

    interface UserInfo {
        Long getId();

        String getFirstName();

        String getLastName();
    }

    interface ProductVariantInfo {
        Long getId();

        ProductInfo getProduct();

        ColorInfo getColor();

        SizeInfo getSize();

        BigDecimal getPrice();

        interface ProductInfo {
            Long getId();

            String getName();
        }

        interface ColorInfo {
            Long getId();

            String getColorName();

            String getHexCode();
        }

        interface SizeInfo {
            Long getId();

            String getSizeValue();

            String getSizeType();
        }

    }
}
