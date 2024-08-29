package com.qlyshopphone_backend.controller.controller;
import static com.qlyshopphone_backend.constant.PathConstant.*;

import com.qlyshopphone_backend.dto.UsersDTO;
import com.qlyshopphone_backend.model.Users;
import com.qlyshopphone_backend.service.AuthenticationService;
import com.qlyshopphone_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping(API_V1)
@RequiredArgsConstructor
public class LoginController {
    private final AuthenticationService authenticationService;

    @PostMapping(LOGIN)
    public ResponseEntity<String> login(@RequestBody Users users) {
        return ResponseEntity.ok(authenticationService.login(users));
    }

    @PostMapping(REGISTER)
    public ResponseEntity<String> register(@ModelAttribute UsersDTO usersDTO) throws Exception {
        usersDTO.setEmployee(false);
        usersDTO.setDeleteUser(false);
        usersDTO.setRoleId(3L);
        return ResponseEntity.ok(authenticationService.register(usersDTO));
    }
}
