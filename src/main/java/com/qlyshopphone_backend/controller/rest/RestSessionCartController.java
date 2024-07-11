package com.qlyshopphone_backend.controller.rest;

import com.qlyshopphone_backend.dto.CartDTO;
import com.qlyshopphone_backend.dto.CustomerInfoDTO;
import com.qlyshopphone_backend.mapper.CartMapper;
import com.qlyshopphone_backend.mapper.CustomerInfoMapper;
import com.qlyshopphone_backend.model.Users;
import com.qlyshopphone_backend.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping()
public class RestSessionCartController {
    @Autowired
    private UserService userService;

    @GetMapping("/session-cart")
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
}
