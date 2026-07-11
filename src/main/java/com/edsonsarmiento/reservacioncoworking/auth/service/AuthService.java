package com.edsonsarmiento.reservacioncoworking.auth.service;

import com.edsonsarmiento.reservacioncoworking.auth.Role;
import com.edsonsarmiento.reservacioncoworking.auth.dto.AuthResponse;
import com.edsonsarmiento.reservacioncoworking.auth.dto.LoginRequest;
import com.edsonsarmiento.reservacioncoworking.auth.dto.RegistroRequest;
import com.edsonsarmiento.reservacioncoworking.auth.entity.User;
import com.edsonsarmiento.reservacioncoworking.auth.repository.UserRepository;
import com.edsonsarmiento.reservacioncoworking.exceptions.EmailExisteException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService,
                       AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthResponse registrarUsuario(RegistroRequest request) {

        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new EmailExisteException("El email ya está en uso");
        }

        User user = new User();
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));

        if (request.role() != null) {
            user.setRole(request.role());
        } else {
            user.setRole(Role.USER);
        }

        userRepository.save(user);

        String jwtToken = jwtService.generateToken(user);

        return new AuthResponse(jwtToken);
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        User user = userRepository.findByEmail(request.email()).orElseThrow();

        String jwtToken = jwtService.generateToken(user);

        return new AuthResponse(jwtToken);
    }
}
