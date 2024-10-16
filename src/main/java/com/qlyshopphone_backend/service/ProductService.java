package com.qlyshopphone_backend.service;

import com.qlyshopphone_backend.dto.request.*;
import com.qlyshopphone_backend.model.*;

import java.util.List;
import java.util.Map;

public interface ProductService {
    List<Map<String, Object>> getAllProducts();

    String saveProduct(ProductRequest productRequest) throws Exception;

    String updateProduct(Long productId, ProductRequest productRequest) throws Exception;

    String deleteProduct(Long productId);

    List<Map<String, Object>> searchAllByProductName(String productName);

    List<Map<String, Object>> searchAllByProductId(Long productId);

    List<Map<String, Object>> searchGroupProductId(Long groupProductId);

    List<Map<String, Object>> searchInventory(int number);

    List<Map<String, Object>> searchActive(int number);

    List<Map<String, Object>> searchDirectSales(int number);

    List<Map<String, Object>> searchByLocationId( Long locationId);

    List<Map<String, Object>> searchCategory(int number);

    List<Category> getAllCategory();

    String saveCategory(CategoryRequest categoryRequest);

    String updateCategory(CategoryRequest categoryRequest, Long categoryId);

    String deleteCategory(Long categoryId);

    List<GroupProduct> getAllGroupProduct();

    String saveGroupProduct(GroupProductRequest groupProductRequest);

    String updateGroupProduct(GroupProductRequest groupProductRequest, Long groupProductId);

    String deleteGroupProduct(Long groupProductId);

    List<Location> getAllLocation();

    String saveLocation(LocationRequest locationRequest);

    String updateLocation(LocationRequest locationRequest, Long locationId);

    String deleteLocation(Long locationId);

    List<Properties> getAllProperties();

    String saveProperties(PropertiesRequest propertiesRequest);

    String updateProperties(PropertiesRequest propertiesRequest, Long propertiesId);

    String deleteProperties(Long propertiesId);

    List<Trademark> getAllTrademarks();

    String saveTrademark(TrademarkRequest trademarkRequest);

    String updateTrademark(TrademarkRequest trademarkRequest, Long trademarkId);

    String deleteTrademark(Long trademarkId);

    List<Unit> getAllUnits();

    String saveUnit(UnitRequest unitRequest);

    String updateUnit(UnitRequest unitRequest, Long unitId);

    String deleteUnit(Long unitId);

}
