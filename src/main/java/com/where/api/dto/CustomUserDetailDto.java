package com.where.api.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CustomUserDetailDto {
    String name;
    String mobile;
    String role;
}
