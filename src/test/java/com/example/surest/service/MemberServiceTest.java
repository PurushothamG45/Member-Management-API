package com.example.surest.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.Mockito;

import com.example.surest.repository.MemberRepository;
import com.example.surest.entity.Member;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {
    @Mock MemberRepository repo;
    @InjectMocks MemberService svc;

    @Test
    void getByIdNotFound() {
        UUID id = UUID.randomUUID();
        when(repo.findById(id)).thenReturn(Optional.empty());
        Exception ex = assertThrows(RuntimeException.class, () -> svc.getById(id));
        assertTrue(ex.getMessage().contains("Member not found"));
    }
}
