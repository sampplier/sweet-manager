package com.sweetmanager.service;
import com.sweetmanager.model.enums.Role;
import com.sweetmanager.dto.RegisterDTO;
import com.sweetmanager.exception.EmailAlreadyExistsException;
import com.sweetmanager.model.User;
import com.sweetmanager.repository.UserRepository;
import com.sweetmanager.dto.LoginDTO;
import com.sweetmanager.dto.TokenResponseDTO;
import com.sweetmanager.config.JwtUtil;
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
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword()))
            throw new RuntimeException("Senha incorreta");
        String token = jwtUtil.generateToken(user.getEmail());
        return new TokenResponseDTO(token);
    }
    public TokenResponseDTO register(RegisterDTO dto) {
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("E-mail já cadastrado");
        }

        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(Role.USER); //

        userRepository.save(user);

        String token = jwtUtil.generateToken(user.getEmail());
        return new TokenResponseDTO(token);
    }
}
