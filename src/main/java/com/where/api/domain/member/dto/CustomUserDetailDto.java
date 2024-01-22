package com.where.api.domain.member.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CustomUserDetailDto {
    String name;
    String mobile;
    List<String> role;
}
