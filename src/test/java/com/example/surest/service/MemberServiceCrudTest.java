package com.example.surest.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.Mockito;

import com.example.surest.repository.MemberRepository;
import com.example.surest.entity.Member;
import com.example.surest.dto.MemberDto;
import com.example.surest.mapper.MemberMapper;
import com.example.surest.exception.ResourceNotFoundException;

import java.util.Optional;
import java.util.UUID;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceCrudTest {
    @Mock MemberRepository repo;
    @Mock MemberMapper mapper;
    @InjectMocks MemberService svc;

    @Test
    void createAndGet() {
        MemberDto dto = new MemberDto();
        dto.setFirstName("A"); dto.setLastName("B"); dto.setEmail("a@example.com"); dto.setDateOfBirth(LocalDate.of(1990,1,1));
        Member entity = new Member();
        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repo.save(entity)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(dto);

        MemberDto created = svc.create(dto);
        assertNotNull(created);
        verify(repo, times(1)).save(entity);
    }

    @Test
    void updateNotFound() {
        UUID id = UUID.randomUUID();
        MemberDto dto = new MemberDto();
        when(repo.findById(id)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> svc.update(id, dto));
    }

    @Test
    void deleteNotFound() {
        UUID id = UUID.randomUUID();
        when(repo.existsById(id)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> svc.delete(id));
    }
}
