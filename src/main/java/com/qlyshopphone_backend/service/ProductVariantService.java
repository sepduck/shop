package com.qlyshopphone_backend.service;

import com.qlyshopphone_backend.dto.request.ProductVariantRequest;

public interface ProductVariantService {

    boolean classifyProductVariant(ProductVariantRequest request);

    boolean updateClassifyProductVariant(ProductVariantRequest request);

    boolean deleteProductVariant(ProductVariantRequest request);
}
