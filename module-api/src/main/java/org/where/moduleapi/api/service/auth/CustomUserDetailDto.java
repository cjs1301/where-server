package org.where.moduleapi.api.service.auth;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CustomUserDetailDto {
    Long id;
    String name;
    String phoneNumber;
    String role;

    public static CustomUserDetailDto from(CustomUserDetails user){
        return CustomUserDetailDto.builder()
                .id(user.getId())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole())
                .name(user.getName())
                .build();
    }
}
