package com.edsonsarmiento.reservacioncoworking.auth.controller;

import com.edsonsarmiento.reservacioncoworking.auth.dto.AuthResponse;
import com.edsonsarmiento.reservacioncoworking.auth.dto.LoginRequest;
import com.edsonsarmiento.reservacioncoworking.auth.dto.RegistroRequest;
import com.edsonsarmiento.reservacioncoworking.auth.entity.User;
import com.edsonsarmiento.reservacioncoworking.auth.repository.UserRepository;
import com.edsonsarmiento.reservacioncoworking.auth.service.AuthService;
import com.edsonsarmiento.reservacioncoworking.auth.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthService authService;

    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, JwtService jwtService,  AuthService authService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        User user = userRepository.findByEmail(request.email()).orElseThrow();

        String jwtToken = jwtService.generateToken(user);

        return ResponseEntity.ok(new AuthResponse(jwtToken));
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> register(@RequestBody RegistroRequest request) {
        AuthResponse response = authService.registrarUsuario(request);
        return ResponseEntity.ok(response);
    }
}
