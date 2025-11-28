package com.example.surest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.surest.dto.AuthRequest;
import com.example.surest.dto.AuthResponse;
import com.example.surest.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    public AuthController(AuthService authService) { this.authService = authService; }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest req) {
        String token = authService.authenticate(req.getUsername(), req.getPassword());
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
