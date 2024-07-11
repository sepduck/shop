package com.qlyshopphone_backend.service;

import com.qlyshopphone_backend.dto.CartDTO;
import com.qlyshopphone_backend.dto.PurchaseDTO;
import com.qlyshopphone_backend.model.Cart;
import com.qlyshopphone_backend.model.Users;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface CartService {
    void addToCart(HttpSession session, CartDTO cartDTO);

    ResponseEntity<?> saveCart(CartDTO cartDTO);

    ResponseEntity<?> deleteCart(int cartId, Users users);

    List<CartDTO> getCart(HttpSession session);

    Optional<Cart> findCartById(int cartId);
}
