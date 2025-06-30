package com.example.ecommerce.controller;

import com.example.ecommerce.dto.LoginRequest;
import com.example.ecommerce.dto.RegisterRequest;
import com.example.ecommerce.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        authService.register(request);
        return ResponseEntity.ok("Registrasi berhasil, silakan verifikasi email.");
    }

    @PostMapping("/admin/register")
    public ResponseEntity<String> registerAdmin(@RequestBody RegisterRequest request) {
        authService.registerAdmin(request);
        return ResponseEntity.ok("Registrasi admin berhasil, silakan verifikasi email.");
    }

    @GetMapping("/verify")
    public ResponseEntity<String> verify(@RequestParam String token) {
        boolean verified = authService.verifyToken(token);
        return verified ? ResponseEntity.ok("Verifikasi berhasil.")
                : ResponseEntity.badRequest().body("Token tidak valid atau kadaluarsa.");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        try {
            String token = authService.login(request.getEmail(), request.getPassword());
            return ResponseEntity.ok(token);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}