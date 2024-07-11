package com.qlyshopphone_backend.service;

import com.qlyshopphone_backend.dto.ProductDTO;
import com.qlyshopphone_backend.model.Product;
import com.qlyshopphone_backend.model.Users;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.Optional;

public interface ProductService {
    ResponseEntity<?> getAllProducts();

    void createProduct(Product product);

    Product saveProduct(ProductDTO productDTO, Users users) throws Exception;

    Product updateProduct(int productId, ProductDTO productDTO, Users users) throws Exception;

    ResponseEntity<?> findByIdProduct(int productId);

    Optional<Product> findByProductId(int productId);

    ResponseEntity<?> deleteProduct(int productId, Users users);

    ResponseEntity<?> searchAllByProductName(String productName);

    ResponseEntity<?> searchAllByProductId(int productId);

    ResponseEntity<?> searchGroupProductId( int groupProductId);

    ResponseEntity<?> searchInventory(int number);

    ResponseEntity<?> searchActive(int number);

    ResponseEntity<?> searchDirectSales(int number);

    ResponseEntity<?> searchByLocationId( int locationId);

    ResponseEntity<?> searchCategory(int number);

    Map<String, Object> getProductDetailId(int productId);


}
