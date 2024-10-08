package com.qlyshopphone_backend.service;

import com.qlyshopphone_backend.dto.*;
import com.qlyshopphone_backend.model.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ProductService {
    List<Map<String, Object>> getAllProducts();

    String saveProduct(ProductDTO productDTO) throws Exception;

    String updateProduct(Long productId, ProductDTO productDTO) throws Exception;

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

    String saveCategory(CategoryDTO categoryDTO);

    String updateCategory(CategoryDTO categoryDTO, Long categoryId);

    String deleteCategory(Long categoryId);

    List<GroupProduct> getAllGroupProduct();

    String saveGroupProduct(GroupProductDTO groupProductDTO);

    String updateGroupProduct(GroupProductDTO groupProductDTO, Long groupProductId);

    String deleteGroupProduct(Long groupProductId);

    List<Location> getAllLocation();

    String saveLocation(LocationDTO locationDTO);

    String updateLocation(LocationDTO locationDTO, Long locationId);

    String deleteLocation(Long locationId);

    List<Properties> getAllProperties();

    String saveProperties(PropertiesDTO propertiesDTO);

    String updateProperties(PropertiesDTO propertiesDTO, Long propertiesId);

    String deleteProperties(Long propertiesId);

    List<Trademark> getAllTrademarks();

    String saveTrademark(TrademarkDTO trademarkDTO);

    String updateTrademark(TrademarkDTO trademarkDTO, Long trademarkId);

    String deleteTrademark(Long trademarkId);

    List<Unit> getAllUnits();

    String saveUnit(UnitDTO unitDTO);

    String updateUnit(UnitDTO unitDTO, Long unitId);

    String deleteUnit(Long unitId);

}
