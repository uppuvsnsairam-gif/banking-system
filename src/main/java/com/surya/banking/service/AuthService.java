package com.surya.banking.service;

import com.surya.banking.dto.AuthResponse;
import com.surya.banking.dto.LoginRequest;
import com.surya.banking.dto.RegisterRequest;
import com.surya.banking.model.User;
import com.surya.banking.repository.UserRepository;
import com.surya.banking.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public String register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered!");
        }

        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("USER");

        userRepository.save(user);
        return "User registered successfully!";
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> 
                    new RuntimeException("User not found!"));

        if (!passwordEncoder.matches(request.getPassword(), 
                user.getPassword())) {
            throw new RuntimeException("Invalid password!");
        }

        String token = jwtUtil.generateToken(
            user.getEmail(), user.getRole());
            
        return new AuthResponse(token, user.getEmail(), user.getRole());
    }
}