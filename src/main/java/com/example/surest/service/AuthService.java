package com.example.surest.service;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.surest.repository.UserRepository;
import com.example.surest.security.JwtUtil;
import com.example.surest.entity.User;

import java.util.Optional;

@Service
public class AuthService {
    private final UserRepository userRepo;
    private final BCryptPasswordEncoder encoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepo, BCryptPasswordEncoder encoder, JwtUtil jwtUtil) {
        this.userRepo = userRepo;
        this.encoder = encoder;
        this.jwtUtil = jwtUtil;
    }

    public String authenticate(String username, String password) {
        Optional<User> u = userRepo.findByUsername(username);
        if (u.isPresent() && encoder.matches(password, u.get().getPasswordHash())) {
            return jwtUtil.generateToken(u.get());
        }
        throw new RuntimeException("Invalid credentials");
    }
}
