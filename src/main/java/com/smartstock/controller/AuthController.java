package com.smartstock.controller;

import com.smartstock.dto.AuthResponse;
import com.smartstock.dto.LoginRequest;
import com.smartstock.dto.RegisterRequest;
import com.smartstock.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(authService.register(registerRequest));
    }

    @GetMapping("/user")
    public ResponseEntity<AuthResponse> getCurrentUser() {
        return ResponseEntity.ok(authService.getCurrentUser());
    }
}