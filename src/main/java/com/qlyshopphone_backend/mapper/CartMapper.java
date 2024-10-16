package com.qlyshopphone_backend.mapper;

import com.qlyshopphone_backend.dto.request.CartRequest;
import com.qlyshopphone_backend.model.Cart;
import com.qlyshopphone_backend.model.Product;
import com.qlyshopphone_backend.model.Users;

import java.util.Base64;

public class CartMapper {
    public static CartRequest toDto(Cart cart) {
        CartRequest cartRequest = new CartRequest();
        cartRequest.setCartId(cart.getCartId());
        cartRequest.setProductId(cart.getProduct().getProductId());
        cartRequest.setProductName(cart.getProduct().getProductName());
        cartRequest.setPrice(cart.getProduct().getPrice());
        cartRequest.setUserId(cart.getUser().getUserId());
        cartRequest.setQuantity(cart.getQuantity());
        cartRequest.setSold(cart.isSold());
        cartRequest.setDeleteCart(cart.isDeleteCart());
        if (cart.getProduct().getFile() != null){
            cartRequest.setFileBase64(Base64.getEncoder().encodeToString(cart.getProduct().getFile()));
        }
        return cartRequest;
    }

    // Chuyển đổi từ CartDTO sang Cart
    public static Cart toEntity(CartRequest cartRequest, Product product, Users user){
        Cart cart = new Cart();
        cart.setCartId(cartRequest.getCartId());
        cart.setProduct(product);
        cart.setUser(user);
        cart.setQuantity(cartRequest.getQuantity());
        cart.setSold(cartRequest.isSold());
        cart.setDeleteCart(cart.isDeleteCart());
        if (cartRequest.getFileBase64() != null && !cartRequest.getFileBase64().isEmpty()) {
            byte[] file = Base64.getDecoder().decode(cartRequest.getFileBase64());
            cart.getProduct().setFile(file);
        }
        return cart;
    }
}
