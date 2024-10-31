package com.qlyshopphone_backend.service.impl;

import com.qlyshopphone_backend.dto.request.ProductVariantRequest;
import com.qlyshopphone_backend.model.ColorAttributes;
import com.qlyshopphone_backend.model.ProductVariants;
import com.qlyshopphone_backend.model.Products;
import com.qlyshopphone_backend.model.SizeAttributes;
import com.qlyshopphone_backend.model.enums.Status;
import com.qlyshopphone_backend.repository.ProductVariantRepository;
import com.qlyshopphone_backend.service.ProductVariantService;
import com.qlyshopphone_backend.service.util.EntityFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductVariantServiceImpl implements ProductVariantService {
    private final EntityFinder entityFinder;
    private final ProductVariantRepository productVariantRepository;

    @Transactional
    @Override
    public boolean classifyProductVariant(ProductVariantRequest request) {
        Products product = entityFinder.findProductById(request.getProductId());
        SizeAttributes size = entityFinder.findSizeById(request.getSizeId());
        ColorAttributes color = entityFinder.findColorById(request.getColorId());

        ProductVariants productVariant = new ProductVariants(
                product, color, size, request.getPrice(),
                request.getStock(), Status.ACTIVE
        );
        productVariantRepository.save(productVariant);
        return true;
    }

    @Transactional
    @Override
    public boolean updateClassifyProductVariant(ProductVariantRequest request) {
        ProductVariants productVariant = entityFinder.findProductVariantByPidAndSidAndCid(
                request.getProductId(), request.getSizeId(), request.getColorId());
        productVariant.setPrice(request.getPrice());
        productVariant.setStock(request.getStock());
        productVariantRepository.save(productVariant);
        return true;
    }

    @Transactional
    @Override
    public boolean deleteProductVariant(ProductVariantRequest request) {
        ProductVariants productVariant = entityFinder.findProductVariantByPidAndSidAndCid(
                request.getProductId(), request.getSizeId(), request.getColorId());
        productVariant.setStatus(Status.INACTIVE);
        productVariantRepository.save(productVariant);
        return true;
    }
}
