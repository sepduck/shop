package com.qlyshopphone_backend.service.impl;

import static com.qlyshopphone_backend.constant.ErrorMessage.*;

import com.qlyshopphone_backend.dto.request.*;
import com.qlyshopphone_backend.dto.response.ProductAttributeResponse;
import com.qlyshopphone_backend.dto.response.ProductResponse;
import com.qlyshopphone_backend.exceptions.ApiRequestException;
import com.qlyshopphone_backend.exceptions.DataNotFoundException;
import com.qlyshopphone_backend.mapper.BasicMapper;
import com.qlyshopphone_backend.model.*;
import com.qlyshopphone_backend.model.enums.Status;
import com.qlyshopphone_backend.repository.*;
import com.qlyshopphone_backend.service.AuthenticationService;
import com.qlyshopphone_backend.service.NotificationService;
import com.qlyshopphone_backend.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;


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
    private final BasicMapper basicMapper;

    @Override
    public List<ProductResponse> getAllProducts() {
        return productRepository.getAllProducts(Status.ACTIVE);
    }

    @Override
    public String saveProduct(ProductRequest productRequest) throws Exception {
        Users users = authenticationService.getAuthenticatedUser();
        Products products = new Products();
        updateProductProperties(products, productRequest);

        sendProductNotification(users, "successfully added", products.getName(), null);
        productRepository.save(products);
        return PRODUCT_CREATED_SUCCESSFULLY;
    }

    @Override
    public String updateProduct(Long productId, ProductRequest productRequest) throws Exception {
        Users users = authenticationService.getAuthenticatedUser();
        Products existingProducts = productRepository.findById(productId)
                .orElseThrow(() -> new DataNotFoundException(PRODUCT_NOT_FOUND));

        updateProductProperties(existingProducts, productRequest);

        sendProductNotification(users, "edited", productRequest.getName(), existingProducts.getName());
        productRepository.save(existingProducts);
        return PRODUCT_UPDATED_SUCCESSFULLY;
    }


    private void updateProductProperties(Products products, ProductRequest productRequest) throws IOException {
        GroupProducts existingGroupProducts = groupProductRepository.findById(productRequest.getGroupProductId())
                .orElseThrow(() -> new RuntimeException(GROUP_PRODUCT_NOT_FOUND));
        Trademarks existingTrademarks = trademarkRepository.findById(productRequest.getTrademarkId())
                .orElseThrow(() -> new RuntimeException(TRADEMARK_NOT_FOUND));
        Locations existingLocations = locationRepository.findById(productRequest.getLocationId())
                .orElseThrow(() -> new RuntimeException(LOCATION_NOT_FOUND));
        Properties existingProperties = propertiesRepository.findById(productRequest.getPropertiesId())
                .orElseThrow(() -> new RuntimeException(PROPERTIES_NOT_FOUND));
        Units existingUnits = unitRepository.findById(productRequest.getUnitId())
                .orElseThrow(() -> new RuntimeException(UNIT_NOT_FOUND));
        Categories existingCategories = categoryRepository.findById(productRequest.getCategoryId())
                .orElseThrow(() -> new RuntimeException(CATEGORY_NOT_FOUND));

        products.setName(productRequest.getName());
        products.setPrice(productRequest.getPrice());
        products.setCapitalPrice(productRequest.getCapitalPrice());
        products.setInventory(productRequest.getInventory());
        products.setGroupProducts(existingGroupProducts);
        products.setLocations(existingLocations);
        products.setTrademarks(existingTrademarks);
        products.setWeight(productRequest.getWeight());
        products.setProperties(existingProperties);
        products.setUnits(existingUnits);
        products.setStatus(Status.ACTIVE);
        products.setCategories(existingCategories);
        products.setDirectSales(productRequest.isDirectSales());
    }

    private void sendProductNotification(Users users, String action, String productName, String newProductName) {
        String message = String.format("%s have %s product %s%s", users.getFirstName(), action, productName,
                newProductName != null ? " to " + newProductName : "");
        notificationService.saveNotification(message, users);
    }

    @Override
    public String deleteProduct(Long productId) {
        Users users = authenticationService.getAuthenticatedUser();
        Products products = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException(PRODUCT_NOT_FOUND));
        productRepository.deleteProductById(products.getId());
        String message = users.getFirstName() + " have successfully deleted product " + products.getName();
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
        return productRepository.searchAllByProductId(groupProductId);
    }

    @Override
    public List<Map<String, Object>> searchInventory(int number) {
        return switch (number) {
//            case 1 -> productRepository.getAllProducts(Status.ACTIVE);
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
//            case 3 -> productRepository.getAllProducts();
            default -> new ArrayList<>();
        };
    }

    @Override
    public List<Map<String, Object>> searchDirectSales(int number) {
        return switch (number) {
//            case 1 -> productRepository.getAllProducts();
            case 2 -> productRepository.searchDirectSales();
            case 3 -> productRepository.searchNoDirectSales();
            default -> new ArrayList<>();
        };
    }

    @Override
    public List<Map<String, Object>> searchByLocationId(Long locationId) {
        return productRepository.searchAllByProductId(locationId);
    }

    @Override
    public List<Map<String, Object>> searchCategory(int number) {
        return switch (number) {
//            case 1 -> productRepository.getAllProducts();
            case 2 -> productRepository.searchCategory1();
            case 3 -> productRepository.searchCategory2();
            case 4 -> productRepository.searchCategory3();
            default -> new ArrayList<>();
        };
    }


    @Override
    public List<ProductAttributeResponse> getAllGroupProduct() {
        List<GroupProducts> list = groupProductRepository.findAll();
        return basicMapper.convertToResponseList(list, ProductAttributeResponse.class);
    }

    @Override
    public ProductAttributeResponse saveGroupProduct(ProductAttributeRequest request) {
        GroupProducts groupProducts = new GroupProducts();
        groupProducts.setName(request.getName());
        groupProductRepository.save(groupProducts);
        return basicMapper.convertToResponse(groupProducts, ProductAttributeResponse.class);
    }

    @Override
    public ProductAttributeResponse updateGroupProduct(ProductAttributeRequest request, Long groupProductId) {
        return updateAttribute(
                groupProductId,
                request,
                groupProductRepository::findById,
                groupProductRepository::save,
                GroupProducts::getName,
                GroupProducts::setName,
                GROUP_PRODUCT_NOT_FOUND
        );
    }

    @Override
    public boolean deleteGroupProduct(Long groupProductId) {
        if (!groupProductRepository.existsById(groupProductId)) {
            throw new ApiRequestException(GROUP_PRODUCT_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        groupProductRepository.deleteById(groupProductId);
        return true;
    }

    @Override
    public List<ProductAttributeResponse> getAllLocation() {
        List<Locations> list = locationRepository.findAll();
        return basicMapper.convertToResponseList(list, ProductAttributeResponse.class);
    }

    @Override
    public ProductAttributeResponse createLocation(ProductAttributeRequest request) {
        Locations locations = new Locations();
        locations.setName(request.getName());
        locationRepository.save(locations);
        return basicMapper.convertToResponse(locations, ProductAttributeResponse.class);
    }

    @Override
    public ProductAttributeResponse updateLocation(ProductAttributeRequest request, Long locationId) {
        return updateAttribute(
                locationId,
                request,
                locationRepository::findById,
                locationRepository::save,
                Locations::getName,
                Locations::setName,
                LOCATION_NOT_FOUND
        );
    }

    @Override
    public boolean deleteLocation(Long locationId) {
        if (!locationRepository.existsById(locationId)) {
            throw new ApiRequestException(LOCATION_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        locationRepository.deleteById(locationId);
        return true;
    }

    @Override
    public List<ProductAttributeResponse> getAllProperties() {
        List<Properties> list = propertiesRepository.findAll();
        return basicMapper.convertToResponseList(list, ProductAttributeResponse.class);
    }

    @Override
    public ProductAttributeResponse saveProperties(ProductAttributeRequest request) {
        Properties properties = new Properties();
        properties.setName(request.getName());
        propertiesRepository.save(properties);
        return basicMapper.convertToResponse(properties, ProductAttributeResponse.class);
    }

    @Override
    public ProductAttributeResponse updateProperties(ProductAttributeRequest request, Long propertiesId) {
        return updateAttribute(
                propertiesId,
                request,
                propertiesRepository::findById,
                propertiesRepository::save,
                Properties::getName,
                Properties::setName,
                PROPERTIES_NOT_FOUND
        );
    }

    @Override
    public boolean deleteProperties(Long propertiesId) {
        if (!propertiesRepository.existsById(propertiesId)) {
            throw new ApiRequestException(PROPERTIES_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        propertiesRepository.deleteById(propertiesId);
        return true;
    }

    @Override
    public List<ProductAttributeResponse> getAllTrademarks() {
        List<Trademarks> list = trademarkRepository.findAll();
        return basicMapper.convertToResponseList(list, ProductAttributeResponse.class);
    }

    @Override
    public ProductAttributeResponse saveTrademark(ProductAttributeRequest request) {
        Trademarks trademarks = new Trademarks();
        trademarks.setName(request.getName());
        trademarkRepository.save(trademarks);
        return basicMapper.convertToResponse(trademarks, ProductAttributeResponse.class);
    }

    @Override
    public ProductAttributeResponse updateTrademark(ProductAttributeRequest request, Long trademarkId) {
        return updateAttribute(
                trademarkId,
                request,
                trademarkRepository::findById,
                trademarkRepository::save,
                Trademarks::getName,
                Trademarks::setName,
                TRADEMARK_NOT_FOUND
        );
    }

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

    @Override
    public ProductAttributeResponse createUnit(ProductAttributeRequest request) {
        Units units = new Units();
        units.setName(request.getName());
        unitRepository.save(units);
        return basicMapper.convertToResponse(units, ProductAttributeResponse.class);
    }

    @Override
    public ProductAttributeResponse updateUnit(ProductAttributeRequest request, Long unitId) {
        return updateAttribute(
                unitId,
                request,
                unitRepository::findById,
                unitRepository::save,
                Units::getName,
                Units::setName,
                UNIT_NOT_FOUND
        );
    }

    @Override
    public boolean deleteUnit(Long unitId) {
        if (!unitRepository.existsById(unitId)) {
            throw new ApiRequestException(UNIT_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        unitRepository.deleteById(unitId);
        return true;
    }

    @Override
    public List<ProductAttributeResponse> getAllCategories() {
        List<Categories> list = categoryRepository.findAll();
        return basicMapper.convertToResponseList(list, ProductAttributeResponse.class);
    }

    @Override
    public ProductAttributeResponse createCategory(ProductAttributeRequest request) {
        Categories categories = new Categories();
        categories.setName(request.getName());
        categoryRepository.save(categories);
        return basicMapper.convertToResponse(categories, ProductAttributeResponse.class);
    }

    @Override
    public ProductAttributeResponse updateCategory(ProductAttributeRequest request, Long categoryId) {
        return updateAttribute(
                categoryId,
                request,
                categoryRepository::findById,
                categoryRepository::save,
                Categories::getName,
                Categories::setName,
                CATEGORY_NOT_FOUND
        );
    }

    @Override
    public boolean deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new ApiRequestException(CATEGORY_NOT_FOUND, HttpStatus.BAD_REQUEST);
        }
        categoryRepository.deleteById(id);
        return true;
    }

    private <T> ProductAttributeResponse updateAttribute(
            Long id,
            ProductAttributeRequest request,
            Function<Long, Optional<T>> findByIdFunction,
            Consumer<T> saveFunction,
            Function<T, String> getNameFunction,
            BiConsumer<T, String> setNameFunction,
            String notFoundMessage) {

        return findByIdFunction.apply(id)
                .map(attribute -> {
                    // Kiểm tra xem tên mới có khác không
                    if (!getNameFunction.apply(attribute).equals(request.getName())) {
                        setNameFunction.accept(attribute, request.getName());
                        saveFunction.accept(attribute); // Lưu lại thực thể đã cập nhật
                    }
                    return basicMapper.convertToResponse(attribute, ProductAttributeResponse.class);
                })
                .orElseThrow(() -> new ApiRequestException(notFoundMessage, HttpStatus.NOT_FOUND));
    }


}
