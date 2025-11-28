package com.example.surest.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.surest.repository.MemberRepository;
import com.example.surest.entity.Member;
import com.example.surest.dto.MemberDto;
import com.example.surest.mapper.MemberMapper;
import com.example.surest.exception.ResourceNotFoundException;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class MemberService {
    private final MemberRepository repo;
    private final MemberMapper mapper;
    private final Logger log = LoggerFactory.getLogger(getClass());

    public MemberService(MemberRepository repo, MemberMapper mapper) { this.repo = repo; this.mapper = mapper; }

    public Page<MemberDto> search(Pageable pageable, String firstName, String lastName) {
        Specification<Member> spec = (root, q, cb) -> {
            List<Predicate> p = new ArrayList<>();
            if (StringUtils.hasText(firstName)) {
                p.add(cb.like(cb.lower(root.get("firstName")), "%" + firstName.toLowerCase() + "%"));
            }
            if (StringUtils.hasText(lastName)) {
                p.add(cb.like(cb.lower(root.get("lastName")), "%" + lastName.toLowerCase() + "%"));
            }
            return cb.and(p.toArray(new Predicate[0]));
        };
        return repo.findAll(spec, pageable).map(mapper::toDto);
    }

    @Cacheable(value = "members", key = "#id")
    public MemberDto getById(UUID id) {
        return repo.findById(id).map(mapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found: " + id));
    }

    @CacheEvict(value = "members", allEntries = false, key = "#result.id")
    public MemberDto create(MemberDto dto) {
        Member toSave = mapper.toEntity(dto);
        Member saved = repo.save(toSave);
        log.info("Created member {}", saved.getId());
        return mapper.toDto(saved);
    }

    @CacheEvict(value = "members", key = "#id")
    public MemberDto update(UUID id, MemberDto dto) {
        Member existing = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Member not found: " + id));
        existing.setFirstName(dto.getFirstName());
        existing.setLastName(dto.getLastName());
        existing.setEmail(dto.getEmail());
        existing.setDateOfBirth(dto.getDateOfBirth());
        Member updated = repo.save(existing);
        log.info("Updated member {}", id);
        return mapper.toDto(updated);
    }

    @CacheEvict(value = "members", key = "#id")
    public void delete(UUID id) {
        if (!repo.existsById(id)) throw new ResourceNotFoundException("Member not found: " + id);
        repo.deleteById(id);
        log.info("Deleted member {}", id);
    }
}
