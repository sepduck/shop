package com.qlyshopphone_backend.controller.rest;

import com.qlyshopphone_backend.dto.CartDTO;
import com.qlyshopphone_backend.mapper.CartMapper;
import com.qlyshopphone_backend.model.*;
import com.qlyshopphone_backend.service.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping()
@RequiredArgsConstructor
public class RestCartController {
    private static final Logger log = LoggerFactory.getLogger(RestCartController.class);
    private final UserService userService;
    private final ProductService productService;
    private final CartService cartService;
    private final UserStatisticsService userStatisticsService;
    private final CustomerInfoService customerInfoService;
    private final PurchaseService purchaseService;
    private final NotificationService notificationService;

    @PostMapping("/cart/add/{productId}")
    public ResponseEntity<?> addCartItem(@PathVariable("productId") int productId, Principal principal) {
        Users users = userService.findByUsername(principal.getName());
        Product product = productService.findByProductId(productId).
                orElseThrow(() -> new RuntimeException("Product not found"));
        boolean productExistsInCart = false;
        // Kiểm tra nếu sản phẩm đã tồn tại trong giỏ hàng
        for (Cart cartItem : users.getCart()) {
            if (cartItem.getProduct().getProductId() == productId && !cartItem.isSold()) {
                cartItem.setQuantity(cartItem.getQuantity() + 1);
                productExistsInCart = true;
                break;
            }
        }
        // Nếu sản phẩm chưa tồn tại trong giỏ hàng, thêm sản phẩm mới vào giỏ hàng
        if (!productExistsInCart) {
            Cart cart = new Cart();
            cart.setProduct(product);
            cart.setQuantity(1);
            cart.setUser(users);
            users.getCart().add(cart);
        }
        userService.saveUser(users);
        return ResponseEntity.ok().body("Product added successfully");
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ACCOUNTANT', 'ROLE_USER')")
    @GetMapping("/list-cart")
    public ResponseEntity<?> viewCart(Principal principal) {
        // Lấy thông tin người dùng từ userService
        Users user = userService.findByUsername(principal.getName());

        // Kiểm tra nếu không tìm thấy người dùng
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Lấy danh sách giỏ hàng của người dùng và lọc các giỏ hàng chưa bán
        List<CartDTO> cartDTOS = user.getCart()
                .stream()
                .filter(cart -> !cart.isSold() && !cart.isDeleteCart()) // Lọc các giỏ hàng chưa bán
                .sorted(Comparator.comparing(Cart::getCartId).reversed()) // Sắp xếp theo ID giảm dần
                .map(CartMapper::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(cartDTOS);
    }

    @DeleteMapping("/delete-cart/{cartId}")
    public ResponseEntity<?> deleteCartItem(@PathVariable("cartId") int cartId,
                                            Principal principal) {
        Users user = userService.findByUsername(principal.getName());
        return cartService.deleteCart(cartId, user);
    }

    @PostMapping("/cart/sell/{cardId}/{customerInfoId}")
    public ResponseEntity<?> sellCartItem(@PathVariable("cardId") int cardId,
                                          @PathVariable("customerInfoId") int customerInfoId) {
        // Tìm kiếm giỏ hàng theo id
        Cart cart = cartService.findCartById(cardId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        // Lấy tên người dùng từ Authentication context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Users users = userService.findByUsername(username);

        // Kiểm tra sản phẩm trong giỏ hàng đã được bán chưa
        if (cart.isSold()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product already sold");
        }

        Product product = cart.getProduct();

        // Kiểm tra tồn kho và cập nhật số lượng tồn kho
        if (product.getInventory() > 0) {
            product.setInventory(product.getInventory() - cart.getQuantity());
            productService.createProduct(product);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product out of stock");
        }

        // Đánh dấu giỏ hàng đã được bán
        cart.setSold(true);
        cartService.saveCart(CartMapper.toDto(cart));

        // Tìm kiếm thông tin khách hàng theo customerInfoId
        CustomerInfo customerInfo = customerInfoService.findById(customerInfoId);
        // Tạo đối tượng Purchase và thiết lập các thuộc tính
        Purchase purchase = new Purchase();
        purchase.setUser(users);
        purchase.setCustomerInfo(customerInfo);
        purchase.setTotalAmount(cart.getQuantity());
        BigDecimal totalPrice = product.getPrice().multiply(BigDecimal.valueOf(cart.getQuantity()));
        purchase.setTotalPrice(totalPrice); // Tính tổng tiền từ số lượng và giá sản phẩm
        purchase.setPurchaseDate(LocalDateTime.now());

        // Lưu giao dịch mua hàng
        purchaseService.createPurchase(purchase);

        Optional<UserStatistics> userStatisticsOptional = userStatisticsService.findUserStatisticsByUserId(users.getUserId());
        UserStatistics userStatistics;
        if (userStatisticsOptional.isPresent()) {
            userStatistics = userStatisticsOptional.get();
        } else {
            userStatistics = new UserStatistics();
            userStatistics.setUser(users);
        }
        BigDecimal currentTotalAmountPaid = userStatistics.getTotalAmountPaid() != null ? userStatistics.getTotalAmountPaid() : BigDecimal.ZERO;
        userStatistics.setTotalAmountPaid(currentTotalAmountPaid.add(totalPrice));

        int currentTotalItemsBought = userStatistics.getTotalItemBought() != null ? userStatistics.getTotalItemBought() : 0;
        userStatistics.setTotalItemBought(currentTotalItemsBought + purchase.getTotalAmount());

        userStatisticsService.saveUserStatistics(userStatistics);

        String message = users.getFullName() + " đã đặt sản phẩm " + cart.getProduct().getProductName();
        notificationService.saveNotification(message, users);
        return ResponseEntity.ok().body("Cart sold successfully");
    }

//     Endpoint để bán nhiều sản phẩm từ giỏ hàng
    @PostMapping("/cart/sells/{customerInfoId}")
    public ResponseEntity<?> sellCart(@RequestBody List<Integer> cardsId,
                                      @PathVariable("customerInfoId") int customerInfoId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Users users = userService.findByUsername(username);
        BigDecimal totalPrice = BigDecimal.ZERO;
        int totalItems = 0;

        // Tìm kiếm thông tin khách hàng theo customerInfoId
        CustomerInfo customerInfo = customerInfoService.findById(customerInfoId);

        for (Integer cardId : cardsId) {
            Optional<Cart> optionalCart = cartService.findCartById(cardId);
            if (!optionalCart.isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cart not found: " + cardId);
            }
            Cart cart = optionalCart.get();
            if (cart.isSold()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product already sold: " + cardId);
            }

            Product product = cart.getProduct();
            if (product.getInventory() >= cart.getQuantity()) {
                product.setInventory(product.getInventory() - cart.getQuantity());
                productService.createProduct(product);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product out of stock: " + cardId);
            }

            cart.setSold(true);
            cart.setUser(users);
            cartService.saveCart(CartMapper.toDto(cart));

            totalPrice = totalPrice.add(product.getPrice().multiply(BigDecimal.valueOf(cart.getQuantity())));
            totalItems += cart.getQuantity();
        }

        Purchase purchase = new Purchase();
        purchase.setUser(users);
        purchase.setCustomerInfo(customerInfo);
        purchase.setTotalAmount(totalItems);
        purchase.setTotalPrice(totalPrice);
        purchase.setPurchaseDate(LocalDateTime.now());
        purchaseService.createPurchase(purchase);

        // Update UserStatistics
        Optional<UserStatistics> userStatisticsOptional = userStatisticsService.findUserStatisticsByUserId(users.getUserId());
        UserStatistics userStatistics;
        if (userStatisticsOptional.isPresent()) {
            userStatistics = userStatisticsOptional.get();
        } else {
            userStatistics = new UserStatistics();
            userStatistics.setUser(users);
        }
        BigDecimal currentTotalAmountPaid = userStatistics.getTotalAmountPaid() != null ? userStatistics.getTotalAmountPaid() : BigDecimal.ZERO;
        userStatistics.setTotalAmountPaid(currentTotalAmountPaid.add(totalPrice));

        int currentTotalItemsBought = userStatistics.getTotalItemBought() != null ? userStatistics.getTotalItemBought() : 0;
        userStatistics.setTotalItemBought(currentTotalItemsBought + purchase.getTotalAmount());


        return ResponseEntity.ok().body("Cart sold successfully");
    }

}

