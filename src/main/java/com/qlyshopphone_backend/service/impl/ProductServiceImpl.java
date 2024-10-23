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
import com.qlyshopphone_backend.service.util.ProductServiceHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @Override
    public List<ProductProjection> getAllProducts() {
        return productRepository.getAllProducts(Status.ACTIVE);
    }

    @Transactional
    @Override
    public boolean createProduct(ProductRequest request) {
        Products product = new Products();
        createAndUpdateProduct(product, request);

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
        Products existingProduct = findProductById(productId);

        createAndUpdateProduct(existingProduct, request);
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
    public List<ProductAttributeResponse> getAllGroupProduct() {
        List<GroupProducts> list = groupProductRepository.findAll();
        return basicMapper.convertToResponseList(list, ProductAttributeResponse.class);
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
    public ProductAttributeResponse saveTrademark(ProductAttributeRequest request) {
        Trademarks trademarks = new Trademarks();
        trademarks.setName(request.getName());
        trademarkRepository.save(trademarks);
        return basicMapper.convertToResponse(trademarks, ProductAttributeResponse.class);
    }

    @Transactional
    @Override
    public ProductAttributeResponse updateTrademark(ProductAttributeRequest request, Long trademarkId) {
        return productServiceHelper.updateAttribute(-
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

    private void createAndUpdateProduct(Products products, ProductRequest request) {
        GroupProducts groupProduct = findGroupProductById(request.getGroupProductId());
        Trademarks trademark = findTrademarkById(request.getTrademarkId());
        Units unit = findUnitById(request.getUnitId());
        Categories category = findCategoryById(request.getCategoryId());

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
    public Products findProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ApiRequestException(PRODUCT_NOT_FOUND, HttpStatus.BAD_REQUEST));
    }

    @Override
    public Locations findLocationById(Long locationId) {
        return locationRepository.findById(locationId)
                .orElseThrow(() -> new ApiRequestException(LOCATION_NOT_FOUND, HttpStatus.BAD_REQUEST));
    }

    @Override
    public GroupProducts findGroupProductById(Long groupProductId) {
        return groupProductRepository.findById(groupProductId)
                .orElseThrow(() -> new ApiRequestException(GROUP_PRODUCT_NOT_FOUND, HttpStatus.BAD_REQUEST));
    }

    @Override
    public Categories findCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ApiRequestException(CATEGORY_NOT_FOUND, HttpStatus.BAD_REQUEST));
    }

    @Override
    public Units findUnitById(Long unitId) {
        return unitRepository.findById(unitId)
                .orElseThrow(() -> new ApiRequestException(UNIT_NOT_FOUND, HttpStatus.BAD_REQUEST));
    }

    @Override
    public Trademarks findTrademarkById(Long trademarkId) {
        return trademarkRepository.findById(trademarkId)
                .orElseThrow(() -> new ApiRequestException(TRADEMARK_NOT_FOUND, HttpStatus.BAD_REQUEST));
    }

//    @Override
//    public List<ProductAttributeResponse> getAllLocation() {
//        List<Locations> list = locationRepository.findAll();
//        return basicMapper.convertToResponseList(list, ProductAttributeResponse.class);
//    }
//
//    @Override
//    public List<ProductAttributeResponse> getAllProperties() {
//        List<Properties> list = propertiesRepository.findAll();
//        return basicMapper.convertToResponseList(list, ProductAttributeResponse.class);
//    }
//
//    @Transactional
//    @Override
//    public ProductAttributeResponse saveProperties(ProductAttributeRequest request) {
//        Properties properties = new Properties();
//        properties.setName(request.getName());
//        propertiesRepository.save(properties);
//        return basicMapper.convertToResponse(properties, ProductAttributeResponse.class);
//    }
//
//    @Transactional
//    @Override
//    public ProductAttributeResponse updateProperties(ProductAttributeRequest request, Long propertiesId) {
//        return productServiceHelper.updateAttribute(
//                propertiesId,
//                request,
//                propertiesRepository::findById,
//                propertiesRepository::save,
//                Properties::getName,
//                Properties::setName,
//                PROPERTIES_NOT_FOUND
//        );
//    }
//
//    @Transactional
//    @Override
//    public boolean deleteProperties(Long propertiesId) {
//        if (!propertiesRepository.existsById(propertiesId)) {
//            throw new ApiRequestException(PROPERTIES_NOT_FOUND, HttpStatus.NOT_FOUND);
//        }
//        propertiesRepository.deleteById(propertiesId);
//        return true;
//    }
//    @Override
//    public List<Products> searchAllByProductName(String productName) {
//        return productRepository.searchAllByProductName(Status.ACTIVE, productName);
//    }
//
//    @Override
//    public List<Map<String, Object>> searchAllByProductId(Long productId) {
//        return productRepository.searchAllByProductId(productId);
//    }
//
//    @Override
//    public List<Map<String, Object>> searchGroupProductId(Long groupProductId) {
//        return productRepository.searchAllByProductId(groupProductId);
//    }
//
//    @Override
//    public List<Map<String, Object>> searchInventory(int number) {
//        return switch (number) {
//            case 1 -> productRepository.getAllProducts(Status.ACTIVE);
//            case 2 -> productRepository.searchBelowInventoryThreshold();
//            case 3 -> productRepository.searchExceedingInventoryLimit();
//            case 4 -> productRepository.searchStockAvailable();
//            case 5 -> productRepository.searchNoInventoryAvailable();
//            default -> new ArrayList<>();
//        };
//    }
//
//    @Override
//    public List<Map<String, Object>> searchActive(int number) {
//        return switch (number) {
//            case 1 -> productRepository.searchActive();
//            case 2 -> productRepository.searchNoActive();
//            case 3 -> productRepository.getAllProducts();
//            default -> new ArrayList<>();
//        };
//    }
//
//    @Override
//    public List<Map<String, Object>> searchDirectSales(int number) {
//        return switch (number) {
//            case 1 -> productRepository.getAllProducts();
//            case 2 -> productRepository.searchDirectSales();
//            case 3 -> productRepository.searchNoDirectSales();
//            default -> new ArrayList<>();
//        };
//    }
//
//    @Override
//    public List<Map<String, Object>> searchByLocationId(Long locationId) {
//        return productRepository.searchAllByProductId(locationId);
//    }


}
