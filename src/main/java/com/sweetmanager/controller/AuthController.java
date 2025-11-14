package com.sweetmanager.controller;

import com.sweetmanager.dto.LoginDTO;
import com.sweetmanager.dto.RegisterDTO;
import com.sweetmanager.dto.TokenResponseDTO;
import com.sweetmanager.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {
    private final AuthService service;

    @PostMapping("/login")
    public TokenResponseDTO login(@RequestBody LoginDTO dto) {
        return service.login(dto);
    }
    @PostMapping("/register")
    public TokenResponseDTO register(@RequestBody RegisterDTO dto) {
        return service.register(dto);
    }
}
