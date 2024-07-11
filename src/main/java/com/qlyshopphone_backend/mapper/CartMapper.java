package com.qlyshopphone_backend.mapper;

import com.qlyshopphone_backend.dto.CartDTO;
import com.qlyshopphone_backend.model.Cart;
import com.qlyshopphone_backend.model.Product;
import com.qlyshopphone_backend.model.Users;

import java.util.Base64;

public class CartMapper {
    // Chuyển đổi từ Cart sang CartDTO
    public static CartDTO toDto(Cart cart) {
        CartDTO cartDTO = new CartDTO();
        cartDTO.setCartId(cart.getCartId());
        cartDTO.setProductId(cart.getProduct().getProductId());
        cartDTO.setProductName(cart.getProduct().getProductName());
        cartDTO.setPrice(cart.getProduct().getPrice());
        cartDTO.setUserId(cart.getUser().getUserId());
        cartDTO.setQuantity(cart.getQuantity());
        cartDTO.setSold(cart.isSold());
        cartDTO.setDeleteCart(cart.isDeleteCart());
        if (cart.getProduct().getFile() != null){
            cartDTO.setFileBase64(Base64.getEncoder().encodeToString(cart.getProduct().getFile()));
        }
        return cartDTO;
    }

    // Chuyển đổi từ CartDTO sang Cart
    public static Cart toEntity(CartDTO cartDTO, Product product, Users user){
        Cart cart = new Cart();
        cart.setCartId(cartDTO.getCartId());
        cart.setProduct(product);
        cart.setUser(user);
        cart.setQuantity(cartDTO.getQuantity());
        cart.setSold(cartDTO.isSold());
        cart.setDeleteCart(cart.isDeleteCart());
        if (cartDTO.getFileBase64() != null && !cartDTO.getFileBase64().isEmpty()) {
            byte[] file = Base64.getDecoder().decode(cartDTO.getFileBase64());
            cart.getProduct().setFile(file);
        }
        return cart;
    }
}
