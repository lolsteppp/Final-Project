package com.didarovamedina.university.controller;

import com.didarovamedina.university.dto.*;
import com.didarovamedina.university.entity.DidarovaMedinaUser;
import com.didarovamedina.university.repository.UserRepository;
import com.didarovamedina.university.security.DidarovaMedinaJwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class DidarovaMedinaAuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final DidarovaMedinaJwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest req) {
        if (userRepository.findByEmail(req.getEmail()).isPresent())
            return ResponseEntity.badRequest().body("Email already exists");
        DidarovaMedinaUser user = DidarovaMedinaUser.builder()
                .email(req.getEmail())
                .password(passwordEncoder.encode(req.getPassword()))
                .role(req.getRole()).build();
        userRepository.save(user);
        log.info("User registered: {}", req.getEmail());
        return ResponseEntity.ok("Registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest req) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));
        String token = jwtUtil.generateToken(req.getEmail());
        log.info("User logged in: {}", req.getEmail());
        return ResponseEntity.ok(new AuthResponse(token));
    }
}