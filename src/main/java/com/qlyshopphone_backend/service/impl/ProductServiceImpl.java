package com.qlyshopphone_backend.service.impl;

import com.qlyshopphone_backend.dto.*;
import com.qlyshopphone_backend.exceptions.DataNotFoundException;
import com.qlyshopphone_backend.model.*;
import com.qlyshopphone_backend.repository.*;
import com.qlyshopphone_backend.service.NotificationService;
import com.qlyshopphone_backend.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ProductServiceImpl extends BaseReponse implements ProductService {
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
    public Product saveProduct(ProductDTO productDTO, Users users) throws Exception {
        GroupProduct existingGroupProduct = groupProductRepository.findById(productDTO.getGroupProductId())
                .orElseThrow(() -> new DataNotFoundException("Can't find product with id: " + productDTO.getGroupProductId()));
        Trademark existingTrademark = trademarkRepository.findById(productDTO.getTrademarkId())
                .orElseThrow(() -> new DataNotFoundException("Can't find trademark with id: " + productDTO.getTrademarkId()));
        Location existingLocation = locationRepository.findById(productDTO.getLocationId())
                .orElseThrow(() -> new DataNotFoundException("Can't find product with id: " + productDTO.getLocationId()));
        Properties existingProperties = propertiesRepository.findById(productDTO.getPropertiesId())
                .orElseThrow(() -> new DataNotFoundException("Can't find product with id: " + productDTO.getPropertiesId()));
        Unit existingUnit = unitRepository.findById(productDTO.getUnitId())
                .orElseThrow(() -> new DataNotFoundException("Can't find unit with id: " + productDTO.getUnitId()));
        Category existingCategory = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new DataNotFoundException("Can't find unit with id: " + productDTO.getCategoryId()));


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
        String message = users.getFullName() + " đã thêm sản phẩm " + product.getProductName();
        notificationService.saveNotification(message, users);
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Long productId, ProductDTO productDTO, Users users) throws Exception {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new DataNotFoundException("Can't find product with id: " + productId));
        GroupProduct existingGroupProduct = groupProductRepository.findById(productDTO.getGroupProductId())
                .orElseThrow(() -> new DataNotFoundException("Can't find product with id: " + productDTO.getGroupProductId()));
        Trademark existingTrademark = trademarkRepository.findById(productDTO.getTrademarkId())
                .orElseThrow(() -> new DataNotFoundException("Can't find trademark with id: " + productDTO.getTrademarkId()));
        Location existingLocation = locationRepository.findById(productDTO.getLocationId())
                .orElseThrow(() -> new DataNotFoundException("Can't find product with id: " + productDTO.getLocationId()));
        Properties existingProperties = propertiesRepository.findById(productDTO.getPropertiesId())
                .orElseThrow(() -> new DataNotFoundException("Can't find product with id: " + productDTO.getPropertiesId()));
        Unit existingUnit = unitRepository.findById(productDTO.getUnitId())
                .orElseThrow(() -> new DataNotFoundException("Can't find unit with id: " + productDTO.getUnitId()));
        Category existingCategory = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new DataNotFoundException("Can't find unit with id: " + productDTO.getCategoryId()));
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

        String message = users.getFullName() + " đã sửa sản phẩm " + productDTO.getProductName() + " thành " + existingProduct.getProductName();
        notificationService.saveNotification(message, users);
        return productRepository.save(existingProduct);
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
    public ResponseEntity<?> deleteProduct(Long productId, Users users) {
        productRepository.deleteProductById(productId);
        String message = users.getFullName() + " đã xóa sản phẩm có ID: HH00" + productId;
        notificationService.saveNotification(message, users);
        return getResponseEntity("Product with id: " + productId + " deleted successfully");
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
    public ResponseEntity<?> saveCategory(CategoryDTO categoryDTO) {
        try {
            Category category = new Category();
            category.setCategoryName(categoryDTO.getCategoryName());
            categoryRepository.save(category);
            return getResponseEntity("Category saved successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Override
    public String updateCategory(CategoryDTO categoryDTO, Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        category.setCategoryName(categoryDTO.getCategoryName());
        categoryRepository.save(category);
        return "Category updated successfully";
    }

    @Override
    public String deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        categoryRepository.deleteById(category.getCategoryId());
        return "Category deleted successfully";

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
        return "GroupProduct saved successfully";
    }

    @Override
    public String updateGroupProduct(GroupProductDTO groupProductDTO, Long groupProductId) {
        GroupProduct groupProduct = groupProductRepository.findById(groupProductId)
                .orElseThrow(() -> new RuntimeException("Group product not found"));
        groupProduct.setGroupProductName(groupProductDTO.getGroupProductName());
        groupProductRepository.save(groupProduct);
        return "GroupProduct updated successfully";
    }

    @Override
    public String deleteGroupProduct(Long groupProductId) {
        GroupProduct groupProduct = groupProductRepository.findById(groupProductId)
                .orElseThrow(() -> new RuntimeException("Group product not found"));
        groupProductRepository.deleteById(groupProduct.getGroupProductId());
        return "Group product deleted successfully";
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
        return "Location saved successfully";
    }

    @Override
    public String updateLocation(LocationDTO locationDTO, Long locationId) {
        Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> new RuntimeException("Location not found"));
        location.setLocationName(locationDTO.getLocationName());
        locationRepository.save(location);
        return "Location updated successfully";
    }

    @Override
    public String deleteLocation(Long locationId) {
        Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> new RuntimeException("Location not found"));
        locationRepository.deleteById(location.getLocationId());
        return "Location deleted successfully";
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
        return "Properties saved successfully";
    }

    @Override
    public String updateProperties(PropertiesDTO propertiesDTO, Long propertiesId) {
        Properties properties = propertiesRepository.findById(propertiesId)
                .orElseThrow(() -> new RuntimeException("Properties not found"));
        properties.setPropertiesName(propertiesDTO.getPropertiesName());
        propertiesRepository.save(properties);
        return "Properties updated successfully";
    }

    @Override
    public String deleteProperties(Long propertiesId) {
        Properties properties = propertiesRepository.findById(propertiesId)
                .orElseThrow(() -> new RuntimeException("Properties not found"));
        propertiesRepository.deleteById(properties.getPropertiesId());
        return "Properties deleted successfully";
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
        return "Trademark saved successfully";
    }

    @Override
    public String updateTrademark(TrademarkDTO trademarkDTO, Long trademarkId) {
        Trademark trademark = trademarkRepository.findById(trademarkId)
                .orElseThrow(() -> new RuntimeException("Trademark not found"));
        trademark.setTrademarkName(trademarkDTO.getTrademarkName());
        trademarkRepository.save(trademark);
        return "Trademark update successful";
    }

    @Override
    public String deleteTrademark(Long trademarkId) {
        Trademark trademark = trademarkRepository.findById(trademarkId)
                .orElseThrow(() -> new RuntimeException("Trademark not found"));
        trademarkRepository.deleteById(trademark.getTrademarkId());
        return "Trademark delete successful";
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
        return "Unit saved successfully";
    }

    @Override
    public String updateUnit(UnitDTO unitDTO, Long unitId) {
        Unit unit = unitRepository.findById(unitId)
                .orElseThrow(() -> new RuntimeException("Unit not found"));
        unit.setUnitName(unitDTO.getUnitName());
        unitRepository.save(unit);
        return "Unit updated successfully";
    }

    @Override
    public String deleteUnit(Long unitId) {
        Unit unit = unitRepository.findById(unitId)
                .orElseThrow(() -> new RuntimeException("Unit not found"));
        unitRepository.deleteById(unit.getUnitId());
        return "Unit deleted successfully";
    }
}
