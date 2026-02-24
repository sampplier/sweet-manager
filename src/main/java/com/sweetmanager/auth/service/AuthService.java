package com.sweetmanager.auth.service;

import com.sweetmanager.auth.dto.LoginDTO;
import com.sweetmanager.auth.dto.RegisterDTO;
import com.sweetmanager.auth.dto.TokenResponseDTO;
import com.sweetmanager.exception.user.EmailAlreadyExistsException;
import com.sweetmanager.user.domain.User;
import com.sweetmanager.user.enums.Role;
import com.sweetmanager.user.repository.UserRepository;
import com.sweetmanager.security.JwtUtil;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public TokenResponseDTO login(LoginDTO dto) {

        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Credenciais invalidas."));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Credenciais invalidas.");
        }
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());
        return new TokenResponseDTO(token);
    }

    public TokenResponseDTO register(RegisterDTO dto) {

        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("E-mail ja cadastrado.");
        }

        User user = User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());
        return new TokenResponseDTO(token);
    }

    public TokenResponseDTO registerAdmin(RegisterDTO dto) {

        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("E-mail ja cadastrado.");
        }

        User user = User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(Role.ADMIN)
                .build();

        userRepository.save(user);

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());
        return new TokenResponseDTO(token);
    }
}