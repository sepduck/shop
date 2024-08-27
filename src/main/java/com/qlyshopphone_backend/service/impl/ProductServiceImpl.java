package com.qlyshopphone_backend.service.impl;
import static com.qlyshopphone_backend.constant.ErrorMessage.*;

import com.qlyshopphone_backend.dto.*;
import com.qlyshopphone_backend.exceptions.DataNotFoundException;
import com.qlyshopphone_backend.model.*;
import com.qlyshopphone_backend.repository.*;
import com.qlyshopphone_backend.service.NotificationService;
import com.qlyshopphone_backend.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


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

    @Override
    public List<Map<String, Object>> getAllProducts() {
        return productRepository.getAllProducts();
    }

    @Override
    public void createProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public String saveProduct(ProductDTO productDTO, Users users) throws Exception {
        GroupProduct existingGroupProduct = groupProductRepository.findById(productDTO.getGroupProductId())
                .orElseThrow(() -> new DataNotFoundException(GROUP_PRODUCT_NOT_FOUND));
        Trademark existingTrademark = trademarkRepository.findById(productDTO.getTrademarkId())
                .orElseThrow(() -> new DataNotFoundException(TRADEMARK_NOT_FOUND));
        Location existingLocation = locationRepository.findById(productDTO.getLocationId())
                .orElseThrow(() -> new DataNotFoundException(LOCATION_NOT_FOUND));
        Properties existingProperties = propertiesRepository.findById(productDTO.getPropertiesId())
                .orElseThrow(() -> new DataNotFoundException(PROPERTIES_NOT_FOUND));
        Unit existingUnit = unitRepository.findById(productDTO.getUnitId())
                .orElseThrow(() -> new DataNotFoundException(UNIT_NOT_FOUND));
        Category existingCategory = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new DataNotFoundException(CATEGORY_NOT_FOUND));


        Product product = new Product();
        product.setProductName(productDTO.getProductName());
        product.setPrice(productDTO.getPrice());
        product.setCapitalPrice(productDTO.getCapitalPrice());
        product.setInventory(productDTO.getInventory());
        product.setGroupProduct(existingGroupProduct);
        product.setLocation(existingLocation);
        product.setTrademark(existingTrademark);
        product.setWeight(productDTO.getWeight());
        product.setProperties(existingProperties);
        product.setUnit(existingUnit);
        product.setDeleteProduct(productDTO.isDeleteProduct());
        product.setCategory(existingCategory);
        product.setDirectSales(productDTO.isDirectSales());
        product.setFile(productDTO.getFile().getBytes());

        // Tạo thông báo
        String message = users.getFullName() + " have successfully added product " + product.getProductName();
        notificationService.saveNotification(message, users);
        productRepository.save(product);
        return PRODUCT_CREATED_SUCCESSFULLY;
    }

    @Override
    public String updateProduct(Long productId, ProductDTO productDTO, Users users) throws Exception {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new DataNotFoundException(PRODUCT_NOT_FOUND));
        GroupProduct existingGroupProduct = groupProductRepository.findById(productDTO.getGroupProductId())
                .orElseThrow(() -> new DataNotFoundException(GROUP_PRODUCT_NOT_FOUND));
        Trademark existingTrademark = trademarkRepository.findById(productDTO.getTrademarkId())
                .orElseThrow(() -> new DataNotFoundException(TRADEMARK_NOT_FOUND));
        Location existingLocation = locationRepository.findById(productDTO.getLocationId())
                .orElseThrow(() -> new DataNotFoundException(LOCATION_NOT_FOUND));
        Properties existingProperties = propertiesRepository.findById(productDTO.getPropertiesId())
                .orElseThrow(() -> new DataNotFoundException(PROPERTIES_NOT_FOUND));
        Unit existingUnit = unitRepository.findById(productDTO.getUnitId())
                .orElseThrow(() -> new DataNotFoundException(UNIT_NOT_FOUND));
        Category existingCategory = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new DataNotFoundException(CATEGORY_NOT_FOUND));
        existingProduct.setProductName(productDTO.getProductName());
        existingProduct.setPrice(productDTO.getPrice());
        existingProduct.setCapitalPrice(productDTO.getCapitalPrice());
        existingProduct.setInventory(productDTO.getInventory());
        existingProduct.setGroupProduct(existingGroupProduct);
        existingProduct.setLocation(existingLocation);
        existingProduct.setTrademark(existingTrademark);
        existingProduct.setWeight(productDTO.getWeight());
        existingProduct.setProperties(existingProperties);
        existingProduct.setUnit(existingUnit);
        existingProduct.setDeleteProduct(productDTO.isDeleteProduct());
        existingProduct.setCategory(existingCategory);
        existingProduct.setDirectSales(productDTO.isDirectSales());
        existingProduct.setFile(productDTO.getFile().getBytes());

        String message = users.getFullName() + " have edited product " + productDTO.getProductName() + " to " + existingProduct.getProductName();
        notificationService.saveNotification(message, users);
        productRepository.save(existingProduct);
        return PRODUCT_UPDATED_SUCCESSFULLY;
    }

    @Override
    public ResponseEntity<?> findByIdProduct(Long productId) {
        return ResponseEntity.ok().body(productRepository.findById(productId));
    }

    @Override
    public Optional<Product> findByProductId(Long productId) {
        return productRepository.findById(productId);
    }

    @Override
    public String deleteProduct(Long productId, Users users) {
        Product product = productRepository.findById(productId)
                        .orElseThrow(() -> new RuntimeException(PRODUCT_NOT_FOUND));
        productRepository.deleteProductById(product.getProductId());
        String message = users.getFullName() + " have successfully deleted product " + product.getProductName();
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
            case 2 -> productRepository.searchBelowInventoryThreshold(); // Dưới định mức tồn
            case 3 -> productRepository.searchExceedingInventoryLimit(); // Vượt định mức tồn
            case 4 -> productRepository.searchStockAvailable(); // Còn hàng trong kho
            case 5 -> productRepository.searchNoInventoryAvailable(); // Hết hàng trong kho
            default -> new ArrayList<>();
        };
    }

    @Override
    public List<Map<String, Object>> searchActive(int number) {
        return switch (number) {
            case 1 -> productRepository.searchActive(); // Hàng đang kinh doanh
            case 2 -> productRepository.searchNoActive(); // Hàng ngưng kinh doanh
            case 3 -> productRepository.getAllProducts(); // Tất cả hàng
            default -> new ArrayList<>();
        };
    }

    @Override
    public List<Map<String, Object>> searchDirectSales(int number) {
        return switch (number) {
            case 1 -> productRepository.getAllProducts(); // Tất cả
            case 2 -> productRepository.searchDirectSales(); // Được bán trực tiếp
            case 3 -> productRepository.searchNoDirectSales(); // Không bán trực tiếp
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
    public Map<String, Object> getProductDetailId(Long productId) {
        return productRepository.getDetailProductId(productId);
    }

    @Override
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

    @Override
    public String saveCategory(CategoryDTO categoryDTO) {
            Category category = new Category();
            category.setCategoryName(categoryDTO.getCategoryName());
            categoryRepository.save(category);
            return CATEGORY_SAVED_SUCCESSFULLY;
    }

    @Override
    public String updateCategory(CategoryDTO categoryDTO, Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException(CATEGORY_NOT_FOUND));
        category.setCategoryName(categoryDTO.getCategoryName());
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
    public String saveGroupProduct(GroupProductDTO groupProductDTO) {
        GroupProduct groupProduct = new GroupProduct();
        groupProduct.setGroupProductName(groupProductDTO.getGroupProductName());
        groupProductRepository.save(groupProduct);
        return GROUP_PRODUCT_SAVED_SUCCESSFULLY;
    }

    @Override
    public String updateGroupProduct(GroupProductDTO groupProductDTO, Long groupProductId) {
        GroupProduct groupProduct = groupProductRepository.findById(groupProductId)
                .orElseThrow(() -> new RuntimeException(GROUP_PRODUCT_NOT_FOUND));
        groupProduct.setGroupProductName(groupProductDTO.getGroupProductName());
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
    public String saveLocation(LocationDTO locationDTO) {
        Location location = new Location();
        location.setLocationName(locationDTO.getLocationName());
        locationRepository.save(location);
        return LOCATION_SAVED_SUCCESSFULLY;
    }

    @Override
    public String updateLocation(LocationDTO locationDTO, Long locationId) {
        Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> new RuntimeException(LOCATION_NOT_FOUND));
        location.setLocationName(locationDTO.getLocationName());
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
    public String saveProperties(PropertiesDTO propertiesDTO) {
        Properties properties = new Properties();
        properties.setPropertiesName(propertiesDTO.getPropertiesName());
        propertiesRepository.save(properties);
        return PROPERTIES_SAVED_SUCCESSFULLY;
    }

    @Override
    public String updateProperties(PropertiesDTO propertiesDTO, Long propertiesId) {
        Properties properties = propertiesRepository.findById(propertiesId)
                .orElseThrow(() -> new RuntimeException(PROPERTIES_NOT_FOUND));
        properties.setPropertiesName(propertiesDTO.getPropertiesName());
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
    public String saveTrademark(TrademarkDTO trademarkDTO) {
        Trademark trademark = new Trademark();
        trademark.setTrademarkName(trademarkDTO.getTrademarkName());
        trademarkRepository.save(trademark);
        return TRADEMARK_SAVED_SUCCESSFULLY;
    }

    @Override
    public String updateTrademark(TrademarkDTO trademarkDTO, Long trademarkId) {
        Trademark trademark = trademarkRepository.findById(trademarkId)
                .orElseThrow(() -> new RuntimeException(TRADEMARK_NOT_FOUND));
        trademark.setTrademarkName(trademarkDTO.getTrademarkName());
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
    public String saveUnit(UnitDTO unitDTO) {
        Unit unit = new Unit();
        unit.setUnitName(unitDTO.getUnitName());
        unitRepository.save(unit);
        return UNIT_SAVED_SUCCESSFULLY;
    }

    @Override
    public String updateUnit(UnitDTO unitDTO, Long unitId) {
        Unit unit = unitRepository.findById(unitId)
                .orElseThrow(() -> new RuntimeException(UNIT_NOT_FOUND));
        unit.setUnitName(unitDTO.getUnitName());
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
