package com.qlyshopphone_backend.controller.controller;
import static com.qlyshopphone_backend.constant.PathConstant.*;

import com.qlyshopphone_backend.dto.request.AuthenticationRequest;
import com.qlyshopphone_backend.dto.request.UserRequest;
import com.qlyshopphone_backend.dto.response.LoginResponse;
import com.qlyshopphone_backend.dto.response.UserResponse;
import com.qlyshopphone_backend.service.AuthenticationService;
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
    public ResponseEntity<LoginResponse> login(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.login(request));
    }

    @PostMapping(REGISTER)
    public ResponseEntity<UserResponse> register(@RequestBody UserRequest userRequest) throws Exception {
        userRequest.setEmployee(false);
        return ResponseEntity.ok(authenticationService.register(userRequest));
    }
}
