package com.qlyshopphone_backend.service.impl;

import com.qlyshopphone_backend.dao.CartDAO;
import com.qlyshopphone_backend.dto.CartDTO;
import com.qlyshopphone_backend.dto.PurchaseDTO;
import com.qlyshopphone_backend.mapper.CartMapper;
import com.qlyshopphone_backend.model.*;
import com.qlyshopphone_backend.repository.*;
import com.qlyshopphone_backend.service.CartService;
import com.qlyshopphone_backend.service.NotificationService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl extends BaseReponse implements CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CartDAO cartDAO;
    private final NotificationService notificationService;

    private static final String CART_SESSION_KEY = "cart";

    @Override
    public void addToCart(HttpSession session, CartDTO cartDTO) {
        List<CartDTO> cart = getCart(session);
        cart.add(cartDTO);
        session.setAttribute(CART_SESSION_KEY, cart);
    }

    @Override
    public ResponseEntity<?> saveCart(CartDTO cartDTO) {
        Optional<Product> productOptional = productRepository.findById(cartDTO.getProductId());
        Optional<Users> usersOptional = userRepository.findById(cartDTO.getUserId());
        if (!productOptional.isPresent() || !usersOptional.isPresent()) { // Sửa || thành &&
            return ResponseEntity.badRequest().body("Invalid product or user ID");
        }
        Cart cart = CartMapper.toEntity(cartDTO, productOptional.get(), usersOptional.get());
        return ResponseEntity.ok(cartRepository.save(cart));
    }

    @Override
    public ResponseEntity<?> deleteCart(int cartId, Users users) {
        try {
            cartDAO.deleteCart(cartId);
            String message = users.getUsername() + " đã xóa sản phẩm trong giỏ hàng có ID: " + cartId;
            notificationService.saveNotification(message, users);
            return getResponseEntity("Delete cart success");
        }catch (Exception e) {
            return getResponseEntity("Delete cart failed");
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<CartDTO> getCart(HttpSession session) {
        List<CartDTO> cart = (List<CartDTO>) session.getAttribute(CART_SESSION_KEY);
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute(CART_SESSION_KEY, cart);
        }
        return cart;
    }

    @Override
    public Optional<Cart> findCartById(int cartId) {
        return cartRepository.findById(cartId);
    }
}
