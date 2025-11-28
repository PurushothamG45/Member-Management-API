package com.example.surest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.example.surest.entity.Member;
import com.example.surest.dto.MemberDto;

@Mapper(componentModel = "spring")
public interface MemberMapper {
    MemberDto toDto(Member member);
    Member toEntity(MemberDto dto);
}
