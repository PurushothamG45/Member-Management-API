package com.example.surest.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mock;
import org.mockito.InjectMocks;

import com.example.surest.repository.UserRepository;
import com.example.surest.security.JwtUtil;
import com.example.surest.entity.User;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    @Mock UserRepository userRepo;
    @Mock BCryptPasswordEncoder encoder;
    @Mock JwtUtil jwtUtil;
    @InjectMocks AuthService authService;

    @Test
    void authenticateSuccess() {
        User u = new User();
        u.setUsername("bob");
        u.setPasswordHash("hashed");
        when(userRepo.findByUsername("bob")).thenReturn(Optional.of(u));
        when(encoder.matches("plain","hashed")).thenReturn(true);
        when(jwtUtil.generateToken(u)).thenReturn("token123");
        String t = authService.authenticate("bob","plain");
        assertEquals("token123", t);
    }

    @Test
    void authenticateFail() {
        when(userRepo.findByUsername("nope")).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> authService.authenticate("nope","x"));
    }
}
