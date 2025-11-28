package com.example.surest.controller;

import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import jakarta.validation.Valid;

import com.example.surest.service.MemberService;
import com.example.surest.dto.MemberDto;

import java.util.UUID;

@RestController
@RequestMapping("/members")
public class MemberController {
    private final MemberService svc;
    public MemberController(MemberService svc) { this.svc = svc; }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public Page<MemberDto> list(
            @PageableDefault(sort = "lastName", direction = Sort.Direction.ASC) Pageable pageable,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName) {
        return svc.search(pageable, firstName, lastName);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public MemberDto get(@PathVariable UUID id) { return svc.getById(id); }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<MemberDto> create(@Valid @RequestBody MemberDto dto) {
        MemberDto created = svc.create(dto);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public MemberDto update(@PathVariable UUID id, @Valid @RequestBody MemberDto dto) { return svc.update(id, dto); }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable UUID id) { svc.delete(id); return ResponseEntity.noContent().build(); }
}
