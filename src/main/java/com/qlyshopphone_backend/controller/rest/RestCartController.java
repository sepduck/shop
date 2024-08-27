package com.qlyshopphone_backend.controller.rest;
import static com.qlyshopphone_backend.constant.PathConstant.*;

import static com.qlyshopphone_backend.constant.ErrorMessage.*;
import com.qlyshopphone_backend.dto.CartDTO;
import com.qlyshopphone_backend.dto.CustomerInfoDTO;
import com.qlyshopphone_backend.dto.PurchaseDTO;
import com.qlyshopphone_backend.mapper.CartMapper;
import com.qlyshopphone_backend.mapper.CustomerInfoMapper;
import com.qlyshopphone_backend.model.*;
import com.qlyshopphone_backend.repository.CartRepository;
import com.qlyshopphone_backend.repository.CustomerInfoRepository;
import com.qlyshopphone_backend.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(API_V1)
@RequiredArgsConstructor
public class RestCartController {
    private final UserService userService;
    private final ProductService productService;
    private final CartService cartService;
    private final NotificationService notificationService;
    private final CustomerInfoRepository customerInfoRepository;
    private final CartRepository cartRepository;

    @PostMapping(CART_ADD_PRODUCT_ID)
    public ResponseEntity<?> addCartItem(@PathVariable("productId") Long productId, Principal principal) {
        Users users = userService.findByUsername(principal.getName());
        Product product = productService.findByProductId(productId).
                orElseThrow(() -> new RuntimeException(PRODUCT_NOT_FOUND));
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
            cart.setQuantity(1L);
            cart.setUser(users);
            users.getCart().add(cart);
        }
        userService.saveUser(users);
        return ResponseEntity.ok().body(PRODUCT_ADDED_SUCCESSFULLY);
    }

    @GetMapping(LIST_CART)
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

    @DeleteMapping(DELETE_CART_CART_ID)
    public ResponseEntity<?> deleteCartItem(@PathVariable("cartId") Long cartId,
                                            Principal principal) {
        Users user = userService.findByUsername(principal.getName());
        return ResponseEntity.ok(cartService.deleteCart(cartId, user));
    }

    @PostMapping(CART_SELL_CART_ID_CUSTOMER_INFO_ID)
    public ResponseEntity<?> sellCartItem(@PathVariable("cardId") Long cardId,
                                          @PathVariable("customerInfoId") Long customerInfoId) {
        // Tìm kiếm giỏ hàng theo id
        Cart cart = cartService.findCartById(cardId);

        // Lấy tên người dùng từ Authentication context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Users users = userService.findByUsername(username);

        // Kiểm tra sản phẩm trong giỏ hàng đã được bán chưa
        if (cart.isSold()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(PRODUCT_ALREADY_SOLD);
        }

        Product product = cart.getProduct();

        // Kiểm tra tồn kho và cập nhật số lượng tồn kho
        if (product.getInventory() > 0) {
            product.setInventory(product.getInventory() - cart.getQuantity());
            productService.createProduct(product);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(PRODUCT_OUT_OF_STOCK);
        }

        // Đánh dấu giỏ hàng đã được bán
        cart.setSold(true);
        cartService.saveCart(CartMapper.toDto(cart));

        // Tìm kiếm thông tin khách hàng theo customerInfoId
        CustomerInfo customerInfo = customerInfoRepository.findById(customerInfoId)
                .orElseThrow(() -> new RuntimeException(CUSTOMER_INFO_NOT_FOUND));
        // Tạo đối tượng Purchase và thiết lập các thuộc tính
        Purchase purchase = new Purchase();
        purchase.setUser(users);
        purchase.setCustomerInfo(customerInfo);
        purchase.setTotalAmount(cart.getQuantity());
        BigDecimal totalPrice = product.getPrice().multiply(BigDecimal.valueOf(cart.getQuantity()));
        purchase.setTotalPrice(totalPrice); // Tính tổng tiền từ số lượng và giá sản phẩm
        purchase.setPurchaseDate(LocalDateTime.now());

        // Lưu giao dịch mua hàng
        cartService.createPurchase(purchase);

        Optional<UserStatistics> userStatisticsOptional = cartService.findUserStatisticsByUserId(users.getUserId());
        UserStatistics userStatistics;
        if (userStatisticsOptional.isPresent()) {
            userStatistics = userStatisticsOptional.get();
        } else {
            userStatistics = new UserStatistics();
            userStatistics.setUser(users);
        }
        BigDecimal currentTotalAmountPaid = userStatistics.getTotalAmountPaid() != null ? userStatistics.getTotalAmountPaid() : BigDecimal.ZERO;
        userStatistics.setTotalAmountPaid(currentTotalAmountPaid.add(totalPrice));

        long currentTotalItemsBought = userStatistics.getTotalItemBought() != null ? userStatistics.getTotalItemBought() : 0;
        userStatistics.setTotalItemBought(currentTotalItemsBought + purchase.getTotalAmount());

        cartService.saveUserStatistics(userStatistics);

        String message = users.getFullName() + " order " + cart.getProduct().getProductName() + " products";
        notificationService.saveNotification(message, users);
        return ResponseEntity.ok().body(CART_SOLD_SUCCESSFULLY);
    }

//     Endpoint để bán nhiều sản phẩm từ giỏ hàng
    @PostMapping(CART_SELLS_CUSTOMER_INFO_ID)
    public ResponseEntity<?> sellCart(@RequestBody List<Integer> cardsId,
                                      @PathVariable("customerInfoId") long customerInfoId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Users users = userService.findByUsername(username);
        BigDecimal totalPrice = BigDecimal.ZERO;
        long totalItems = 0;

        // Tìm kiếm thông tin khách hàng theo customerInfoId
        CustomerInfo customerInfo = customerInfoRepository.findById(customerInfoId)
                .orElseThrow(() -> new RuntimeException(CUSTOMER_INFO_NOT_FOUND));

        for (Integer cardId : cardsId) {
            Optional<Cart> optionalCart = cartRepository.findById(Long.valueOf(cardId));
            if (!optionalCart.isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(CART_NOT_FOUND);
            }
            Cart cart = optionalCart.get();
            if (cart.isSold()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(PRODUCT_ALREADY_SOLD);
            }

            Product product = cart.getProduct();
            if (product.getInventory() >= cart.getQuantity()) {
                product.setInventory(product.getInventory() - cart.getQuantity());
                productService.createProduct(product);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(PRODUCT_OUT_OF_STOCK);
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
        cartService.createPurchase(purchase);

        // Update UserStatistics
        Optional<UserStatistics> userStatisticsOptional = cartService.findUserStatisticsByUserId(users.getUserId());
        UserStatistics userStatistics;
        if (userStatisticsOptional.isPresent()) {
            userStatistics = userStatisticsOptional.get();
        } else {
            userStatistics = new UserStatistics();
            userStatistics.setUser(users);
        }
        BigDecimal currentTotalAmountPaid = userStatistics.getTotalAmountPaid() != null ? userStatistics.getTotalAmountPaid() : BigDecimal.ZERO;
        userStatistics.setTotalAmountPaid(currentTotalAmountPaid.add(totalPrice));

        long currentTotalItemsBought = userStatistics.getTotalItemBought() != null ? userStatistics.getTotalItemBought() : 0;
        userStatistics.setTotalItemBought(currentTotalItemsBought + purchase.getTotalAmount());


        return ResponseEntity.ok().body(CART_SOLD_SUCCESSFULLY);
    }

    @GetMapping(SALE)
    public ResponseEntity<?> listOfProduct(HttpServletRequest request) {
        HttpSession session = request.getSession();
        ArrayList<Integer> list = (ArrayList<Integer>) session.getAttribute("list");
        return null;
    }
    @GetMapping(CART_VIEW)
    public ResponseEntity<?> listCart(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        ArrayList<Integer> list = (ArrayList<Integer>) session.getAttribute("list");
        ArrayList<Map<String, Object>> productList = new ArrayList<>();
        if (list != null && !list.isEmpty()) {
            for (long id : list) {
                Map<String, Object> map = productService.getProductDetailId(id);
                productList.add(map);
            }
            return ResponseEntity.ok(productList);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(NO_ITEMS_IN_CART);
        }
    }


    // Thống kê doanh thu bán trong ngày
    @GetMapping(ADMIN_TODAY_PURCHASES)
    public ResponseEntity<?> getTodayPurchases() {
        List<PurchaseDTO> todayPurchases = cartService.getTodayPurchases();
        if (todayPurchases.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        BigDecimal totalPrice = cartService.calculateTotalPrice(todayPurchases);

        Map<String, Object> response = new HashMap<>();
        response.put("purchases", todayPurchases);
        response.put("totalPrice", totalPrice);

        return ResponseEntity.ok(response);
    }

    // Thống kê doanh thu trong 30 ngày tính từ thời điểm hiện tại
    @GetMapping(ADMIN_LAST_30_DAYS_PURCHASES)
    public ResponseEntity<?> getLast30DaysPurchases() {
        List<PurchaseDTO> getPurchaseLast30Days = cartService.getPurchaseBetweenDates();
        if (getPurchaseLast30Days.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        BigDecimal totalPrice = cartService.calculateTotalPrice(getPurchaseLast30Days);

        Map<String, Object> response = new HashMap<>();
        response.put("purchases", getPurchaseLast30Days);
        response.put("totalPrice", totalPrice);

        return ResponseEntity.ok(response);
    }

    @GetMapping(ADMIN_DAILY_SALES_TOTAL_PRICE_LAST_30_DAYS)
    public ResponseEntity<?> getDailySalesTotalPriceLast30Days() {
        List<BigDecimal> dailySaleTotalPrice = cartService.getDailySalesTotalPriceLast30Days();
        if (dailySaleTotalPrice.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(dailySaleTotalPrice);
    }



    // Phần trăm thay đổi tổng tiền bán hàng từ ngày hôm qua đến hôm nay
    @GetMapping("/admin/sales-percentage-change")
    public ResponseEntity<?> getSalesPercentageChange() {
        BigDecimal percentageChange = cartService.getPercentageChangeFromYesterdayToToday();
        return ResponseEntity.ok(percentageChange);
    }

    @GetMapping(ADMIN_SALES_MONTH_PERCENTAGE_CHANGE)
    public ResponseEntity<?> getSalesMonthPercentageChange() {
        BigDecimal percentageChange = cartService.getPercentageChangeFromLastMonthToThisMonth();
        return ResponseEntity.ok(percentageChange);
    }

    @GetMapping(CART_SESSION)
    public ResponseEntity<?> viewSessionCartAndCustomerInfo(Principal principal, HttpSession session) {
        Users user = userService.findByUsername(principal.getName());
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        // Lấy thông tin khách hàng từ session
        CustomerInfoDTO customerInfo = (CustomerInfoDTO) session.getAttribute("customerInfo");
        if (customerInfo == null) {
            List<CustomerInfoDTO> customerInfoList = user.getCustomerInfo()
                    .stream()
                    .filter(info -> !info.isDeleteCustomerInfo())
                    .map(CustomerInfoMapper::infoDTO)
                    .collect(Collectors.toList());
            if (!customerInfoList.isEmpty()) {
                customerInfo = customerInfoList.get(0);
                session.setAttribute("customerInfo", customerInfo);
            }
        }
        // Lấy thông tin giỏ hàng từ session
        List<CartDTO> cartDTOS = (List<CartDTO>) session.getAttribute("cartItems");
        if (cartDTOS == null) {
            cartDTOS = user.getCart()
                    .stream()
                    .filter(cart -> !cart.isSold())
                    .map(CartMapper::toDto)
                    .collect(Collectors.toList());
            session.setAttribute("cartItems", cartDTOS);
        }

        return ResponseEntity.ok().body(new SessionCartResponse(customerInfo, cartDTOS));
    }
    static class SessionCartResponse {
        private CustomerInfoDTO customerInfo;
        private List<CartDTO> cartItems;

        public SessionCartResponse(CustomerInfoDTO customerInfo, List<CartDTO> cartItems) {
            this.customerInfo = customerInfo;
            this.cartItems = cartItems;
        }

        // Getters and setters
    }

    @GetMapping(CUSTOMER_INFO)
    public ResponseEntity<?> viewCustomerInfo(Principal principal) {
        Users user = userService.findByUsername(principal.getName());
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        List<CustomerInfoDTO> customerInfoDTOS = user.getCustomerInfo()
                .stream()
                .filter(customerInfo -> !customerInfo.isDeleteCustomerInfo())
                .map(CustomerInfoMapper::infoDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(customerInfoDTOS);
    }

    @PostMapping(CUSTOMER_INFO)
    public ResponseEntity<?> createCustomerInfo(@RequestBody CustomerInfoDTO customerInfoDTO, Principal principal) {
        Users users = userService.findByUsername(principal.getName());
        if (users == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(USER_NOT_FOUND);
        }
        long userId = users.getUserId();
        customerInfoDTO.setUserId(userId);

        // Gọi service để tạo và lưu CustomerInfo
        return ResponseEntity.ok(cartService.createCustomerInfo(customerInfoDTO));
    }

    @PutMapping(CUSTOMER_INFO_ID)
    public ResponseEntity<?> updateCustomerInfo(@PathVariable("customerInfoId") Long customerInfoId,
                                                @RequestBody CustomerInfoDTO customerInfoDTO) {
        return ResponseEntity.ok(cartService.updateCustomerInfo(customerInfoId, customerInfoDTO));
    }

    @DeleteMapping(CUSTOMER_INFO_ID)
    public ResponseEntity<?> deleteCustomerInfo(@PathVariable("customerInfoId") Long customerInfoId) {
        cartService.deleteCustomerInfo(customerInfoId);
        return ResponseEntity.noContent().build();
    }
}

