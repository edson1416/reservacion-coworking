package com.edsonsarmiento.reservacioncoworking.auth.controller;

import com.edsonsarmiento.reservacioncoworking.auth.dto.AuthResponse;
import com.edsonsarmiento.reservacioncoworking.auth.dto.LoginRequest;
import com.edsonsarmiento.reservacioncoworking.auth.dto.RegistroRequest;
import com.edsonsarmiento.reservacioncoworking.auth.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {


    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> register(@Valid @RequestBody RegistroRequest request) {
        AuthResponse response = authService.registrarUsuario(request);
        return ResponseEntity.ok(response);
    }
}
