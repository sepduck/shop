package com.qlyshopphone_backend.service;

import com.qlyshopphone_backend.dto.request.*;
import com.qlyshopphone_backend.dto.response.ProductAttributeResponse;
import com.qlyshopphone_backend.dto.response.ProductResponse;
import com.qlyshopphone_backend.model.*;
import com.qlyshopphone_backend.repository.projection.ProductAttributeProjection;
import com.qlyshopphone_backend.repository.projection.ProductProjection;

import java.util.List;
import java.util.Map;

public interface ProductService {
    List<ProductProjection> getAllProducts();

    boolean createProduct(ProductRequest request);

    ProductResponse updateProduct(Long productId, ProductRequest request);

    boolean deleteProduct(Long productId);

    List<ProductAttributeProjection> getAllCategories();

    ProductAttributeResponse createCategory(ProductAttributeRequest request);

    ProductAttributeResponse updateCategory(ProductAttributeRequest request, Long categoryId);

    boolean deleteCategory(Long categoryId);

    List<ProductAttributeResponse> getAllGroupProduct();

    ProductAttributeResponse createGroupProduct(ProductAttributeRequest request);

    ProductAttributeResponse updateGroupProduct(ProductAttributeRequest request, Long groupProductId);

    boolean deleteGroupProduct(Long groupProductId);

    List<ProductAttributeResponse> getAllTrademarks();

    ProductAttributeResponse saveTrademark(ProductAttributeRequest request);

    ProductAttributeResponse updateTrademark(ProductAttributeRequest request, Long trademarkId);

    boolean deleteTrademark(Long trademarkId);

    List<ProductAttributeResponse> getAllUnits();

    ProductAttributeResponse createUnit(ProductAttributeRequest productAttributeRequest);

    ProductAttributeResponse updateUnit(ProductAttributeRequest productAttributeRequest, Long unitId);

    boolean deleteUnit(Long unitId);


//    List<ProductAttributeResponse> getAllLocation();
//
    ProductAttributeResponse createLocation(ProductAttributeRequest request);

    ProductAttributeResponse updateLocation(ProductAttributeRequest request, Long locationId);

    boolean deleteLocation(Long locationId);

    Products findProductById(Long productId);

    Locations findLocationById(Long locationId);

    GroupProducts findGroupProductById(Long groupProductId);

    Categories findCategoryById(Long categoryId);

    Units findUnitById(Long unitId);

    Trademarks findTrademarkById(Long trademarkId);
//
//    List<ProductAttributeResponse> getAllProperties();
//
//    ProductAttributeResponse saveProperties(ProductAttributeRequest request);
//
//    ProductAttributeResponse updateProperties(ProductAttributeRequest request, Long propertiesId);
//
//    boolean deleteProperties(Long propertiesId);
//
//    List<Products> searchAllByProductName(String productName);
//
//    List<Map<String, Object>> searchAllByProductId(Long productId);
//
//    List<Map<String, Object>> searchGroupProductId(Long groupProductId);
//
//    List<Map<String, Object>> searchInventory(int number);
//
//    List<Map<String, Object>> searchActive(int number);
//
//    List<Map<String, Object>> searchDirectSales(int number);
//
//    List<Map<String, Object>> searchByLocationId( Long locationId);

}
