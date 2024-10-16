package com.qlyshopphone_backend.service.impl;
import static com.qlyshopphone_backend.constant.ErrorMessage.*;

import com.qlyshopphone_backend.dto.request.*;
import com.qlyshopphone_backend.exceptions.DataNotFoundException;
import com.qlyshopphone_backend.model.*;
import com.qlyshopphone_backend.repository.*;
import com.qlyshopphone_backend.service.AuthenticationService;
import com.qlyshopphone_backend.service.NotificationService;
import com.qlyshopphone_backend.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final GroupProductRepository groupProductRepository;
    private final TrademarkRepository trademarkRepository;
    private final LocationRepository locationRepository;
    private final PropertiesRepository propertiesRepository;
    private final UnitRepository unitRepository;
    private final CategoryRepository categoryRepository;
    private final NotificationService notificationService;
    private final AuthenticationService authenticationService;

    @Override
    public List<Map<String, Object>> getAllProducts() {
        return productRepository.getAllProducts();
    }

    @Override
    public String saveProduct(ProductRequest productRequest) throws Exception {
        Users users = authenticationService.getAuthenticatedUser();
        Product product = new Product();
        updateProductProperties(product, productRequest);

        sendProductNotification(users, "successfully added", product.getProductName(), null);
        productRepository.save(product);
        return PRODUCT_CREATED_SUCCESSFULLY;
    }

    @Override
    public String updateProduct(Long productId, ProductRequest productRequest) throws Exception {
        Users users = authenticationService.getAuthenticatedUser();
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new DataNotFoundException(PRODUCT_NOT_FOUND));

        updateProductProperties(existingProduct, productRequest);

        sendProductNotification(users, "edited", productRequest.getProductName(), existingProduct.getProductName());
        productRepository.save(existingProduct);
        return PRODUCT_UPDATED_SUCCESSFULLY;
    }


    private void updateProductProperties(Product product, ProductRequest productRequest) throws IOException {
        GroupProduct existingGroupProduct = groupProductRepository.findById(productRequest.getGroupProductId())
                .orElseThrow(() -> new RuntimeException(GROUP_PRODUCT_NOT_FOUND));
        Trademark existingTrademark = trademarkRepository.findById(productRequest.getTrademarkId())
                .orElseThrow(() -> new RuntimeException(TRADEMARK_NOT_FOUND));
        Location existingLocation = locationRepository.findById(productRequest.getLocationId())
                .orElseThrow(() -> new RuntimeException(LOCATION_NOT_FOUND));
        Properties existingProperties = propertiesRepository.findById(productRequest.getPropertiesId())
                .orElseThrow(() -> new RuntimeException(PROPERTIES_NOT_FOUND));
        Unit existingUnit = unitRepository.findById(productRequest.getUnitId())
                .orElseThrow(() -> new RuntimeException(UNIT_NOT_FOUND));
        Category existingCategory = categoryRepository.findById(productRequest.getCategoryId())
                .orElseThrow(() -> new RuntimeException(CATEGORY_NOT_FOUND));

        product.setProductName(productRequest.getProductName());
        product.setPrice(productRequest.getPrice());
        product.setCapitalPrice(productRequest.getCapitalPrice());
        product.setInventory(productRequest.getInventory());
        product.setGroupProduct(existingGroupProduct);
        product.setLocation(existingLocation);
        product.setTrademark(existingTrademark);
        product.setWeight(productRequest.getWeight());
        product.setProperties(existingProperties);
        product.setUnit(existingUnit);
        product.setDeleteProduct(productRequest.isDeleteProduct());
        product.setCategory(existingCategory);
        product.setDirectSales(productRequest.isDirectSales());
        product.setFile(productRequest.getFile().getBytes());
    }

    private void sendProductNotification(Users users, String action, String productName, String newProductName) {
        String message = String.format("%s have %s product %s%s", users.getFirstName(), action, productName,
                newProductName != null ? " to " + newProductName : "");
        notificationService.saveNotification(message, users);
    }

    @Override
    public String deleteProduct(Long productId) {
        Users users = authenticationService.getAuthenticatedUser();
        Product product = productRepository.findById(productId)
                        .orElseThrow(() -> new RuntimeException(PRODUCT_NOT_FOUND));
        productRepository.deleteProductById(product.getProductId());
        String message = users.getFirstName() + " have successfully deleted product " + product.getProductName();
        notificationService.saveNotification(message, users);
        return PRODUCT_DELETED_SUCCESSFULLY;
    }

    @Override
    public List<Map<String, Object>> searchAllByProductName(String productName) {
        return productRepository.searchAllByProductName(productName);
    }

    @Override
    public List<Map<String, Object>> searchAllByProductId(Long productId) {
        return productRepository.searchAllByProductId(productId);
    }

    @Override
    public List<Map<String, Object>> searchGroupProductId(Long groupProductId) {
        return productRepository.searchGroupProductId(groupProductId);
    }

    @Override
    public List<Map<String, Object>> searchInventory(int number) {
        return switch (number) {
            case 1 -> productRepository.getAllProducts();
            case 2 -> productRepository.searchBelowInventoryThreshold();
            case 3 -> productRepository.searchExceedingInventoryLimit();
            case 4 -> productRepository.searchStockAvailable();
            case 5 -> productRepository.searchNoInventoryAvailable();
            default -> new ArrayList<>();
        };
    }

    @Override
    public List<Map<String, Object>> searchActive(int number) {
        return switch (number) {
            case 1 -> productRepository.searchActive();
            case 2 -> productRepository.searchNoActive();
            case 3 -> productRepository.getAllProducts();
            default -> new ArrayList<>();
        };
    }

    @Override
    public List<Map<String, Object>> searchDirectSales(int number) {
        return switch (number) {
            case 1 -> productRepository.getAllProducts();
            case 2 -> productRepository.searchDirectSales();
            case 3 -> productRepository.searchNoDirectSales();
            default -> new ArrayList<>();
        };
    }

    @Override
    public List<Map<String, Object>> searchByLocationId(Long locationId) {
        return productRepository.searchByLocationId(locationId);
    }

    @Override
    public List<Map<String, Object>> searchCategory(int number) {
        return switch (number) {
            case 1 -> productRepository.getAllProducts();
            case 2 -> productRepository.searchCategory1();
            case 3 -> productRepository.searchCategory2();
            case 4 -> productRepository.searchCategory3();
            default -> new ArrayList<>();
        };
    }

    @Override
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

    @Override
    public String saveCategory(CategoryRequest categoryRequest) {
            Category category = new Category();
            category.setCategoryName(categoryRequest.getCategoryName());
            categoryRepository.save(category);
            return CATEGORY_SAVED_SUCCESSFULLY;
    }

    @Override
    public String updateCategory(CategoryRequest categoryRequest, Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException(CATEGORY_NOT_FOUND));
        category.setCategoryName(categoryRequest.getCategoryName());
        categoryRepository.save(category);
        return CATEGORY_UPDATED_SUCCESSFULLY;
    }

    @Override
    public String deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException(CATEGORY_NOT_FOUND));
        categoryRepository.deleteById(category.getCategoryId());
        return CATEGORY_DELETED_SUCCESSFULLY;

    }

    @Override
    public List<GroupProduct> getAllGroupProduct() {
        return groupProductRepository.findAll();
    }

    @Override
    public String saveGroupProduct(GroupProductRequest groupProductRequest) {
        GroupProduct groupProduct = new GroupProduct();
        groupProduct.setGroupProductName(groupProductRequest.getGroupProductName());
        groupProductRepository.save(groupProduct);
        return GROUP_PRODUCT_SAVED_SUCCESSFULLY;
    }

    @Override
    public String updateGroupProduct(GroupProductRequest groupProductRequest, Long groupProductId) {
        GroupProduct groupProduct = groupProductRepository.findById(groupProductId)
                .orElseThrow(() -> new RuntimeException(GROUP_PRODUCT_NOT_FOUND));
        groupProduct.setGroupProductName(groupProductRequest.getGroupProductName());
        groupProductRepository.save(groupProduct);
        return GROUP_PRODUCT_UPDATED_SUCCESSFULLY;
    }

    @Override
    public String deleteGroupProduct(Long groupProductId) {
        GroupProduct groupProduct = groupProductRepository.findById(groupProductId)
                .orElseThrow(() -> new RuntimeException(GROUP_PRODUCT_NOT_FOUND));
        groupProductRepository.deleteById(groupProduct.getGroupProductId());
        return GROUP_PRODUCT_DELETED_SUCCESSFULLY;
    }

    @Override
    public List<Location> getAllLocation() {
        return locationRepository.findAll();
    }

    @Override
    public String saveLocation(LocationRequest locationRequest) {
        Location location = new Location();
        location.setLocationName(locationRequest.getLocationName());
        locationRepository.save(location);
        return LOCATION_SAVED_SUCCESSFULLY;
    }

    @Override
    public String updateLocation(LocationRequest locationRequest, Long locationId) {
        Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> new RuntimeException(LOCATION_NOT_FOUND));
        location.setLocationName(locationRequest.getLocationName());
        locationRepository.save(location);
        return LOCATION_UPDATED_SUCCESSFULLY;
    }

    @Override
    public String deleteLocation(Long locationId) {
        Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> new RuntimeException(LOCATION_NOT_FOUND));
        locationRepository.deleteById(location.getLocationId());
        return LOCATION_DELETED_SUCCESSFULLY;
    }

    @Override
    public List<Properties> getAllProperties() {
        return propertiesRepository.findAll();
    }

    @Override
    public String saveProperties(PropertiesRequest propertiesRequest) {
        Properties properties = new Properties();
        properties.setPropertiesName(propertiesRequest.getPropertiesName());
        propertiesRepository.save(properties);
        return PROPERTIES_SAVED_SUCCESSFULLY;
    }

    @Override
    public String updateProperties(PropertiesRequest propertiesRequest, Long propertiesId) {
        Properties properties = propertiesRepository.findById(propertiesId)
                .orElseThrow(() -> new RuntimeException(PROPERTIES_NOT_FOUND));
        properties.setPropertiesName(propertiesRequest.getPropertiesName());
        propertiesRepository.save(properties);
        return PROPERTIES_UPDATED_SUCCESSFULLY;
    }

    @Override
    public String deleteProperties(Long propertiesId) {
        Properties properties = propertiesRepository.findById(propertiesId)
                .orElseThrow(() -> new RuntimeException(PROPERTIES_NOT_FOUND));
        propertiesRepository.deleteById(properties.getPropertiesId());
        return PROPERTIES_DELETED_SUCCESSFULLY;
    }

    @Override
    public List<Trademark> getAllTrademarks() {
        return trademarkRepository.findAll();
    }

    @Override
    public String saveTrademark(TrademarkRequest trademarkRequest) {
        Trademark trademark = new Trademark();
        trademark.setTrademarkName(trademarkRequest.getTrademarkName());
        trademarkRepository.save(trademark);
        return TRADEMARK_SAVED_SUCCESSFULLY;
    }

    @Override
    public String updateTrademark(TrademarkRequest trademarkRequest, Long trademarkId) {
        Trademark trademark = trademarkRepository.findById(trademarkId)
                .orElseThrow(() -> new RuntimeException(TRADEMARK_NOT_FOUND));
        trademark.setTrademarkName(trademarkRequest.getTrademarkName());
        trademarkRepository.save(trademark);
        return TRADEMARK_UPDATED_SUCCESSFULLY;
    }

    @Override
    public String deleteTrademark(Long trademarkId) {
        Trademark trademark = trademarkRepository.findById(trademarkId)
                .orElseThrow(() -> new RuntimeException(TRADEMARK_NOT_FOUND));
        trademarkRepository.deleteById(trademark.getTrademarkId());
        return TRADEMARK_DELETED_SUCCESSFULLY;
    }

    @Override
    public List<Unit> getAllUnits() {
        return unitRepository.findAll();
    }

    @Override
    public String saveUnit(UnitRequest unitRequest) {
        Unit unit = new Unit();
        unit.setUnitName(unitRequest.getUnitName());
        unitRepository.save(unit);
        return UNIT_SAVED_SUCCESSFULLY;
    }

    @Override
    public String updateUnit(UnitRequest unitRequest, Long unitId) {
        Unit unit = unitRepository.findById(unitId)
                .orElseThrow(() -> new RuntimeException(UNIT_NOT_FOUND));
        unit.setUnitName(unitRequest.getUnitName());
        unitRepository.save(unit);
        return UNIT_UPDATED_SUCCESSFULLY;
    }

    @Override
    public String deleteUnit(Long unitId) {
        Unit unit = unitRepository.findById(unitId)
                .orElseThrow(() -> new RuntimeException(UNIT_NOT_FOUND));
        unitRepository.deleteById(unit.getUnitId());
        return UNIT_DELETED_SUCCESSFULLY;
    }
}
