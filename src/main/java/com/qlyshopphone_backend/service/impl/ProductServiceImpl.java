package com.qlyshopphone_backend.service.impl;

import static com.qlyshopphone_backend.constant.ErrorMessage.*;

import com.qlyshopphone_backend.dto.request.*;
import com.qlyshopphone_backend.dto.response.ProductAttributeResponse;
import com.qlyshopphone_backend.dto.response.ProductResponse;
import com.qlyshopphone_backend.exceptions.ApiRequestException;
import com.qlyshopphone_backend.mapper.BasicMapper;
import com.qlyshopphone_backend.model.*;
import com.qlyshopphone_backend.model.enums.Status;
import com.qlyshopphone_backend.repository.*;
import com.qlyshopphone_backend.repository.projection.ProductAttributeProjection;
import com.qlyshopphone_backend.repository.projection.ProductProjection;
import com.qlyshopphone_backend.service.ProductService;
import com.qlyshopphone_backend.service.util.EntityFinder;
import com.qlyshopphone_backend.service.util.ProductServiceHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final GroupProductRepository groupProductRepository;
    private final TrademarkRepository trademarkRepository;
    private final UnitRepository unitRepository;
    private final CategoryRepository categoryRepository;
    private final BasicMapper basicMapper;
    private final ProductServiceHelper productServiceHelper;
    private final ImageRepository imageRepository;
    private final LocationRepository locationRepository;
    private final EntityFinder entityFinder;

    @Override
    public List<ProductProjection> getAllProducts() {
        return productRepository.getAllProducts(Status.ACTIVE);
    }

    @Transactional
    @Override
    public boolean createProduct(ProductRequest request) {
        Products product = new Products();
        populateProductData(product, request);

        Products savedProduct = productRepository.save(product);
        List<Images> images = imageRepository.findAllById(request.getImageIds());
        savedProduct.getImages().addAll(images);
        if (!images.isEmpty()) {
            savedProduct.setThumbnail(images.getFirst().getUrl());
        }
        productRepository.save(savedProduct);
        return true;
    }

    @Transactional
    @Override
    public ProductResponse updateProduct(Long productId, ProductRequest request) {
        Products existingProduct = entityFinder.findProductById(productId);

        populateProductData(existingProduct, request);
        productRepository.save(existingProduct);
        return basicMapper.convertToResponse(existingProduct, ProductResponse.class);
    }

    @Transactional
    @Override
    public boolean deleteProduct(Long productId) {
        return productRepository.findById(productId)
                .map(product -> {
                    product.setStatus(Status.DELETED);
                    productRepository.save(product);
                    return true;
                })
                .orElseThrow(() -> new ApiRequestException(PRODUCT_NOT_FOUND, HttpStatus.NOT_FOUND));
    }


    @Override
    public List<ProductAttributeResponse> getAllGroupProducts() {
        List<GroupProducts> groups = groupProductRepository.findAll();
        return basicMapper.convertToResponseList(groups, ProductAttributeResponse.class);
    }

    @Transactional
    @Override
    public ProductAttributeResponse createGroupProduct(ProductAttributeRequest request) {
        GroupProducts groupProduct = new GroupProducts();
        groupProduct.setName(request.getName());
        groupProductRepository.save(groupProduct);
        return basicMapper.convertToResponse(groupProduct, ProductAttributeResponse.class);
    }

    @Transactional
    @Override
    public ProductAttributeResponse updateGroupProduct(ProductAttributeRequest request, Long groupProductId) {
        return productServiceHelper.updateAttribute(
                groupProductId,
                request,
                groupProductRepository::findById,
                groupProductRepository::save,
                GroupProducts::getName,
                GroupProducts::setName,
                GROUP_PRODUCT_NOT_FOUND
        );
    }

    @Transactional
    @Override
    public boolean deleteGroupProduct(Long groupProductId) {
        if (!groupProductRepository.existsById(groupProductId)) {
            throw new ApiRequestException(GROUP_PRODUCT_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        groupProductRepository.deleteById(groupProductId);
        return true;
    }

    @Override
    public List<ProductAttributeResponse> getAllTrademarks() {
        List<Trademarks> list = trademarkRepository.findAll();
        return basicMapper.convertToResponseList(list, ProductAttributeResponse.class);
    }

    @Transactional
    @Override
    public ProductAttributeResponse createTrademark(ProductAttributeRequest request) {
        Trademarks trademarks = new Trademarks();
        trademarks.setName(request.getName());
        trademarkRepository.save(trademarks);
        return basicMapper.convertToResponse(trademarks, ProductAttributeResponse.class);
    }

    @Transactional
    @Override
    public ProductAttributeResponse updateTrademark(ProductAttributeRequest request, Long trademarkId) {
        return productServiceHelper.updateAttribute(
                trademarkId,
                request,
                trademarkRepository::findById,
                trademarkRepository::save,
                Trademarks::getName,
                Trademarks::setName,
                TRADEMARK_NOT_FOUND
        );
    }

    @Transactional
    @Override
    public boolean deleteTrademark(Long id) {
        if (!trademarkRepository.existsById(id)) {
            throw new ApiRequestException(TRADEMARK_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        trademarkRepository.deleteById(id);
        return true;
    }

    @Override
    public List<ProductAttributeResponse> getAllUnits() {
        List<Units> list = unitRepository.findAll();
        return basicMapper.convertToResponseList(list, ProductAttributeResponse.class);
    }

    @Transactional
    @Override
    public ProductAttributeResponse createUnit(ProductAttributeRequest request) {
        Units units = new Units();
        units.setName(request.getName());
        unitRepository.save(units);
        return basicMapper.convertToResponse(units, ProductAttributeResponse.class);
    }

    @Transactional
    @Override
    public ProductAttributeResponse updateUnit(ProductAttributeRequest request, Long unitId) {
        return productServiceHelper.updateAttribute(
                unitId,
                request,
                unitRepository::findById,
                unitRepository::save,
                Units::getName,
                Units::setName,
                UNIT_NOT_FOUND
        );
    }

    @Transactional
    @Override
    public boolean deleteUnit(Long unitId) {
        if (!unitRepository.existsById(unitId)) {
            throw new ApiRequestException(UNIT_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        unitRepository.deleteById(unitId);
        return true;
    }

    @Override
    public List<ProductAttributeProjection> getAllCategories() {
        return categoryRepository.findAllCategories();
    }

    @Transactional
    @Override
    public ProductAttributeResponse createCategory(ProductAttributeRequest request) {
        Categories categories = new Categories();
        categories.setName(request.getName());
        categoryRepository.save(categories);
        return basicMapper.convertToResponse(categories, ProductAttributeResponse.class);
    }

    @Transactional
    @Override
    public ProductAttributeResponse updateCategory(ProductAttributeRequest request, Long categoryId) {
        return productServiceHelper.updateAttribute(
                categoryId,
                request,
                categoryRepository::findById,
                categoryRepository::save,
                Categories::getName,
                Categories::setName,
                CATEGORY_NOT_FOUND
        );
    }

    @Transactional
    @Override
    public boolean deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new ApiRequestException(CATEGORY_NOT_FOUND, HttpStatus.BAD_REQUEST);
        }
        categoryRepository.deleteById(id);
        return true;
    }

    private void populateProductData(Products products, ProductRequest request) {
        GroupProducts groupProduct = entityFinder.findGroupProductById(request.getGroupProductId());
        Trademarks trademark = entityFinder.findTrademarkById(request.getTrademarkId());
        Units unit = entityFinder.findUnitById(request.getUnitId());
        Categories category = entityFinder.findCategoryById(request.getCategoryId());

        products.setName(request.getName());
        products.setGroupProduct(groupProduct);
        products.setTrademark(trademark);
        products.setUnit(unit);
        products.setStatus(Status.ACTIVE);
        products.setCategory(category);
    }


    @Transactional
    @Override
    public ProductAttributeResponse createLocation(ProductAttributeRequest request) {
        Locations locations = new Locations();
        locations.setName(request.getName());
        locationRepository.save(locations);
        return basicMapper.convertToResponse(locations, ProductAttributeResponse.class);
    }

    @Transactional
    @Override
    public ProductAttributeResponse updateLocation(ProductAttributeRequest request, Long locationId) {
        return productServiceHelper.updateAttribute(
                locationId,
                request,
                locationRepository::findById,
                locationRepository::save,
                Locations::getName,
                Locations::setName,
                LOCATION_NOT_FOUND
        );
    }

    @Transactional
    @Override
    public boolean deleteLocation(Long locationId) {
        if (!locationRepository.existsById(locationId)) {
            throw new ApiRequestException(LOCATION_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        locationRepository.deleteById(locationId);
        return true;
    }

    @Override
    public List<ProductProjection> searchProductByName(String name) {
        return productRepository.searchProductsByName(Status.ACTIVE, name);
    }

    @Override
    public List<ProductProjection> searchProductById(Long id) {
        return productRepository.searchProductsById(Status.ACTIVE, id);
    }

    @Override
    public List<ProductProjection> searchProductByGroupProductId(Long id) {
        return productRepository.searchProductsByGroupProductId(Status.ACTIVE, id);
    }

    @Override
    public List<ProductProjection> searchProductsByTrademarkId(Long id) {
        return productRepository.searchProductsByTrademarkId(Status.ACTIVE, id);
    }

    @Override
    public List<ProductProjection> searchProductsByStatus(String status) {
        return productRepository.searchProductsByStatus(Status.valueOf(status));
    }

    @Override
    public List<ProductProjection> getProductById(Long id) {
        return productRepository.findProductById(Status.ACTIVE, id);
    }

    @Override
    public List<ProductProjection> searchProductsByCategoryId(Long id) {
        return productRepository.searchProductsByCategoryId(Status.ACTIVE, id);
    }

}
