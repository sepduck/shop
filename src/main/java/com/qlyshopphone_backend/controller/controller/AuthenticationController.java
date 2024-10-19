package com.qlyshopphone_backend.controller.controller;
import static com.qlyshopphone_backend.constant.PathConstant.*;

import com.qlyshopphone_backend.dto.request.AuthenticationRequest;
import com.qlyshopphone_backend.dto.request.UserRequest;
import com.qlyshopphone_backend.dto.response.LoginResponse;
import com.qlyshopphone_backend.service.AuthenticationService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(API_V1)
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping(LOGIN)
    public ResponseEntity<LoginResponse> login(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.login(request));
    }

    @PostMapping(REGISTER)
    public ResponseEntity<Boolean> register(@RequestBody UserRequest userRequest) throws MessagingException {
        return ResponseEntity.ok(authenticationService.registerUser(userRequest));
    }

    @GetMapping(VERIFY)
    public ResponseEntity<String> verifyAccount(@RequestParam("token") String token) {
        return ResponseEntity.ok(authenticationService.verifyAccount(token));
    }

    @PostMapping(RESEND_VERIFICATION)
    public ResponseEntity<String> resendVerification(@RequestParam String email) {
        try {
            return ResponseEntity.ok(authenticationService.resendVerificationToken(email));
        } catch (MessagingException e) {
            return ResponseEntity.status(500).body("Failed to send verification email: " + e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(FORGOT_PASSWORD)
    public ResponseEntity<String> forgotPassword(@RequestParam String email) {
        try {
            return ResponseEntity.ok(authenticationService.forgotPassword(email));
        } catch (MessagingException e) {
            return ResponseEntity.status(500).body("Failed to send new password email: " + e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

}
