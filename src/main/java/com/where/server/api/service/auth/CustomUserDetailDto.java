package com.where.server.api.service.auth;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CustomUserDetailDto {
    String name;
    String mobile;
    String role;
}
