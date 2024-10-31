package com.qlyshopphone_backend.service;

import com.qlyshopphone_backend.dto.request.AddToCartRequest;
import com.qlyshopphone_backend.dto.request.CartIdsRequest;
import com.qlyshopphone_backend.repository.projection.CartProjection;

import java.util.List;

public interface CartService {
    boolean addProductToCart(AddToCartRequest request);

    List<CartProjection> getUserActiveCarts();

    boolean removeProductVariantFromCart(CartIdsRequest request);


}
