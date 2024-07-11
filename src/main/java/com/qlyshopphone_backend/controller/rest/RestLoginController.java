package com.qlyshopphone_backend.controller.rest;

import com.qlyshopphone_backend.dto.UsersDTO;
import com.qlyshopphone_backend.model.Users;
import com.qlyshopphone_backend.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;

@RestController
@RequestMapping("/account")
public class RestLoginController {
    @Autowired
    private UserService userService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ACCOUNTANT', 'ROLE_USER')")
    @GetMapping("/info")
    public ResponseEntity<UsersDTO> getAccountInfo(HttpServletRequest request) {
        String username = request.getUserPrincipal().getName();
        Users user = userService.findByUsername(username);

        UsersDTO userDTO = new UsersDTO();
        if (user.getFileUser() != null) {
            String base64Image = Base64.getEncoder().encodeToString(user.getFileUser());
            userDTO.setThumbnail(base64Image);
            System.out.println("Encoded image: " + base64Image);
        }else {
            userDTO.setThumbnail("");
        }

        return ResponseEntity.ok(userDTO);
    }
}
