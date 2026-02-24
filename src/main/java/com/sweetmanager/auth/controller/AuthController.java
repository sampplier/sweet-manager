package com.sweetmanager.auth.controller;

import com.sweetmanager.auth.dto.LoginDTO;
import com.sweetmanager.auth.dto.RegisterDTO;
import com.sweetmanager.auth.dto.TokenResponseDTO;
import com.sweetmanager.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@EnableMethodSecurity
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final AuthService service;

    @PostMapping("/login")
    public TokenResponseDTO login(@RequestBody LoginDTO dto) {
        return service.login(dto);
    }

    // Registro normal
    @PostMapping("/register")
    public TokenResponseDTO register(@RequestBody RegisterDTO dto) {
        return service.register(dto);
    }

    // Registro de admin
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/register")
    public TokenResponseDTO registerAdmin(@RequestBody RegisterDTO dto) {
        return service.registerAdmin(dto);
    }
}