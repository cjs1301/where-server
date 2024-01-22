package com.where.api.domain.member.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CustomUserDetailDto {
    String name;
    String mobile;
    String role;
}
