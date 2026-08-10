package com.example.PaymentGatewaySimulator.controller;

import com.example.PaymentGatewaySimulator.dto.RegisterRequest;
import com.example.PaymentGatewaySimulator.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.PaymentGatewaySimulator.dto.AuthResponse;
import com.example.PaymentGatewaySimulator.dto.LoginRequest;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @Valid @RequestBody RegisterRequest request) {

        authService.register(request);

        return ResponseEntity.ok(
                "User registered successfully"
        );
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest request) {

        String token = authService.login(request);

        return ResponseEntity.ok(
                new AuthResponse(token)
        );
    }
}