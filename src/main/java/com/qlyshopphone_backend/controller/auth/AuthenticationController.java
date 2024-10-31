package com.qlyshopphone_backend.controller.auth;
import static com.qlyshopphone_backend.constant.PathConstant.*;

import com.qlyshopphone_backend.dto.request.AuthenticationRequest;
import com.qlyshopphone_backend.dto.request.UserDetailRequest;
import com.qlyshopphone_backend.dto.response.LoginResponse;
import com.qlyshopphone_backend.service.AuthenticationService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(API_V1_AUTH)
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping(LOGIN)
    public ResponseEntity<LoginResponse> loginUser(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.loginUser(request));
    }

    @PostMapping(REGISTER)
    public ResponseEntity<Boolean> register(@RequestBody UserDetailRequest userDetailRequest) throws MessagingException {
        return ResponseEntity.ok(authenticationService.registerUser(userDetailRequest));
    }

    @GetMapping(VERIFY)
    public ResponseEntity<String> verifyAccount(@RequestParam("token") String token) {
        return ResponseEntity.ok(authenticationService.verifyUserAccount(token));
    }

    @PostMapping(RESEND_VERIFICATION)
    public ResponseEntity<String> resendVerification(@RequestParam String email) {
        try {
            return ResponseEntity.ok(authenticationService.resendVerificationTokenEmail(email));
        } catch (MessagingException e) {
            return ResponseEntity.status(500).body("Failed to send verification email: " + e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(FORGOT_PASSWORD)
    public ResponseEntity<String> forgotPassword(@RequestParam String email) {
        try {
            return ResponseEntity.ok(authenticationService.resetUserPassword(email));
        } catch (MessagingException e) {
            return ResponseEntity.status(500).body("Failed to send new password email: " + e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

}
