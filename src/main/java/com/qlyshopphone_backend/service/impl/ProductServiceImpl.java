package com.qlyshopphone_backend.service.impl;

import com.qlyshopphone_backend.dao.ProductDAO;
import com.qlyshopphone_backend.dto.ProductDTO;
import com.qlyshopphone_backend.exceptions.DataNotFoundException;
import com.qlyshopphone_backend.model.*;
import com.qlyshopphone_backend.repository.*;
import com.qlyshopphone_backend.service.NotificationService;
import com.qlyshopphone_backend.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
    private final ProductDAO productDAO;
    private final NotificationService notificationService;

    @Override
    public ResponseEntity<?> getAllProducts() {
        return getResponseEntity(productRepository.getAllProducts());
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
    public Product updateProduct(int productId, ProductDTO productDTO, Users users) throws Exception {
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
    public ResponseEntity<?> findByIdProduct(int productId) {
        return ResponseEntity.ok().body(productRepository.findById(productId));
    }

    @Override
    public Optional<Product> findByProductId(int productId) {
        return productRepository.findById(productId);
    }

    @Override
    public ResponseEntity<?> deleteProduct(int productId, Users users) {
        try {
            productDAO.deleteProduct(productId);
            String message = users.getFullName() + " đã xóa sản phẩm có ID: HH00" + productId;
            notificationService.saveNotification(message, users);
            return getResponseEntity("Product with id: " + productId + " deleted successfully");
        } catch (Exception e) {
            return getResponseEntity("Can't delete product with id: " + productId);
        }
    }

    @Override
    public ResponseEntity<?> searchAllByProductName(String productName) {
        List<Map<String, Object>> allProducts = productRepository.searchAllByProductName(productName);
        if (allProducts.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No products found");
        } else {
            return getResponseEntity(allProducts);
        }
    }

    @Override
    public ResponseEntity<?> searchAllByProductId(int productId) {
        return getResponseEntity(productRepository.searchAllByProductId(productId));
    }

    @Override
    public ResponseEntity<?> searchGroupProductId(int groupProductId) {
        return getResponseEntity(productRepository.searchGroupProductId(groupProductId));
    }

    @Override
    public ResponseEntity<?> searchInventory(int number) {
        switch (number) {
            case 1:
                return getResponseEntity(productRepository.getAllProducts());
            case 2:
                // Dưới định mức tồn
                return getResponseEntity(productRepository.searchBelowInventoryThreshold());
            case 3:
                // Vượt định mức tồn
                return getResponseEntity(productRepository.searchExceedingInventoryLimit());
            case 4:
                // Còn hàng trong kho
                return getResponseEntity(productRepository.searchStockAvailable());
            case 5:
                // Hết hàng trong kho
                return getResponseEntity(productRepository.searchNoInventoryAvailable());
            default:
                return getResponseEntity("Search Inventory failed");
        }
    }

    @Override
    public ResponseEntity<?> searchActive(int number) {
        switch (number) {
            case 1:
                // Hàng đang kinh doanh
                return getResponseEntity(productRepository.searchActive());
            case 2:
                // Hàng ngưng kinh doanh
                return getResponseEntity(productRepository.searchNoActive());
            case 3:
                // Tất cả hàng
                return getResponseEntity(productRepository.getAllProducts());
            default:
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid active number");
        }
    }

    @Override
    public ResponseEntity<?> searchDirectSales(int number) {
        switch (number) {
            case 1:
                // Tất cả
                return getResponseEntity(productRepository.getAllProducts());
            case 2:
                // Được bán trực tiếp
                return getResponseEntity(productRepository.searchDirectSales());
            case 3:
                // Không bán trực tiếp
                return getResponseEntity(productRepository.searchNoDirectSales());
            default:
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid active number");
        }
    }

    @Override
    public ResponseEntity<?> searchByLocationId(int locationId) {
        return getResponseEntity(productRepository.searchByLocationId(locationId));
    }

    @Override
    public ResponseEntity<?> searchCategory(int number) {
        switch (number) {
            case 1:
                return getResponseEntity(productRepository.getAllProducts());
            case 2:
                return getResponseEntity(productRepository.searchCategory1());
            case 3:
                return getResponseEntity(productRepository.searchCategory2());
            case 4:
                return getResponseEntity(productRepository.searchCategory3());
            default:
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid category number");
        }
    }

    @Override
    public Map<String, Object> getProductDetailId(int productId) {
        return productRepository.getDetailProductId(productId);
    }
}
