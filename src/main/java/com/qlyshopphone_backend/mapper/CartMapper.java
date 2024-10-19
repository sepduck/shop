package com.qlyshopphone_backend.mapper;

import com.qlyshopphone_backend.dto.request.CartRequest;
import com.qlyshopphone_backend.model.Carts;
import com.qlyshopphone_backend.model.Products;
import com.qlyshopphone_backend.model.Users;

public class CartMapper {
    public static CartRequest toDto(Carts carts) {
        CartRequest cartRequest = new CartRequest();
        cartRequest.setCartId(carts.getId());
        cartRequest.setProductId(carts.getProducts().getId());
        cartRequest.setName(carts.getProducts().getName());
//        cartRequest.setPrice(cart.getProduct().getPrice());
        cartRequest.setUserId(carts.getUsers().getId());
        cartRequest.setQuantity(carts.getQuantity());
        cartRequest.setSold(carts.isSold());
        cartRequest.setDeleteCart(carts.isDeleteCart());

        return cartRequest;
    }

    // Chuyển đổi từ CartDTO sang Cart
    public static Carts toEntity(CartRequest cartRequest, Products products, Users users){
        Carts carts = new Carts();
        carts.setId(cartRequest.getCartId());
        carts.setProducts(products);
        carts.setUsers(users);
        carts.setQuantity(cartRequest.getQuantity());
        carts.setSold(cartRequest.isSold());
        carts.setDeleteCart(carts.isDeleteCart());

        return carts;
    }
}
